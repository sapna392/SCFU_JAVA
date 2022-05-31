package com.scf.serviceImpl;

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
	//@Autowired
	//VendorHistoryRepository vendorHistoryRepository;

	/**
	 * Getting all vendor record by using the method findaAll()
	 * 
	 * @return vendor
	 */
	public VendorDetailsResponseDto getAllVendor() {
		VendorDetailsResponseDto response = new VendorDetailsResponseDto();
		List<Vendor> vendor = new ArrayList<Vendor>();
		
		try {
			vendorRepository.findAll().forEach(vendors -> vendor.add(vendors));
			if(!vendor.isEmpty()) {
				response.setStatusCode("200");
				response.setStatus("Success");
				response.setMsg("List of Vendors retreived successfully !");
				response.setListOfVendor(vendor);
			}
			else {
				response.setStatusCode("404");
				response.setStatus("Failure");
				response.setMsg("Data not available !");
			}
			}
			catch(Exception e) {
	         log.error("Exception Occurred" +e.getMessage() );
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
	public Optional<Vendor> getVendorByCode(int id) {
		return vendorRepository.findById(id);
	}

	/**
	 * Saving a specific record by using the method save()
	 * 
	 * @param vendor
	 */
	public ResponseDto addVendor(Vendor vendor) {
		ResponseDto responseDto= new ResponseDto();
		
		Optional<UserEntity> userEntityData =userEntityService.getEntityType("VENDOR");
		String prefix=userEntityData.get().getPrefix();
		try {
		log.info("add Vendor started ");
		Long maxId=imRepository.getTopId();
		System.out.println("maxId:" +maxId);
		if(maxId==null){
		Long stratValue=userEntityData.get().getStratValue();
		vendor.setVendorSeqCode(stratValue);
		vendor.setVendorCode(prefix+stratValue);
		}else {
			vendor.setVendorSeqCode(maxId+1);
			vendor.setVendorCode(prefix+(maxId+1));
		}
		vendorRepository.save(vendor);
		// Vendor details added to onb_im_master_history  
		// VendorHistory vendorHistory = new VendorHistory(vendor);
		//vendorHistoryRepository.save(vendorHistory);
		 
		responseDto.setStatus("Success");
		responseDto.setStatusCode("200");
		responseDto.setMsg("Vendor Added successfully");
		}
		catch(Exception e) {
            log.error("Exception Occurred" +e.getMessage() );
			  responseDto.setMsg(e.getMessage());	
		}
		return responseDto;
	}

	/**
	 * Deleting a specific record by using the method deleteById()
	 * 
	 * @param id
	 */
	public void delete(int id) {
		vendorRepository.deleteById(id);
	}

	@Override
	public void update(Vendor vendor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ResponseDto deActivate(VendorDeactivateRequest request) {
		ResponseDto responseDto= new ResponseDto();
		String vendorCode =request.getVendorCode();
		boolean deactivateFlag = request.getIsVendorDeativate();
		
		try{
			log.info("Start Vendor deactivat for "+vendorCode);
			vendorRepository.deactvateVendor(vendorCode,deactivateFlag);
			log.info("Vendor deactivated successfully for "+vendorCode);
			responseDto.setStatus("Success");
			responseDto.setStatusCode("200");
			responseDto.setMsg("Vendor deactivated successfully for "+vendorCode);
			
		}
		catch(Exception e) {
			log.error("Exception Occurred" +e.getMessage() );
			
			responseDto.setMsg(e.getMessage());
			}
		return responseDto;
	}

}