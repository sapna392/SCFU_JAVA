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
import onb.com.scf.dto.IMActivateRequest;
import onb.com.scf.dto.IMDeactivateReq;
import onb.com.scf.dto.IMDetailsResponseDto;
import onb.com.scf.dto.IMForApproveResponse;
import onb.com.scf.dto.ResponseDto;
import onb.com.scf.entity.IMEntity;
import onb.com.scf.entity.IMHistoryEntity;
import onb.com.scf.entity.IMPreauthEntity;
import onb.com.scf.entity.UserEntity;
import onb.com.scf.repository.IMHistoryRepository;
import onb.com.scf.repository.IMPreauthRepository;
import onb.com.scf.repository.IMRepository;
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
	public ResponseDto addIm(IMEntity im) {
		ResponseDto responseDto = new ResponseDto();
		Optional<UserEntity> userEntityData = userEntityService.getEntityType("IM");
		String prefix = "";
		try {
			if (userEntityData.isPresent()) {
				prefix = userEntityData.get().getPrefix();
				log.info("add IM started ");
				Long maxId = imRepository.getTopId();
				if (maxId == null) {
					Long stratValue = userEntityData.get().getStratValue();
					im.setImId(stratValue);
					im.setImCode(prefix + stratValue);
				} else {
					im.setImId(maxId + 1);
					im.setImCode(prefix + (maxId + 1));
				}
				im.setCreationTime(Date.valueOf(LocalDate.now()));
				im.setStatus(StatusConstant.IM_PENDING_STATUS);

				IMPreauthEntity imPreAuth = new IMPreauthEntity(im);
				log.info("inserted in PREAUTH_MASTER");
				imPreauthRepository.save(imPreAuth);
				log.info("inserted in IM_MASTER");
				imRepository.save(im);
				// Im details added to onb_im_master_history
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

	/**
	 * Deleting a specific record by using the method deleteById()
	 * 
	 * @param id
	 */
	public ResponseDto delete(String imCode) {
		ResponseDto responseDto = new ResponseDto();
		try {
			log.info("delete IM started for given imcode " + imCode);
			imRepository.deleteIMById(imCode);
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

	public Long getTopId() {
		return imRepository.getTopId();
	}

	public ResponseDto isImInactive(IMDeactivateReq request) {
		ResponseDto responseDto = new ResponseDto();
		String imCode = request.getImCode();
		try {
			imRepository.isImInactive(imCode, request.getIsImInactive(), Date.valueOf(LocalDate.now()));
			log.info(StatusConstant.STATUS_IM_DEACTIVATED_SUCCESSFULLY + imCode);
			responseDto.setStatusCode(StatusConstant.STATUS_SUCCESS_CODE);
			responseDto.setStatus(StatusConstant.STATUS_SUCCESS);
			responseDto.setMsg(StatusConstant.STATUS_IM_DEACTIVATED_SUCCESSFULLY + imCode);
		} catch (Exception e) {
			log.error(StatusConstant.EXCEPTION_OCCURRED + e.getMessage());
			responseDto.setStatusCode(StatusConstant.STATUS_FAILURE_CODE);
			responseDto.setStatus(StatusConstant.STATUS_FAILURE);
			responseDto.setMsg(e.getMessage());
		}
		return responseDto;
	}

	public ResponseDto updateIm(IMEntity im) {
		ResponseDto responseDto = new ResponseDto();
		try {
			if (im.getImCode() == null || im.getImCode().isEmpty()) {
				responseDto.setStatusCode(StatusConstant.STATUS_FAILURE_CODE);
				responseDto.setStatus(StatusConstant.STATUS_FAILURE);
				responseDto.setMsg(StatusConstant.STATUS_IM_CODE_NOT_FOUND);
				return responseDto;
			}
			List<IMEntity> im2 = imRepository.findByImCode(im.getImCode());
			im.setImId(im2.get(0).getImId());
			log.info("updated in PREAUTH_MASTER");
			IMPreauthEntity imPreAuth = new IMPreauthEntity(im);
			imPreauthRepository.save(imPreAuth);
			log.info("updated in IM_MASTER");
			imRepository.save(im);
			// Im details added to onb_im_master_history
			IMHistoryEntity iHis = new IMHistoryEntity(im);
			log.info("updated in IM_MASTER_HISTORY");
			imHistoryRepository.save(iHis);
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
		try {
			imRepository.activateIm(imCode, request.getIsImInactive(), Date.valueOf(LocalDate.now()));
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

	public IMForApproveResponse getAllUnAuthorisedIM() {
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
		try {
			log.info("authorization started for im_master");
			imPreauthRepository.deletePreAuthIM(im.getImCode());
			imRepository.authoriseIM(im.getImCode(), im.getStatus(), im.getRemark(), im.getAuthorizedBy(),
					Date.valueOf(LocalDate.now()));
			log.info("authorization started for im_history");
			imHistoryRepository.authoriseIM(im.getImCode(), im.getStatus(), im.getRemark(), im.getAuthorizedBy(),
					Date.valueOf(LocalDate.now()));
		} catch (Exception e) {
			log.error(StatusConstant.EXCEPTION_OCCURRED + e.getMessage());
		}
	}
}