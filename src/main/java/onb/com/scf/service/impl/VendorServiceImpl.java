package onb.com.scf.service.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import onb.com.scf.constant.StatusConstant;
import onb.com.scf.datetimeutility.DateTimeUtility;
import onb.com.scf.dto.ResponseDto;
import onb.com.scf.dto.VendoActivateRequest;
import onb.com.scf.dto.VendorDeactivateRequest;
import onb.com.scf.dto.VendorDetailsResponseDto;
import onb.com.scf.dto.VendorPreAuthResponse;
import onb.com.scf.entity.UserEntity;
import onb.com.scf.entity.VendorEntity;
import onb.com.scf.entity.VendorHistoryEntity;
import onb.com.scf.entity.VendorPreAuthEntity;
import onb.com.scf.repository.IMRepository;
import onb.com.scf.repository.VendorHistoryRepository;
import onb.com.scf.repository.VendorPreAuthRepository;
import onb.com.scf.repository.VendorRepository;
import onb.com.scf.service.VendorService;

/**
 * @author Sapna Singh
 *
 */
@Service
@Slf4j
public class VendorServiceImpl implements VendorService {
	@Autowired
	VendorRepository vendorRepository;
	@Autowired
	UserEntityService userEntityService;
	@Autowired
	IMRepository imRepository;
	@Autowired
	VendorPreAuthRepository vendorPreAuthRepository;
	@Autowired
	VendorHistoryRepository vendorHistoryRepository;

	/**
	 * Getting all vendor record by using the method findaAll()
	 * 
	 * @return vendor
	 */
	public VendorDetailsResponseDto getAllVendor() {
		VendorDetailsResponseDto response = new VendorDetailsResponseDto();
		List<VendorEntity> vendor = new ArrayList<>();
		try {
			vendorRepository.findAll().forEach(vendor::add);
			if (!vendor.isEmpty()) {
				response.setStatusCode(StatusConstant.STATUS_SUCCESS_CODE);
				response.setStatus(StatusConstant.STATUS_SUCCESS);
				response.setMsg(StatusConstant.STATUS_DESCRIPTION_VENDOR_LIST_RETRIVED_SUCESSFULLY);
				response.setData(vendor);
			} else {
				response.setStatusCode(StatusConstant.STATUS_DATA_NOT_FOUND_CODE);
				response.setStatus(StatusConstant.STATUS_FAILURE);
				response.setMsg(StatusConstant.STATUS_DATA_NOT_AVAILAIBLE);
				response.setData(vendor);
			}
		} catch (Exception e) {
			log.error(StatusConstant.EXCEPTION_OCCURRED + e.getMessage());
			response.setStatusCode(StatusConstant.STATUS_FAILURE_CODE);
			response.setStatus(StatusConstant.STATUS_FAILURE);
			response.setMsg(e.getMessage());
		}
		return response;
	}

	/**
	 * Getting a specific record by using the method findById()
	 * 
	 * @param id
	 * @return vendor
	 */
	public VendorDetailsResponseDto getVendorByCode(String id) {
		VendorDetailsResponseDto response = new VendorDetailsResponseDto();
		try {
			List<VendorEntity> vendorList = vendorRepository.findByVendorCode(id);
			if (!vendorList.isEmpty()) {
				response.setStatusCode(StatusConstant.STATUS_SUCCESS_CODE);
				response.setStatus(StatusConstant.STATUS_SUCCESS);
				response.setMsg(StatusConstant.STATUS_DESCRIPTION_VENDOR_RETRIVED_SUCESSFULLY + id);
				response.setData(vendorList);
			} else {
				response.setStatusCode(StatusConstant.STATUS_DATA_NOT_FOUND_CODE);
				response.setStatus(StatusConstant.STATUS_FAILURE);
				response.setMsg(StatusConstant.STATUS_DATA_NOT_AVAILAIBLE);
			}
		} catch (Exception e) {
			log.error(StatusConstant.EXCEPTION_OCCURRED + e.getMessage());
			response.setStatusCode(StatusConstant.STATUS_FAILURE_CODE);
			response.setStatus(StatusConstant.STATUS_FAILURE);
			response.setMsg(e.getMessage());
		}
		return response;
	}

	public synchronized Long getTopId() {
		return vendorRepository.getTopId();
	}

	public synchronized String getTopVendorCode() {
		return vendorPreAuthRepository.getTopVendorCode();
	}

	/**
	 * Saving a specific record by using the method save()
	 * 
	 * @param vendor
	 */
	public ResponseDto addVendorByMaker(VendorEntity vendor) {
		ResponseDto responseDto = new ResponseDto();
		Optional<UserEntity> userEntityData = userEntityService.getEntityType("VENDOR");
		String prefix = "";
		boolean isPanAlreadyExistWithIM = false;
		try {
			if (userEntityData.isPresent()) {
				/*
				 * if (vendor.getVendorPan() == null || vendor.getVendorPan().isEmpty()) {
				 * responseDto.setStatusCode(StatusConstant.STATUS_FAILURE_CODE);
				 * responseDto.setStatus(StatusConstant.STATUS_FAILURE);
				 * responseDto.setMsg(StatusConstant.STATUS_PAN_NUMBER_NOT_FOUND); return
				 * responseDto; }
				 */
				/*
				 * isPanAlreadyExistWithIM = checkPanAlreadyExistForAdd(vendor.getVendorPan());
				 * if (isPanAlreadyExistWithIM) {
				 * responseDto.setStatusCode(StatusConstant.STATUS_FAILURE_CODE);
				 * responseDto.setStatus(StatusConstant.STATUS_FAILURE);
				 * responseDto.setMsg(StatusConstant.PAN_CARD_ALREADY_MAPPED); return
				 * responseDto; }
				 */
				prefix = userEntityData.get().getPrefix();
				log.info("add Vendor started ");
				Long maxId = vendorRepository.getTopId();
				if (maxId == null) {
					Long stratValue = userEntityData.get().getStratValue();
					vendor.setVendorSeqId(stratValue);
					vendor.setVendorCode(prefix + stratValue);
				} else {
					vendor.setVendorSeqId(maxId + 1);
					vendor.setVendorCode(prefix + (maxId + 1));
				}
				vendor.setAction(StatusConstant.ACTION_ADD_VENDOR);
				vendor.setCreationTime(DateTimeUtility.getCurrentTimeStamp());
				vendor.setStatus(StatusConstant.VENDOR_PENDING_STATUS);
				vendor.setVendorOnboardedFromSourceId(StatusConstant.VENDOR_ONB_FROM_SCF);
				VendorPreAuthEntity preauthVendor = new VendorPreAuthEntity(vendor);
				preauthVendor.setVendorOnboardedFromSourceId(StatusConstant.VENDOR_ONB_FROM_SCF);
				log.info("preauth vendor saved");
				vendorPreAuthRepository.save(preauthVendor);
				log.info("Vendor saved in master ");
				vendorRepository.save(vendor);
				VendorHistoryEntity vendorHistory = new VendorHistoryEntity(vendor);
				vendorHistory.setVendorOnboardedFromSourceId(StatusConstant.VENDOR_ONB_FROM_SCF);
				log.info("Vendor saved in history");
				vendorHistoryRepository.save(vendorHistory);
				responseDto.setStatus(StatusConstant.STATUS_SUCCESS);
				responseDto.setStatusCode(StatusConstant.STATUS_SUCCESS_CODE);
				responseDto.setMsg(StatusConstant.STATUS_DESCRIPTION_VENDOR_ADDED_SUCESSFULLY);
			} else {
				responseDto.setStatus(StatusConstant.STATUS_FAILURE);
				responseDto.setStatusCode(StatusConstant.STATUS_DATA_NOT_FOUND_CODE);
				responseDto.setMsg(StatusConstant.STATUS_PREFIX_NOT_AVAILAIBLE);
			}
		} catch (Exception e) {
			log.error(StatusConstant.EXCEPTION_OCCURRED + e.getMessage());
			responseDto.setStatusCode(StatusConstant.STATUS_FAILURE_CODE);
			responseDto.setStatus(StatusConstant.STATUS_FAILURE);
			responseDto.setMsg(e.getMessage());
		}
		return responseDto;
	}

	public ResponseDto updateVendorByMaker(VendorEntity vendor) {
		ResponseDto responseDto = new ResponseDto();
		Timestamp currentTimestamp = null;
		boolean isPanAlreadyExistWithIM = false;
		log.info("update vendor details  ");
		try {
			currentTimestamp = DateTimeUtility.getCurrentTimeStamp();
			if (vendor.getVendorOnboardedFromSourceId() == StatusConstant.VENDOR_ONB_FROM_LLMS) {
				responseDto.setStatusCode(StatusConstant.STATUS_FAILURE_CODE);
				responseDto.setStatus(StatusConstant.STATUS_FAILURE);
				responseDto
						.setMsg(StatusConstant.STATUS_DESCRIPTION_VENDOR_ONBOARDED_FROM_LLMS + vendor.getVendorCode());
				return responseDto;
			}
			if (vendor.getVendorCode() == null || vendor.getVendorCode().isEmpty()) {
				responseDto.setStatusCode(StatusConstant.STATUS_FAILURE_CODE);
				responseDto.setStatus(StatusConstant.STATUS_FAILURE);
				responseDto.setMsg(StatusConstant.STATUS_VENDOR_CODE_NOT_FOUND);
				return responseDto;
			}
			/*
			 * if (vendor.getVendorPan() == null || vendor.getVendorPan().isEmpty()) {
			 * responseDto.setStatusCode(StatusConstant.STATUS_FAILURE_CODE);
			 * responseDto.setStatus(StatusConstant.STATUS_FAILURE);
			 * responseDto.setMsg(StatusConstant.STATUS_PAN_NUMBER_NOT_FOUND); return
			 * responseDto; }
			 */
			/*
			 * isPanAlreadyExistWithIM =
			 * checkPanAlreadyExistForUpdate(vendor.getVendorPan(), vendor.getVendorCode());
			 * 
			 * if (isPanAlreadyExistWithIM) {
			 * responseDto.setStatusCode(StatusConstant.STATUS_FAILURE_CODE);
			 * responseDto.setStatus(StatusConstant.STATUS_FAILURE);
			 * responseDto.setMsg(StatusConstant.PAN_CARD_ALREADY_MAPPED); return
			 * responseDto; }
			 */

			List<VendorEntity> vendorList = vendorRepository.findByVendorCode(vendor.getVendorCode());
			vendor.setVendorSeqId(vendorList.get(0).getVendorSeqId());
			vendor.setLastModificationDateTime(currentTimestamp);
			Long vendorPreAuthId = vendorPreAuthRepository.getVendorPreauthId(vendorList.get(0).getVendorCode());
			VendorPreAuthEntity preauthVendor = new VendorPreAuthEntity(vendor);
			preauthVendor.setAction(StatusConstant.ACTION_UPDATE_VENDOR);
			preauthVendor.setStatus(StatusConstant.VENDOR_PENDING_STATUS);
			preauthVendor.setCreationTime(currentTimestamp);
			if (vendorPreAuthId != null && vendorPreAuthId != 0) {
				preauthVendor.setPreAuthvendorId(vendorPreAuthId);
			}
			vendorPreAuthRepository.save(preauthVendor);
			log.info("preauth vendor updated");

			VendorHistoryEntity vendorHistory = new VendorHistoryEntity(vendorList.get(0));
			vendorHistory.setCreationTime(currentTimestamp);
			vendorHistory.setAction(StatusConstant.ACTION_UPDATE_VENDOR);
			vendorHistory.setStatus(StatusConstant.VENDOR_PENDING_STATUS);
			log.info("Vendor saved in history");
			vendorHistoryRepository.save(vendorHistory);
			log.info(StatusConstant.STATUS_DESCRIPTION_VENDOR_UPDATED_SUCESSFULLY);
			responseDto.setStatus(StatusConstant.STATUS_SUCCESS);
			responseDto.setStatusCode(StatusConstant.STATUS_SUCCESS_CODE);
			responseDto.setMsg(StatusConstant.STATUS_DESCRIPTION_VENDOR_UPDATED_SUCESSFULLY + vendor.getVendorCode());
		} catch (Exception e) {
			log.error(StatusConstant.EXCEPTION_OCCURRED + e.getMessage());
			responseDto.setStatusCode(StatusConstant.STATUS_FAILURE_CODE);
			responseDto.setStatus(StatusConstant.STATUS_FAILURE);
			responseDto.setMsg(e.getMessage());
		}
		return responseDto;
	}

	/**
	 * Deleting a specific record by using the method deleteById()
	 * 
	 * @param id
	 */
	public ResponseDto deleteVendorByIdByMaker(String vendorId) {
		ResponseDto responseDto = new ResponseDto();
		VendorHistoryEntity vhis = null;
		try {
			log.info("delete Vendor started for given VendorCode " + vendorId);
			Long vendorPreAuthId = vendorPreAuthRepository.getVendorPreauthId(vendorId);
			List<VendorEntity> vendorList = vendorRepository.findByVendorCode(vendorId);
			if (vendorPreAuthId != null && vendorPreAuthId != 0) {
				vendorPreAuthRepository.deletePreAuthVendor(vendorId);
			} else {
				if (vendorList != null && !vendorList.isEmpty()) {
					VendorPreAuthEntity vendorPreauth = new VendorPreAuthEntity(vendorList.get(0));
					vendorPreauth.setAction(StatusConstant.ACTION_DELETE_VENDOR);
					vendorPreauth.setStatus(StatusConstant.VENDOR_PENDING_STATUS);
					vendorPreauth.setCreationTime(DateTimeUtility.getCurrentTimeStamp());
					vendorPreauth.setAuthorizedBy(null);
					vendorPreauth.setAuthorizedDate(null);
					vendorPreAuthRepository.save(vendorPreauth);
					responseDto.setStatusCode(StatusConstant.STATUS_SUCCESS_CODE);
					responseDto.setStatus(StatusConstant.STATUS_SUCCESS);
					responseDto.setMsg(StatusConstant.STATUS_DESCRIPTION_VENDOR_DELETED_SUCESSFULLY + vendorId);
				}
			}
			if (vendorList != null && !vendorList.isEmpty()) {
				vhis = new VendorHistoryEntity(vendorList.get(0));
				vhis.setCreationTime(DateTimeUtility.getCurrentTimeStamp());
				vhis.setAction(StatusConstant.ACTION_DELETE_VENDOR);
				vhis.setStatus(StatusConstant.VENDOR_PENDING_STATUS);
				vendorHistoryRepository.save(vhis);
			}
			responseDto.setStatus(StatusConstant.STATUS_SUCCESS);
			responseDto.setStatusCode(StatusConstant.STATUS_SUCCESS_CODE);
			responseDto.setMsg(StatusConstant.STATUS_DESCRIPTION_VENDOR_DELETED_SUCESSFULLY + vendorId);
		} catch (Exception e) {
			log.error(StatusConstant.EXCEPTION_OCCURRED + e.getMessage());
			responseDto.setStatusCode(StatusConstant.STATUS_FAILURE_CODE);
			responseDto.setStatus(StatusConstant.STATUS_FAILURE);
			responseDto.setMsg(e.getMessage());
		}
		return responseDto;
	}

	@Override
	public ResponseDto deActivateVendorByMaker(VendorDeactivateRequest request) {
		ResponseDto responseDto = new ResponseDto();
		String vendorCode = request.getVendorCode();
		boolean deactivateFlag = request.getIsVendorDeativate();
		Timestamp currentTimestamp = null;
		VendorHistoryEntity vhis = null;
		try {
			currentTimestamp = DateTimeUtility.getCurrentTimeStamp();
			Long vendorPreAuthId = vendorPreAuthRepository.getVendorPreauthId(vendorCode);
			List<VendorEntity> vendorList = vendorRepository.findByVendorCode(vendorCode);
			if (vendorList != null && !vendorList.isEmpty()) {
				vhis = new VendorHistoryEntity(vendorList.get(0));
				vhis.setCreationTime(DateTimeUtility.getCurrentTimeStamp());
				vhis.setAction(StatusConstant.ACTION_DEACTIVATE_VENDOR);
				vhis.setStatus(StatusConstant.VENDOR_PENDING_STATUS);
				vendorHistoryRepository.save(vhis);
			}
			if (vendorPreAuthId == null || vendorPreAuthId <= 0 && (vendorList != null && !vendorList.isEmpty())) {
				VendorPreAuthEntity vendorPreauth = new VendorPreAuthEntity(vendorList.get(0));
				vendorPreauth.setAction(StatusConstant.ACTION_DEACTIVATE_VENDOR);
				vendorPreauth.setStatus(StatusConstant.VENDOR_PENDING_STATUS);
				vendorPreauth.setAuthorizedBy(null);
				vendorPreauth.setAuthorizedDate(null);
				vendorPreauth.setCreationTime(currentTimestamp);
				vendorPreauth.setIsVendorInactive(request.getIsVendorDeativate());
				vendorPreAuthRepository.save(vendorPreauth);
			} else {
				vendorPreAuthRepository.deactvateVendor(vendorCode, deactivateFlag, currentTimestamp);
			}
			responseDto.setStatus(StatusConstant.STATUS_SUCCESS);
			responseDto.setStatusCode(StatusConstant.STATUS_SUCCESS_CODE);
			responseDto.setMsg(StatusConstant.STATUS_DESCRIPTION_VENDOR_DEACTIVATED_SUCESSFULLY + vendorCode);
			log.info("Start Vendor deactivat for vendor master " + vendorCode);
			log.info("Start Vendor deactivat for vendor preauth " + vendorCode);
			log.info(StatusConstant.STATUS_DESCRIPTION_VENDOR_DEACTIVATED_SUCESSFULLY + vendorCode);
		} catch (Exception e) {
			log.error(StatusConstant.EXCEPTION_OCCURRED + e.getMessage());
			responseDto.setStatusCode(StatusConstant.STATUS_FAILURE_CODE);
			responseDto.setStatus(StatusConstant.STATUS_FAILURE);
			responseDto.setMsg(e.getMessage());
		}
		return responseDto;
	}

	public VendorDetailsResponseDto getAllVendorByImCode(String imCode) {
		VendorDetailsResponseDto response = new VendorDetailsResponseDto();
		List<VendorEntity> vendor = new ArrayList<>();
		try {
			vendorRepository.findByImCode(imCode).forEach(vendor::add);
			if (!vendor.isEmpty()) {
				response.setStatusCode(StatusConstant.STATUS_SUCCESS_CODE);
				response.setStatus(StatusConstant.STATUS_SUCCESS);
				response.setMsg(StatusConstant.STATUS_DESCRIPTION_VENDOR_LIST_RETRIVED_SUCESSFULLY);
				response.setData(vendor);
			} else {
				response.setStatusCode(StatusConstant.STATUS_DATA_NOT_FOUND_CODE);
				response.setStatus(StatusConstant.STATUS_FAILURE);
				response.setMsg(StatusConstant.STATUS_DATA_NOT_AVAILAIBLE);
				response.setData(vendor);
			}
		} catch (Exception e) {
			log.error(StatusConstant.EXCEPTION_OCCURRED + e.getMessage());
			response.setStatusCode(StatusConstant.STATUS_FAILURE_CODE);
			response.setStatus(StatusConstant.STATUS_FAILURE);
			response.setMsg(e.getMessage());
		}
		return response;
	}

	public ResponseDto activateVendor(VendoActivateRequest request) {
		ResponseDto responseDto = new ResponseDto();
		String vendorCode = request.getVendorCode();
		boolean activateFlag = request.getIsVendorInActive();
		Timestamp currentTimestamp = null;
		try {
			currentTimestamp = DateTimeUtility.getCurrentTimeStamp();
			log.info("Start Vendor activate for " + vendorCode);
			vendorRepository.actvateVendor(vendorCode, activateFlag, currentTimestamp);
			vendorPreAuthRepository.actvateVendor(vendorCode, activateFlag, currentTimestamp);
			log.info(StatusConstant.STATUS_DESCRIPTION_VENDOR_DEACTIVATED_SUCESSFULLY + vendorCode);
			responseDto.setStatus(StatusConstant.STATUS_SUCCESS);
			responseDto.setStatusCode(StatusConstant.STATUS_SUCCESS_CODE);
			responseDto.setMsg(StatusConstant.STATUS_DESCRIPTION_VENDOR_ACTIVATED_SUCESSFULLY + vendorCode);
		} catch (Exception e) {
			log.error(StatusConstant.EXCEPTION_OCCURRED + e.getMessage());
			responseDto.setStatusCode(StatusConstant.STATUS_FAILURE_CODE);
			responseDto.setStatus(StatusConstant.STATUS_FAILURE);
			responseDto.setMsg(e.getMessage());
		}
		return responseDto;
	}

	public VendorPreAuthResponse getAllUnAuthorisedVendor() {
		VendorPreAuthResponse responseDto = new VendorPreAuthResponse();
		try {
			List<VendorPreAuthEntity> vendorreAuthList = vendorPreAuthRepository.getAllUnAuthorisedVednor();
			if (!vendorreAuthList.isEmpty()) {
				responseDto.setStatusCode(StatusConstant.STATUS_SUCCESS_CODE);
				responseDto.setStatus(StatusConstant.STATUS_SUCCESS);
				responseDto.setMsg(StatusConstant.STATUS_DESCRIPTION_PREAUTH_VENDOR_RETRIVED_SUCESSFULLY);
				responseDto.setData(vendorreAuthList);
			} else {
				responseDto.setStatusCode(StatusConstant.STATUS_DATA_NOT_FOUND_CODE);
				responseDto.setStatus(StatusConstant.STATUS_FAILURE);
				responseDto.setMsg(StatusConstant.STATUS_DATA_NOT_AVAILAIBLE_FOR_APPROVE_IM);
			}
		} catch (Exception e) {
			log.error(StatusConstant.EXCEPTION_OCCURRED + e.getMessage());
			responseDto.setStatusCode(StatusConstant.STATUS_FAILURE_CODE);
			responseDto.setStatus(StatusConstant.STATUS_FAILURE);
			responseDto.setMsg(e.getMessage());
		}
		return responseDto;
	}

	public ResponseDto authoriseVendorByCheker(List<VendorEntity> approvedVendorList) {
		ResponseDto responseDto = new ResponseDto();
		try {
			if (approvedVendorList != null && !approvedVendorList.isEmpty()) {
				approvedVendorList.forEach(this::performAuthoriseAction);
				responseDto.setStatusCode(StatusConstant.STATUS_SUCCESS_CODE);
				responseDto.setStatus(StatusConstant.STATUS_SUCCESS);
				responseDto.setMsg(StatusConstant.STATUS_DESCRIPTION_PREAUTH_VENDOR_AUTHORISED_SUCESSFULLY);
			} else {
				responseDto.setStatusCode(StatusConstant.STATUS_DATA_NOT_FOUND_CODE);
				responseDto.setStatus(StatusConstant.STATUS_FAILURE);
				responseDto.setMsg(StatusConstant.STATUS_DATA_NOT_AVAILAIBLE);
			}
		} catch (Exception e) {
			log.error(StatusConstant.EXCEPTION_OCCURRED + e.getMessage());
			responseDto.setStatusCode(StatusConstant.STATUS_FAILURE_CODE);
			responseDto.setStatus(StatusConstant.STATUS_FAILURE);
			responseDto.setMsg(e.getMessage());
		}
		return responseDto;
	}

	public void performAuthoriseAction(VendorEntity vendor) {
		Timestamp currentTimestamp = null;
		try {
			currentTimestamp = DateTimeUtility.getCurrentTimeStamp();
			VendorPreAuthEntity vendorPreAuth = vendorPreAuthRepository
					.getVendorPreauthByVendorCode(vendor.getVendorCode());
			if (vendorPreAuth != null) {
				VendorEntity vData = new VendorEntity(vendorPreAuth);
				VendorHistoryEntity venHistory = new VendorHistoryEntity(vData);
				venHistory.setCreationTime(currentTimestamp);
				venHistory.setAuthorizedBy(vendor.getAuthorizedBy());
				venHistory.setAuthorizedDate(currentTimestamp);
				venHistory.setRemark(vendor.getRemark());
				venHistory.setStatus(StatusConstant.VENDOR_AUTHORISED_STATUS);
				/*
				 * if (Objects.equals(vendorPreAuth.getAction(),
				 * StatusConstant.ACTION_ADD_VENDOR)) { vData.setCreationTime(currentTimestamp);
				 * vData.setAction(StatusConstant.ACTION_ADD_IM);
				 * vData.setStatus(StatusConstant.VENDOR_AUTHORISED_STATUS);
				 * vendorRepository.save(vData); }
				 */
				if (Objects.equals(vendorPreAuth.getAction(), StatusConstant.ACTION_UPDATE_VENDOR)) {
					Long vendorSeqCode = vendorRepository.getVendorIDByImCode(vendor.getVendorCode());
					if (vendorSeqCode != null && vendorSeqCode > 0) {
						vData.setVendorSeqId(vendorSeqCode);
					}
					vData.setLastModificationDateTime(currentTimestamp);
					vData.setStatus(StatusConstant.VENDOR_AUTHORISED_STATUS);
					venHistory.setAction(StatusConstant.ACTION_UPDATE_VENDOR);
					vendorRepository.save(vData);
				}
				if (Objects.equals(vendorPreAuth.getAction(), StatusConstant.ACTION_DELETE_VENDOR)) {
					vendorRepository.deleteIMById(vendor.getVendorCode());
					venHistory.setAction(StatusConstant.ACTION_DELETE_VENDOR);
				}
				if (Objects.equals(vendorPreAuth.getAction(), StatusConstant.ACTION_DEACTIVATE_VENDOR)) {
					vendorRepository.deactvateVendor(vendor.getVendorCode(), vendorPreAuth.getIsVendorInactive(),
							currentTimestamp, StatusConstant.VENDOR_AUTHORISED_STATUS,
							StatusConstant.ACTION_DEACTIVATE_VENDOR);
					venHistory.setAction(StatusConstant.ACTION_DEACTIVATE_VENDOR);
				}
				vendorHistoryRepository.save(venHistory);
			}
			log.info("authorization started for im_master");
			vendorPreAuthRepository.deletePreAuthVendor(vendor.getVendorCode());
			vendorRepository.authoriseVendor(vendor.getVendorCode(), vendor.getStatus(), vendor.getRemark(),
					vendor.getAuthorizedBy(), currentTimestamp);
			log.info("authorization started for im_history");
		} catch (Exception e) {
			log.error(StatusConstant.EXCEPTION_OCCURRED + e.getMessage());
		}
	}

	private boolean checkPanAlreadyExistForAdd(String panCardNumber) {
		boolean isPanAlreadyExistWithIM = false;
		try {
			Long vendorPreAuthId = vendorPreAuthRepository.getVendorIDByPancard(panCardNumber);
			Long vendorMasterId = vendorRepository.getVendorIDByPancard(panCardNumber);
			if (vendorPreAuthId != null && vendorPreAuthId > 0) {
				isPanAlreadyExistWithIM = true;
			}
			if (vendorMasterId != null && vendorMasterId > 0) {
				isPanAlreadyExistWithIM = true;
			}
		} catch (Exception e) {
			log.info("Exception " + e.getMessage());
		}
		return isPanAlreadyExistWithIM;
	}

	private boolean checkPanAlreadyExistForUpdate(String panCardNumber, String vendorCode) {
		boolean isPanAlreadyExistWithIM = false;
		try {
			Long vendorPreAuthId = vendorPreAuthRepository.getVendorIDByPancard(panCardNumber);
			Long vendorMasterId = vendorRepository.getVendorIDByPancard(panCardNumber);
			String existPanForVendor = vendorRepository.getPanNumberByVendorCode(vendorCode);
			if (!existPanForVendor.equalsIgnoreCase(panCardNumber)) {
				if (vendorPreAuthId != null && vendorPreAuthId > 0) {
					isPanAlreadyExistWithIM = true;
				}
				if (vendorMasterId != null && vendorMasterId > 0) {
					isPanAlreadyExistWithIM = true;
				}
			}
		} catch (Exception e) {
			log.info("Exception " + e.getMessage());
		}
		return isPanAlreadyExistWithIM;
	}

/*
	public ResponseDto addBulkVendor(String id, MultipartFile file) throws IOException {
		List<String> contentList = null;
		String[] bulkVendorOnboardDetailList = null;
		VendorEntity bulkvendormapping = null;
		ResponseDto responsedto = new ResponseDto();
		//List<Long> duplicatevendorim = vendorRepository.checkDuplicateVendorIM();

		contentList = Arrays.asList(new String(file.getInputStream().readAllBytes()).split("\r\n"));

		contentList = contentList.stream().filter(cl -> !StringUtils.isEmpty(cl)).collect(Collectors.toList());

		System.out.println(contentList);
		List<VendorEntity> bulkvendetailslist =new ArrayList<VendorEntity>();

		List<VendorPreAuthEntity> bulkvendetailslist1 =new ArrayList<VendorPreAuthEntity>();
		for (String content : contentList) {
			bulkvendormapping = new VendorEntity();

			Optional<UserEntity> userEntityData = userEntityService.getEntityType("VENDOR");
			String prefix = "";
			if (userEntityData.isPresent()) {
				prefix = userEntityData.get().getPrefix();
				log.info("add Vendor started ");
				Long maxId = vendorRepository.getTopId();
				System.out.println("33");
				if (maxId == null) {
					System.out.println("33");
					Long stratValue = userEntityData.get().getStratValue();
					bulkvendormapping.setVendorSeqId(stratValue);
					bulkvendormapping.setVendorCode(prefix + stratValue);
				} else {
					System.out.println("33");
					bulkvendormapping.setVendorSeqId(maxId + 1);
					bulkvendormapping.setVendorCode(prefix + (maxId + 1));
				}
			}
			System.out.println("33");
			try {
				log.info("inside try");
				if (!content.contains("#")) {
					if (file.getContentType().equals("text/plain")) {
						bulkVendorOnboardDetailList = content.split("\\|");
					} else if (file.getContentType().equals("text/csv")) {
						bulkVendorOnboardDetailList = content.split(",");
					}
					
					log.info(bulkVendorOnboardDetailList[5]);
					log.info(bulkVendorOnboardDetailList[6]);
					
Name#Vendor Code#Address#City#District#State#Country#Pincode#Email ID#Mobile No.#Fax#Additional Field#Business Group#Credit Account Number with SBI/Other#Credit Account No#Branch Code/IFS Code#Default Credit Account Number with SBI/Other#Credit Account No#Branch Code/IFS Code            
Mohit1|VEN100|Dombivli Ameet CHS LTD|Dombivli|Thane|Maharashtra|India|422222|m.s@gmail.com|1234567890|12345|qqqq|wwww|12345|234556|2222|00000|123456
					
					
					bulkvendormapping.setName(bulkVendorOnboardDetailList[0]);
					bulkvendormapping.setAddress1(bulkVendorOnboardDetailList[1]);
					bulkvendormapping.setCity(bulkVendorOnboardDetailList[2]);
					bulkvendormapping.setDistrict(bulkVendorOnboardDetailList[3]);
					bulkvendormapping.setState(bulkVendorOnboardDetailList[4]);
				//	bulkvendormapping.setCountry(bulkVendorOnboardDetailList[5]);
					bulkvendormapping.setPincode(Integer.valueOf(bulkVendorOnboardDetailList[6]));
					bulkvendormapping.setEmail(bulkVendorOnboardDetailList[7]);
					bulkvendormapping.setPhoneNo(bulkVendorOnboardDetailList[8]);
					bulkvendormapping.setFax(bulkVendorOnboardDetailList[9]);
					bulkvendormapping.setBusinessGroup(bulkVendorOnboardDetailList[10]);
					log.info("name:" +bulkvendormapping.getName());
					String vendorCode= "V200";
					bulkvendormapping.setVendorCode(vendorCode);
					bulkvendormapping.setCreationTime(DateTimeUtility.getCurrentTimeStamp());

					bulkvendormapping.setStatus(StatusConstant.VENDOR_PENDING_STATUS);
					bulkvendormapping.setImCode(id);


					VendorPreAuthEntity preauthVendor = new VendorPreAuthEntity(bulkvendormapping);

					//bulkvendetailslist.add(bulkvendormapping);
					bulkvendetailslist1.add(preauthVendor);		

					responsedto.setStatusCode(StatusConstant.STATUS_SUCCESS_CODE);
					responsedto.setStatus(StatusConstant.STATUS_SUCCESS);
					responsedto.setMsg(StatusConstant.STATUS_DESCRIPTION_VENDOR_ADDED_SUCESSFULLY);
				}
			} catch (Exception e) {
				log.error(StatusConstant.EXCEPTION_OCCURRED + e.getMessage());
				responsedto.setStatusCode(StatusConstant.STATUS_FAILURE_CODE);
				responsedto.setStatus(StatusConstant.STATUS_FAILURE);
				responsedto.setMsg(e.getMessage());
			}
		}
 
		log.info("preauth vendor saved");
		//vendorPreAuthRepository.save(preauthVendor);
		//vendorPreAuthRepository.saveAll(bulkvendetailslist1);
		log.info("Vendor saved in master ");
		//vendorRepository.saveAll(bulkvendetailslist);
		//VendorHistoryEntity vendorHistory = new VendorHistoryEntity(bulkvendormapping);
		log.info("Vendor saved in history");
		//	vendorHistoryRepository.saveAll(bulkvendetailslist);
		//vendorRepository.saveAll(bulkvendetailslist);


		return responsedto;
	}
*/
	public ResponseDto addBulkVendor(String id, MultipartFile file) throws IOException {
		List<String> contentList = null;
		String[] bulkVendorOnboardDetailList = null;
		VendorEntity bulkvendormapping = null;
		VendorHistoryEntity vendorHistory = null;
		ResponseDto responsedto = new ResponseDto();
		try {
			if (file != null) {
				contentList = Arrays.asList(new String(file.getInputStream().readAllBytes()).split("\r\n"));
				contentList = contentList.stream().filter(cl -> !StringUtils.isEmpty(cl)).collect(Collectors.toList());
				List<VendorEntity> bulkvendetailslist = new ArrayList<>();
				List<VendorPreAuthEntity> bulkvendetailslist1 = new ArrayList<>();
				List<VendorHistoryEntity> bulkvendetailshistorylist = new ArrayList<>();
				
				for (String content : contentList) {
					bulkvendormapping = new VendorEntity();
					vendorHistory = new VendorHistoryEntity();
					
					Optional<UserEntity> userEntityData = userEntityService.getEntityType("VENDOR");
					String prefix = "";
					if (userEntityData.isPresent()) {
						prefix = userEntityData.get().getPrefix();
						log.info("add Vendor started ");
					 
						
						Long maxId = vendorRepository.getTopId();
						if (maxId == null) {
							Long stratValue = userEntityData.get().getStratValue();
							bulkvendormapping.setVendorSeqId(stratValue);
							bulkvendormapping.setVendorCode(prefix + stratValue);
						} else {
							bulkvendormapping.setVendorSeqId(maxId + 1);
							bulkvendormapping.setVendorCode(prefix + (maxId + 1));
						}
						
					}
					System.out.println("getVendorCode >>"+bulkvendormapping.getVendorCode());
					if (!content.contains("#")) {
						if (file.getContentType() != null) {
							if (file.getContentType().equals("text/plain")) {
								bulkVendorOnboardDetailList = content.split("\\|");
							}
							if (file.getContentType().equals("text/csv")) {
								bulkVendorOnboardDetailList = content.split(",");
							}
						}
			
						bulkvendormapping.setName(bulkVendorOnboardDetailList[0]);
						bulkvendormapping.setAddress1(bulkVendorOnboardDetailList[1]);
						bulkvendormapping.setCity(bulkVendorOnboardDetailList[2]);
						bulkvendormapping.setDistrict(bulkVendorOnboardDetailList[3]);
						bulkvendormapping.setState(bulkVendorOnboardDetailList[4]);
						bulkvendormapping.setCountry(bulkVendorOnboardDetailList[5]);
						bulkvendormapping.setPincode(Integer.valueOf(bulkVendorOnboardDetailList[6]));
						bulkvendormapping.setEmail(bulkVendorOnboardDetailList[7]);
						bulkvendormapping.setPhoneNo(bulkVendorOnboardDetailList[8]);
						bulkvendormapping.setFax(bulkVendorOnboardDetailList[9]);
						bulkvendormapping.setBusinessGroup(bulkVendorOnboardDetailList[10]);
						log.info("name:" + bulkvendormapping.getName());
						bulkvendormapping.setCreationTime(DateTimeUtility.getCurrentTimeStamp());
						bulkvendormapping.setStatus(StatusConstant.VENDOR_PENDING_STATUS);
						bulkvendormapping.setImCode(id);
						
						bulkvendormapping.setAction(StatusConstant.ACTION_ADD_VENDOR);
						bulkvendormapping.setStatus(StatusConstant.VENDOR_PENDING_STATUS);
						bulkvendormapping.setVendorOnboardedFromSourceId(StatusConstant.VENDOR_ONB_FROM_SCF);
						bulkvendormapping.setIsVendorAutoOnboarding("true");
						
						VendorPreAuthEntity preauthVendor = new VendorPreAuthEntity(bulkvendormapping);
						VendorHistoryEntity vendorHIstory = new VendorHistoryEntity(bulkvendormapping);
				
						
						bulkvendetailslist1.add(preauthVendor);
						bulkvendetailshistorylist.add(vendorHIstory);
						bulkvendetailslist.add(bulkvendormapping);
						
						responsedto.setStatusCode(StatusConstant.STATUS_SUCCESS_CODE);
						responsedto.setStatus(StatusConstant.STATUS_SUCCESS);
						responsedto.setMsg(StatusConstant.STATUS_DESCRIPTION_VENDOR_ADDED_SUCESSFULLY);
						log.info("preauth vendor saved");
						vendorPreAuthRepository.save(preauthVendor);
						log.info("Vendor saved in master ");
						vendorRepository.save(bulkvendormapping);
						log.info("Vendor saved in history");
						vendorHistoryRepository.save(vendorHIstory);
						
					}
				}
				 
				
//				vendorPreAuthRepository.saveAll(bulkvendetailslist1);
//	
//				vendorRepository.saveAll(bulkvendetailslist);
//
//				vendorHistoryRepository.saveAll(bulkvendetailshistorylist);
				
			}
		} catch (Exception e) {
			responsedto.setStatusCode(StatusConstant.STATUS_FAILURE_CODE);
			responsedto.setStatus(StatusConstant.STATUS_FAILURE);
			responsedto.setMsg(e.getMessage());
		}
		return responsedto;
	}
	
}