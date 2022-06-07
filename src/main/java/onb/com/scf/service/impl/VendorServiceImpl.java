package onb.com.scf.service.impl;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import onb.com.scf.constant.StatusConstant;
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

	/**
	 * Saving a specific record by using the method save()
	 * 
	 * @param vendor
	 */
	public ResponseDto addVendor(VendorEntity vendor) {
		ResponseDto responseDto = new ResponseDto();

		Optional<UserEntity> userEntityData = userEntityService.getEntityType("VENDOR");
		String prefix = "";
		try {
			if (userEntityData.isPresent()) {
				prefix = userEntityData.get().getPrefix();
				log.info("add Vendor started ");
				Long maxId = vendorRepository.getTopId();
				if (maxId == null) {
					Long stratValue = userEntityData.get().getStratValue();
					vendor.setVendorSeqCode(stratValue);
					vendor.setVendorCode(prefix + stratValue);
				} else {
					vendor.setVendorSeqCode(maxId + 1);
					vendor.setVendorCode(prefix + (maxId + 1));
				}
				vendor.setCreationTime(Date.valueOf(LocalDate.now()));
				vendor.setStatus(StatusConstant.VENDOR_PENDING_STATUS);

				VendorPreAuthEntity preauthVendor = new VendorPreAuthEntity(vendor);
				log.info("preauth vendor saved");
				vendorPreAuthRepository.save(preauthVendor);
				log.info("Vendor saved in master ");
				vendorRepository.save(vendor);
				VendorHistoryEntity vendorHistory = new VendorHistoryEntity(vendor);
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

	/**
	 * Deleting a specific record by using the method deleteById()
	 * 
	 * @param id
	 */
	public ResponseDto deleteById(String vendorId) {
		ResponseDto responseDto = new ResponseDto();
		try {
			log.info("delete Vendor started for given VendorCode " + vendorId);
			vendorRepository.deleteIMById(vendorId);
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
	public void update(VendorEntity vendor) {
		log.info("update vendor details  ");
		try {
			vendor.setLastModTime(Date.valueOf(LocalDate.now()));
			vendorRepository.save(vendor);
			VendorPreAuthEntity preauthVendor = new VendorPreAuthEntity(vendor);
			log.info("preauth vendor updated");
			vendorPreAuthRepository.save(preauthVendor);
			VendorHistoryEntity vendorHistory = new VendorHistoryEntity(vendor);
			log.info("Vendor saved in history");
			vendorHistoryRepository.save(vendorHistory);
			log.info(StatusConstant.STATUS_DESCRIPTION_VENDOR_UPDATED_SUCESSFULLY);
		} catch (Exception e) {
			log.error(StatusConstant.EXCEPTION_OCCURRED + e.getMessage());
		}
	}

	@Override
	public ResponseDto deActivate(VendorDeactivateRequest request) {
		ResponseDto responseDto = new ResponseDto();
		String vendorCode = request.getVendorCode();
		boolean deactivateFlag = request.getIsVendorDeativate();
		try {
			log.info("Start Vendor deactivat for " + vendorCode);
			vendorRepository.deactvateVendor(vendorCode, deactivateFlag, Date.valueOf(LocalDate.now()));
			log.info(StatusConstant.STATUS_DESCRIPTION_VENDOR_DEACTIVATED_SUCESSFULLY + vendorCode);
			responseDto.setStatus(StatusConstant.STATUS_SUCCESS);
			responseDto.setStatusCode(StatusConstant.STATUS_SUCCESS_CODE);
			responseDto.setMsg(StatusConstant.STATUS_DESCRIPTION_VENDOR_DEACTIVATED_SUCESSFULLY + vendorCode);
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
		try {
			log.info("Start Vendor activate for " + vendorCode);
			vendorRepository.actvateVendor(vendorCode, activateFlag, Date.valueOf(LocalDate.now()));
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

	public ResponseDto authoriseVendor(List<VendorEntity> approvedVendorList) {
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
		try {
			log.info("authorization started for im_master");
			vendorPreAuthRepository.deletePreAuthVendor(vendor.getVendorCode());
			vendorRepository.authoriseVendor(vendor.getVendorCode(), vendor.getStatus(), vendor.getRemark(),
					vendor.getAuthorizedBy(), Date.valueOf(LocalDate.now()));
			log.info("authorization started for im_history");
			vendorHistoryRepository.authoriseVendor(vendor.getVendorCode(), vendor.getStatus(), vendor.getRemark(),
					vendor.getAuthorizedBy(), Date.valueOf(LocalDate.now()));
		} catch (Exception e) {
			log.error(StatusConstant.EXCEPTION_OCCURRED + e.getMessage());
		}
	}
}