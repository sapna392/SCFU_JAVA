package onb.com.scf.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import onb.com.scf.constant.StatusConstant;
import onb.com.scf.datetimeutility.DateTimeUtility;
import onb.com.scf.dto.IMActivateRequest;
import onb.com.scf.dto.IMAndVendorResponse;
import onb.com.scf.dto.IMDeactivateReq;
import onb.com.scf.dto.IMDetailsResponseDto;
import onb.com.scf.dto.IMForApproveResponse;
import onb.com.scf.dto.ResponseDto;
import onb.com.scf.entity.IMEntity;
import onb.com.scf.entity.IMHistoryEntity;
import onb.com.scf.entity.IMPreauthEntity;
import onb.com.scf.entity.UserEntity;
import onb.com.scf.entity.VendorEntity;
import onb.com.scf.repository.IMHistoryRepository;
import onb.com.scf.repository.IMPreauthRepository;
import onb.com.scf.repository.IMRepository;
import onb.com.scf.repository.VendorRepository;
import onb.com.scf.service.IMService;

/**
 * @author Naseem
 *
 */
@Service
@Slf4j
public class IMServiceImpl implements IMService {
	@Autowired
	IMRepository imRepository;

	@Autowired
	IMHistoryRepository imHistoryRepository;

	@Autowired
	UserEntityService userEntityService;

	@Autowired
	IMPreauthRepository imPreauthRepository;
	
	@Autowired
	VendorRepository vendorRepository;

	/**
	 * Getting all im record by using the method findaAll()
	 * 
	 * @return im
	 */
	public IMDetailsResponseDto getAllIM() {
		IMDetailsResponseDto response = new IMDetailsResponseDto();
		List<IMEntity> im = new ArrayList<>();
		try {
			imRepository.findAll().forEach(im::add);
			if (!im.isEmpty()) {
				response.setStatusCode(StatusConstant.STATUS_SUCCESS_CODE);
				response.setStatus(StatusConstant.STATUS_SUCCESS);
				response.setMsg(StatusConstant.STATUS_DESCRIPTION_IM_LIST_RETRIVED_SUCESSFULLY);
				response.setData(im);
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
	 * @return im
	 */
	public IMDetailsResponseDto getIMByCode(String imCode) {
		IMDetailsResponseDto response = new IMDetailsResponseDto();
		try {
			List<IMEntity> im = imRepository.findByImCode(imCode);
			if (!im.isEmpty()) {
				response.setStatusCode(StatusConstant.STATUS_SUCCESS_CODE);
				response.setStatus(StatusConstant.STATUS_SUCCESS);
				response.setMsg(StatusConstant.STATUS_DESCRIPTION_IM_RETRIVED_SUCESSFULLY);
				response.setData(im);
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
	 * @param im
	 */
	public ResponseDto addImByMaker(IMEntity im) {
		ResponseDto responseDto = new ResponseDto();
		Optional<UserEntity> userEntityData = userEntityService.getEntityType("IM");
		String prefix = "";
		boolean isPanAlreadyExistWithIM = false;
		try {
			if (userEntityData.isPresent()) {
				if (im.getImPan() == null || im.getImPan().isEmpty()) {
					responseDto.setStatusCode(StatusConstant.STATUS_FAILURE_CODE);
					responseDto.setStatus(StatusConstant.STATUS_FAILURE);
					responseDto.setMsg(StatusConstant.STATUS_PAN_NUMBER_NOT_FOUND);
					return responseDto;
				}
				isPanAlreadyExistWithIM = checkPanAlreadyExistForAdd(im.getImPan());
				if (isPanAlreadyExistWithIM) {
					responseDto.setStatusCode(StatusConstant.STATUS_FAILURE_CODE);
					responseDto.setStatus(StatusConstant.STATUS_FAILURE);
					responseDto.setMsg(StatusConstant.PAN_CARD_ALREADY_MAPPED);
					return responseDto;
				}
				prefix = userEntityData.get().getPrefix();
				log.info("add IM started ");
				Long maxId = imRepository.getTopId();
				if (maxId == null) {
					Long stratValue = userEntityData.get().getStratValue();
					im.setImSeqId(stratValue);
					im.setImCode(prefix + stratValue);
				} else {
					im.setImSeqId(maxId + 1);
					im.setImCode(prefix + (maxId + 1));
				}
				im.setAction(StatusConstant.ACTION_ADD_IM);
				im.setCreationTime(DateTimeUtility.getCurrentTimeStamp());
				im.setStatus(StatusConstant.IM_PENDING_STATUS);

				IMPreauthEntity imPreAuth = new IMPreauthEntity(im);
				imPreAuth.setAction(StatusConstant.ACTION_ADD_IM);
				log.info("inserted in PREAUTH_MASTER");
				imPreauthRepository.save(imPreAuth);
				imRepository.save(im);
				IMHistoryEntity iHis = new IMHistoryEntity(im);
				log.info("inserted in IM_MASTER_HISTORY");
				imHistoryRepository.save(iHis);

				responseDto.setStatusCode(StatusConstant.STATUS_SUCCESS_CODE);
				responseDto.setStatus(StatusConstant.STATUS_SUCCESS);
				responseDto.setMsg(StatusConstant.STATUS_DESCRIPTION_IM_ADDED_SUCESSFULLY);
			} else {
				responseDto.setStatusCode(StatusConstant.STATUS_FAILURE_CODE);
				responseDto.setStatus(StatusConstant.STATUS_FAILURE);
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

	public synchronized Long getTopId() {
		return imRepository.getTopId();
	}

	public synchronized String getTopIMCode() {
		return imPreauthRepository.getTopIMCode();
	}

	public ResponseDto updateImByMaker(IMEntity im) {
		ResponseDto responseDto = new ResponseDto();
		Timestamp currentTimestamp = null;
		boolean isPanAlreadyExistWithIM = false;
		try {
			currentTimestamp = DateTimeUtility.getCurrentTimeStamp();
			if (im.getImCode() == null || im.getImCode().isEmpty()) {
				responseDto.setStatusCode(StatusConstant.STATUS_FAILURE_CODE);
				responseDto.setStatus(StatusConstant.STATUS_FAILURE);
				responseDto.setMsg(StatusConstant.STATUS_IM_CODE_NOT_FOUND);
				return responseDto;
			}
			if (im.getImPan() == null || im.getImPan().isEmpty()) {
				responseDto.setStatusCode(StatusConstant.STATUS_FAILURE_CODE);
				responseDto.setStatus(StatusConstant.STATUS_FAILURE);
				responseDto.setMsg(StatusConstant.STATUS_PAN_NUMBER_NOT_FOUND);
				return responseDto;
			}
			/*
			 * isPanAlreadyExistWithIM = checkPanAlreadyExistForUpdate(im.getImPan(),
			 * im.getImCode());
			 * 
			 * if (isPanAlreadyExistWithIM) {
			 * responseDto.setStatusCode(StatusConstant.STATUS_FAILURE_CODE);
			 * responseDto.setStatus(StatusConstant.STATUS_FAILURE);
			 * responseDto.setMsg(StatusConstant.PAN_CARD_ALREADY_MAPPED); return
			 * responseDto; }
			 */
			List<IMEntity> im2 = imRepository.findByImCode(im.getImCode());
			im.setImSeqId(im2.get(0).getImSeqId());
			im.setLastModificationDateTime(currentTimestamp);
			Long imPreAuthId = imPreauthRepository.getIMPreauthId(im2.get(0).getImCode());
			IMPreauthEntity imPreAuth = new IMPreauthEntity(im);
			imPreAuth.setAction(StatusConstant.ACTION_UPDATE_IM);
			imPreAuth.setStatus(StatusConstant.IM_PENDING_STATUS);
			imPreAuth.setCreationTime(currentTimestamp);
			if (imPreAuthId != null && imPreAuthId != 0) {
				imPreAuth.setImPreauthId(imPreAuthId);
			}
			imPreauthRepository.save(imPreAuth);
			IMHistoryEntity ihistory = new IMHistoryEntity(im2.get(0));
			ihistory.setCreationTime(DateTimeUtility.getCurrentTimeStamp());
			ihistory.setAction(StatusConstant.ACTION_UPDATE_IM);
			ihistory.setStatus(StatusConstant.IM_PENDING_STATUS);
			imHistoryRepository.save(ihistory);
			log.info("INSERT in PREAUTH_MASTER WHILE UPDATING IM");

			log.info("updated in IM_MASTER");
			// Im details added to onb_im_master_history
			log.info("updated in IM_MASTER_HISTORY");
			log.info(StatusConstant.STATUS_IM_UPDATED_SUCCESSFULLY);

			responseDto.setStatusCode(StatusConstant.STATUS_SUCCESS_CODE);
			responseDto.setStatus(StatusConstant.STATUS_SUCCESS);
			responseDto.setMsg(StatusConstant.STATUS_IM_UPDATED_SUCCESSFULLY);
		} catch (Exception e) {
			log.error(StatusConstant.EXCEPTION_OCCURRED + e.getMessage());
			responseDto.setStatusCode(StatusConstant.STATUS_FAILURE_CODE);
			responseDto.setStatus(StatusConstant.STATUS_FAILURE);
			responseDto.setMsg(e.getMessage());
		}
		return responseDto;
	}

	/**
	 * Activating IM by using IMCode
	 */
	public ResponseDto activeIM(IMActivateRequest request) {
		ResponseDto responseDto = new ResponseDto();
		String imCode = request.getImCode();
		Timestamp currentTimestamp = null;
		try {
			currentTimestamp = DateTimeUtility.getCurrentTimeStamp();
			imRepository.activateIm(imCode, request.getIsImInactive(), currentTimestamp);
			imPreauthRepository.activateIm(imCode, request.getIsImInactive(), currentTimestamp);
			imHistoryRepository.activateIm(imCode, request.getIsImInactive(), currentTimestamp);
			log.info(StatusConstant.STATUS_IM_DEACTIVATED_SUCCESSFULLY + imCode);
			responseDto.setStatusCode(StatusConstant.STATUS_SUCCESS_CODE);
			responseDto.setStatus(StatusConstant.STATUS_SUCCESS);
			responseDto.setMsg(StatusConstant.STATUS_IM_ACTIVATED_SUCCESSFULLY + imCode);
		} catch (Exception e) {
			log.error(StatusConstant.EXCEPTION_OCCURRED + e.getMessage());
			responseDto.setStatusCode(StatusConstant.STATUS_FAILURE_CODE);
			responseDto.setStatus(StatusConstant.STATUS_FAILURE);
			responseDto.setMsg(e.getMessage());
		}
		return responseDto;
	}

	public ResponseDto validateIMOBNumberAndEmail(IMActivateRequest request) {
		ResponseDto responseDto = new ResponseDto();
		try {
			// here we will call sms service and eamil service for validate the same
		} catch (Exception e) {
			log.error(StatusConstant.EXCEPTION_OCCURRED + e.getMessage());
			responseDto.setMsg(e.getMessage());
		}
		return responseDto;
	}

	public IMForApproveResponse getAllUnAuthorisedTxnOfIM() {
		IMForApproveResponse responseDto = new IMForApproveResponse();
		try {
			List<IMPreauthEntity> impreAuthList = imPreauthRepository.getAllUnAuthorisedIM();
			if (!impreAuthList.isEmpty()) {
				responseDto.setStatusCode(StatusConstant.STATUS_SUCCESS_CODE);
				responseDto.setStatus(StatusConstant.STATUS_SUCCESS);
				responseDto.setMsg(StatusConstant.STATUS_DESCRIPTION_PREAUTH_IM_RETRIVED_SUCESSFULLY);
				responseDto.setData(impreAuthList);
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

	public ResponseDto deleteIMByMaker(String imCode) {
		ResponseDto responseDto = new ResponseDto();
		IMHistoryEntity ihis = null;
		try {
			log.info("delete IM started for given imcode " + imCode);
			Long imPreAuthId = imPreauthRepository.getIMPreauthId(imCode);
			List<IMEntity> imList = imRepository.findByImCode(imCode);
			if (imPreAuthId != null && imPreAuthId != 0) {
				imPreauthRepository.deletePreAuthIM(imCode);
				imRepository.deleteIMById(imCode);
			} else {
				if (imList != null && !imList.isEmpty()) {
					IMPreauthEntity imPreAuth = new IMPreauthEntity(imList.get(0));
					imPreAuth.setAction(StatusConstant.ACTION_DELETE_IM);
					imPreAuth.setStatus(StatusConstant.IM_PENDING_STATUS);
					imPreAuth.setCreationTime(DateTimeUtility.getCurrentTimeStamp());
					imPreAuth.setAuthorizedBy(null);
					imPreAuth.setAuthorizedDate(null);
					imPreauthRepository.save(imPreAuth);
					responseDto.setStatusCode(StatusConstant.STATUS_SUCCESS_CODE);
					responseDto.setStatus(StatusConstant.STATUS_SUCCESS);
					responseDto.setMsg(StatusConstant.STATUS_IM_DEACTIVATED_SUCCESSFULLY + imCode);
				}
			}
			if (imList != null && !imList.isEmpty()) {
				ihis = new IMHistoryEntity(imList.get(0));
				ihis.setCreationTime(DateTimeUtility.getCurrentTimeStamp());
				ihis.setAction(StatusConstant.ACTION_DELETE_IM);
				ihis.setStatus(StatusConstant.IM_PENDING_STATUS);
				imHistoryRepository.save(ihis);
			}
			responseDto.setStatusCode(StatusConstant.STATUS_SUCCESS_CODE);
			responseDto.setStatus(StatusConstant.STATUS_SUCCESS);
			responseDto.setMsg(StatusConstant.STATUS_IM_DELETED_SUCCESSFULLY + imCode);
		} catch (Exception e) {
			log.error(StatusConstant.EXCEPTION_OCCURRED + e.getMessage());
			responseDto.setStatusCode(StatusConstant.STATUS_FAILURE_CODE);
			responseDto.setStatus(StatusConstant.STATUS_FAILURE);
			responseDto.setMsg(e.getMessage());
		}
		return responseDto;
	}

	public ResponseDto deactivateIMByMaker(IMDeactivateReq request) {
		ResponseDto responseDto = new ResponseDto();
		String imCode = request.getImCode();
		Timestamp currentTimestamp = null;
		IMHistoryEntity ihis = null;
		try {
			currentTimestamp = DateTimeUtility.getCurrentTimeStamp();
			List<IMEntity> imList = imRepository.findByImCode(imCode);
			Long imPreAuthId = imPreauthRepository.getIMPreauthId(imCode);
			if (imList != null && !imList.isEmpty()) {
				ihis = new IMHistoryEntity(imList.get(0));
				ihis.setCreationTime(DateTimeUtility.getCurrentTimeStamp());
				ihis.setAction(StatusConstant.ACTION_DEACTIVATE_IM);
				ihis.setStatus(StatusConstant.IM_PENDING_STATUS);
				imHistoryRepository.save(ihis);
			}
			if (imPreAuthId == null || imPreAuthId <= 0) {
				if (imList != null && !imList.isEmpty()) {
					IMPreauthEntity imPreAuth = new IMPreauthEntity(imList.get(0));
					imPreAuth.setAction(StatusConstant.ACTION_DEACTIVATE_IM);
					imPreAuth.setStatus(StatusConstant.IM_PENDING_STATUS);
					imPreAuth.setAuthorizedBy(null);
					imPreAuth.setAuthorizedDate(null);
					imPreAuth.setCreationTime(currentTimestamp);
					imPreAuth.setIsImInactive(request.getIsImInactive());
					imPreauthRepository.save(imPreAuth);
				}
			} else {
				imPreauthRepository.isImInactive(imCode, request.getIsImInactive(), currentTimestamp,
						StatusConstant.ACTION_DEACTIVATE_IM_STATUS, StatusConstant.ACTION_DEACTIVATE_IM);
			}
			responseDto.setStatusCode(StatusConstant.STATUS_SUCCESS_CODE);
			responseDto.setStatus(StatusConstant.STATUS_SUCCESS);
			responseDto.setMsg(StatusConstant.STATUS_IM_DEACTIVATED_SUCCESSFULLY + imCode);
			log.info(StatusConstant.STATUS_IM_NOT_DEACTIVATE + imCode);
		} catch (Exception e) {
			log.error(StatusConstant.EXCEPTION_OCCURRED + e.getMessage());
			responseDto.setStatusCode(StatusConstant.STATUS_FAILURE_CODE);
			responseDto.setStatus(StatusConstant.STATUS_FAILURE);
			responseDto.setMsg(e.getMessage());
		}
		return responseDto;
	}

	public ResponseDto authoriseIM(List<IMEntity> approvedIMList) {
		ResponseDto responseDto = new ResponseDto();
		try {
			if (approvedIMList != null && !approvedIMList.isEmpty()) {
				approvedIMList.forEach(this::performAuthoriseAction);
				responseDto.setStatusCode(StatusConstant.STATUS_SUCCESS_CODE);
				responseDto.setStatus(StatusConstant.STATUS_SUCCESS);
				responseDto.setMsg(StatusConstant.STATUS_DESCRIPTION_PREAUTH_IM_AUTHORISED_SUCESSFULLY);
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

	public void performAuthoriseAction(IMEntity im) {
		Timestamp currentTimestamp = null;
		try {
			currentTimestamp = DateTimeUtility.getCurrentTimeStamp();
			IMPreauthEntity impreAUth = imPreauthRepository.getIMPreauthByImCode(im.getImCode());
			if (impreAUth != null) {
				IMEntity imData = new IMEntity(impreAUth);
				IMHistoryEntity ihistory = new IMHistoryEntity(imData);
				ihistory.setCreationTime(currentTimestamp);
				ihistory.setAuthorizedBy(im.getAuthorizedBy());
				ihistory.setAuthorizedDate(currentTimestamp);
				ihistory.setRemark(im.getRemark());
				ihistory.setStatus(StatusConstant.IM_AUTHORISED_STATUS);
				if (Objects.equals(impreAUth.getAction(), StatusConstant.ACTION_ADD_IM)) {
					/*
					 * imData.setStatus(StatusConstant.IM_AUTHORISED_STATUS);
					 * imData.setAction(StatusConstant.ACTION_ADD_IM);
					 * imData.setCreationTime(currentTimestamp);
					 * ihistory.setAction(StatusConstant.IM_AUTHORISED_STATUS);
					 * imRepository.save(imData);
					 */
				}
				if (Objects.equals(impreAUth.getAction(), StatusConstant.ACTION_UPDATE_IM)) {
					Long imId = imRepository.getIMIDByImCode(im.getImCode());
					if (imId != null && imId > 0) {
						imData.setImSeqId(imId);
					}
					imData.setLastModificationDateTime(currentTimestamp);
					imData.setStatus(StatusConstant.IM_AUTHORISED_STATUS);
					ihistory.setAction(StatusConstant.ACTION_UPDATE_IM);
					imRepository.save(imData);
				}
				if (Objects.equals(impreAUth.getAction(), StatusConstant.ACTION_DELETE_IM)) {
					imRepository.deleteIMById(im.getImCode());
					ihistory.setAction(StatusConstant.ACTION_DELETE_IM);
				}
				if (Objects.equals(impreAUth.getAction(), StatusConstant.ACTION_DEACTIVATE_IM)) {
					imRepository.isImInactive(im.getImCode(), impreAUth.getIsImInactive(), currentTimestamp,
							StatusConstant.IM_AUTHORISED_STATUS, StatusConstant.ACTION_DEACTIVATE_IM);
					ihistory.setAction(StatusConstant.ACTION_DEACTIVATE_IM);
				}
				imHistoryRepository.save(ihistory);
			}
			imRepository.authoriseIM(im.getImCode(), im.getStatus(), im.getRemark(), im.getAuthorizedBy(),
					currentTimestamp);
			log.info("authorization started for im_master");
			imPreauthRepository.deletePreAuthIM(im.getImCode());
			log.info("authorization started for im_history");

		} catch (Exception e) {
			log.error(StatusConstant.EXCEPTION_OCCURRED + e.getMessage());
		}
	}

	private boolean checkPanAlreadyExistForUpdate(String panCardNumber, String imCode) {
		boolean isPanAlreadyExistWithIM = false;
		try {
			Long imSeqId = imPreauthRepository.getIMIDByPancard(panCardNumber);
			Long imMasterId = imRepository.getIMIDByPancard(panCardNumber);
			String existPanForIM = imRepository.getPanNumberByIMCode(imCode);

			if (!existPanForIM.equalsIgnoreCase(panCardNumber)) {
				if (imSeqId != null && imSeqId > 0) {
					isPanAlreadyExistWithIM = true;
				}
				if (imMasterId != null && imMasterId > 0) {
					isPanAlreadyExistWithIM = true;
				}
			}
		} catch (Exception e) {
			log.info("Exception " + e.getMessage());
		}
		return isPanAlreadyExistWithIM;
	}

	private boolean checkPanAlreadyExistForAdd(String panCardNumber) {
		boolean isPanAlreadyExistWithIM = false;
		try {
			Long imSeqId = imPreauthRepository.getIMIDByPancard(panCardNumber);
			Long imMasterId = imRepository.getIMIDByPancard(panCardNumber);
			if (imSeqId != null && imSeqId > 0) {
				isPanAlreadyExistWithIM = true;
			}
			if (imMasterId != null && imMasterId > 0) {
				isPanAlreadyExistWithIM = true;
			}
		} catch (Exception e) {
			log.info("Exception " + e.getMessage());
		}
		return isPanAlreadyExistWithIM;
	}
	
	public IMAndVendorResponse getAllIMCodeAndVendorCode(short identifier) {
		IMAndVendorResponse responseDto = new IMAndVendorResponse();
		List<IMEntity> imList = new ArrayList<>();
		List<VendorEntity> vendorList = new ArrayList<>();
		try {
			if(identifier == 0) {
				responseDto.setStatusCode(StatusConstant.STATUS_DATA_NOT_FOUND_CODE);
				responseDto.setStatus(StatusConstant.STATUS_FAILURE);
				responseDto.setMsg(StatusConstant.DATA_IDENTIFIER_NOT_FOUND);
			}
			if(identifier == StatusConstant.IM_DATA_IDENTIFIER) {
				imRepository.findAll().forEach(imList::add);
				responseDto.setStatusCode(StatusConstant.STATUS_SUCCESS_CODE);
				responseDto.setStatus(StatusConstant.STATUS_SUCCESS);
				responseDto.setMsg(StatusConstant.STATUS_DESCRIPTION_IM_LIST_RETRIVED_SUCESSFULLY);
				if(imList.isEmpty()) {
					responseDto.setStatusCode(StatusConstant.STATUS_DATA_NOT_FOUND_CODE);
					responseDto.setStatus(StatusConstant.STATUS_FAILURE);
					responseDto.setMsg(StatusConstant.STATUS_DATA_NOT_AVAILAIBLE);
					return responseDto;
				}
				responseDto.setImdata(imList);
			}
			if(identifier == StatusConstant.VENDOR_DATA_IDENTIFIER) {
				vendorRepository.findAll().forEach(vendorList::add);
				responseDto.setStatusCode(StatusConstant.STATUS_SUCCESS_CODE);
				responseDto.setStatus(StatusConstant.STATUS_SUCCESS);
				responseDto.setMsg(StatusConstant.STATUS_DESCRIPTION_VENDOR_LIST_RETRIVED_SUCESSFULLY);
				if(vendorList.isEmpty()) {
					responseDto.setStatusCode(StatusConstant.STATUS_DATA_NOT_FOUND_CODE);
					responseDto.setStatus(StatusConstant.STATUS_FAILURE);
					responseDto.setMsg(StatusConstant.STATUS_DATA_NOT_AVAILAIBLE);
					return responseDto;
				}
				responseDto.setVendorData(vendorList);
			}
		}catch(Exception e) {
			log.info("Exception " + e.getMessage());
		}
		return responseDto;
	}
}