package com.scf.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scf.dto.ResponseDto;
import com.scf.dto.VendorDeactivateRequest;
import com.scf.dto.VendorDetailsResponseDto;
import com.scf.model.UserEntity;
import com.scf.model.Vendor;
import com.scf.repository.IMRepository;
import com.scf.repository.VendorRepository;
import com.scf.service.VendorService;
import com.scf.status.constant.StatusConstant;

import lombok.extern.slf4j.Slf4j;

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
		List<Vendor> vendor 			  = new ArrayList<>();
		try {
			vendorRepository.findAll().forEach(vendor::add);
			if(!vendor.isEmpty()) {
				response.setStatusCode(StatusConstant.STATUS_SUCCESS_CODE);
				response.setStatus(StatusConstant.STATUS_SUCCESS);
				response.setMsg(StatusConstant.STATUS_DESCRIPTION_VENDOR_LIST_RETRIVED_SUCESSFULLY);
				response.setListOfVendor(vendor);
			}
			else {
				response.setStatusCode(StatusConstant.STATUS_FAILURE_CODE);
				response.setStatus(StatusConstant.STATUS_FAILURE);
				response.setMsg(StatusConstant.STATUS_DATA_NOT_AVAILAIBLE);
			}
		}
		catch(Exception e) {
			log.error(StatusConstant.EXCEPTION_OCCURRED +e.getMessage() );
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
		VendorDetailsResponseDto response= new VendorDetailsResponseDto();
		try {
			List<Vendor> vendorList =vendorRepository.findByVendorCode(id);
			if(!vendorList.isEmpty()) {
				response.setStatusCode(StatusConstant.STATUS_SUCCESS_CODE);
				response.setStatus(StatusConstant.STATUS_SUCCESS);
				response.setMsg(StatusConstant.STATUS_DESCRIPTION_VENDOR_RETRIVED_SUCESSFULLY+id);
				response.setListOfVendor(vendorList);
			}
			else {
				response.setStatusCode(StatusConstant.STATUS_FAILURE_CODE);
				response.setStatus(StatusConstant.STATUS_FAILURE);
				response.setMsg(StatusConstant.STATUS_DATA_NOT_AVAILAIBLE);
			}
		}
		catch(Exception e) {
			log.error(StatusConstant.EXCEPTION_OCCURRED +e.getMessage() );
			response.setMsg(e.getMessage());
		}
		return response;
	}

	/**
	 * Saving a specific record by using the method save()
	 * 
	 * @param vendor
	 */
	public ResponseDto addVendor(Vendor vendor) {
		ResponseDto responseDto= new ResponseDto();

		Optional<UserEntity> userEntityData 	= userEntityService.getEntityType("VENDOR");
		String 				 prefix				= "";
		try {
			if(userEntityData.isPresent()) {
				prefix=userEntityData.get().getPrefix();
				log.info("add Vendor started ");
				Long maxId=vendorRepository.getTopId();
				if(maxId==null){
					Long stratValue=userEntityData.get().getStratValue();
					vendor.setVendorSeqCode(stratValue);
					vendor.setVendorCode(prefix+stratValue);
				}else {
					vendor.setVendorSeqCode(maxId+1);
					vendor.setVendorCode(prefix+(maxId+1));
				}
				vendorRepository.save(vendor);
				responseDto.setStatus(StatusConstant.STATUS_SUCCESS);
				responseDto.setStatusCode(StatusConstant.STATUS_SUCCESS_CODE);
				responseDto.setMsg(StatusConstant.STATUS_DESCRIPTION_VENDOR_ADDED_SUCESSFULLY);
			}else {
				responseDto.setStatus(StatusConstant.STATUS_FAILURE);
				responseDto.setStatusCode(StatusConstant.STATUS_FAILURE_CODE);
				responseDto.setMsg(StatusConstant.STATUS_PREFIX_NOT_AVAILAIBLE);
			}
		}
		catch(Exception e) {
			log.error(StatusConstant.EXCEPTION_OCCURRED +e.getMessage() );
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
		ResponseDto responseDto= new ResponseDto();
		try {
			log.info("delete Vendor started for given VendorCode "+vendorId);
			vendorRepository.deleteIMById(vendorId);
			responseDto.setStatus(StatusConstant.STATUS_SUCCESS);
			responseDto.setStatusCode(StatusConstant.STATUS_SUCCESS_CODE);
			responseDto.setMsg(StatusConstant.STATUS_DESCRIPTION_VENDOR_DELETED_SUCESSFULLY+vendorId);
		}
		catch(Exception e) {
			log.error(StatusConstant.EXCEPTION_OCCURRED +e.getMessage() );
			responseDto.setMsg(e.getMessage());	
		}
		return responseDto;
	}

	@Override
	public void update(Vendor vendor) {
		log.info("update vendor details  ");
		try {
			vendorRepository.save(vendor);
			log.info(StatusConstant.STATUS_DESCRIPTION_VENDOR_UPDATED_SUCESSFULLY);
		}
		catch(Exception e) {
			log.error(StatusConstant.EXCEPTION_OCCURRED +e.getMessage() );
		}
	}

	@Override
	public ResponseDto deActivate(VendorDeactivateRequest request) {
		ResponseDto responseDto = new ResponseDto();
		String vendorCode       = request.getVendorCode();
		boolean deactivateFlag  = request.getIsVendorDeativate();
		try{
			log.info("Start Vendor deactivat for "+vendorCode);
			vendorRepository.deactvateVendor(vendorCode,deactivateFlag);
			log.info(StatusConstant.STATUS_DESCRIPTION_VENDOR_DEACTIVATED_SUCESSFULLY+vendorCode);
			responseDto.setStatus(StatusConstant.STATUS_SUCCESS);
			responseDto.setStatusCode(StatusConstant.STATUS_SUCCESS_CODE);
			responseDto.setMsg(StatusConstant.STATUS_DESCRIPTION_VENDOR_DEACTIVATED_SUCESSFULLY+vendorCode);
		}
		catch(Exception e) {
			log.error(StatusConstant.EXCEPTION_OCCURRED +e.getMessage() );
			responseDto.setMsg(e.getMessage());
		}
		return responseDto;
	}
	
	
	public VendorDetailsResponseDto getAllVendorByImCode(String imCode) {
		VendorDetailsResponseDto response = new VendorDetailsResponseDto();
		List<Vendor> vendor 			  = new ArrayList<>();
		try {
			vendorRepository.findByImCode(imCode).forEach(vendor::add);
			if(!vendor.isEmpty()) {
				response.setStatusCode(StatusConstant.STATUS_SUCCESS_CODE);
				response.setStatus(StatusConstant.STATUS_SUCCESS);
				response.setMsg(StatusConstant.STATUS_DESCRIPTION_VENDOR_LIST_RETRIVED_SUCESSFULLY);
				response.setListOfVendor(vendor);
			}
			else {
				response.setStatusCode(StatusConstant.STATUS_FAILURE_CODE);
				response.setStatus(StatusConstant.STATUS_FAILURE);
				response.setMsg(StatusConstant.STATUS_DATA_NOT_AVAILAIBLE);
			}
		}
		catch(Exception e) {
			log.error(StatusConstant.EXCEPTION_OCCURRED +e.getMessage() );
			response.setMsg(e.getMessage());
		}
		return response;
	}
}