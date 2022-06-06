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
import onb.com.scf.entity.UserEntity;
import onb.com.scf.entity.VendorEntity;
import onb.com.scf.repository.IMRepository;
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
				response.setListOfVendor(vendor);
			} else {
				response.setStatusCode(StatusConstant.STATUS_FAILURE_CODE);
				response.setStatus(StatusConstant.STATUS_FAILURE);
				response.setMsg(StatusConstant.STATUS_DATA_NOT_AVAILAIBLE);
			}
		} catch (Exception e) {
			log.error(StatusConstant.EXCEPTION_OCCURRED + e.getMessage());
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
				response.setListOfVendor(vendorList);
			} else {
				response.setStatusCode(StatusConstant.STATUS_FAILURE_CODE);
				response.setStatus(StatusConstant.STATUS_FAILURE);
				response.setMsg(StatusConstant.STATUS_DATA_NOT_AVAILAIBLE);
			}
		} catch (Exception e) {
			log.error(StatusConstant.EXCEPTION_OCCURRED + e.getMessage());
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
				vendorRepository.save(vendor);
				responseDto.setStatus(StatusConstant.STATUS_SUCCESS);
				responseDto.setStatusCode(StatusConstant.STATUS_SUCCESS_CODE);
				responseDto.setMsg(StatusConstant.STATUS_DESCRIPTION_VENDOR_ADDED_SUCESSFULLY);
			} else {
				responseDto.setStatus(StatusConstant.STATUS_FAILURE);
				responseDto.setStatusCode(StatusConstant.STATUS_FAILURE_CODE);
				responseDto.setMsg(StatusConstant.STATUS_PREFIX_NOT_AVAILAIBLE);
			}
		} catch (Exception e) {
			log.error(StatusConstant.EXCEPTION_OCCURRED + e.getMessage());
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
				response.setListOfVendor(vendor);
			} else {
				response.setStatusCode(StatusConstant.STATUS_FAILURE_CODE);
				response.setStatus(StatusConstant.STATUS_FAILURE);
				response.setMsg(StatusConstant.STATUS_DATA_NOT_AVAILAIBLE);
			}
		} catch (Exception e) {
			log.error(StatusConstant.EXCEPTION_OCCURRED + e.getMessage());
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
			responseDto.setMsg(e.getMessage());
		}
		return responseDto;
	}
}