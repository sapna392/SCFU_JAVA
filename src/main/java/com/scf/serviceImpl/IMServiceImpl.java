package com.scf.serviceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scf.dto.IMDeactivateReq;
import com.scf.dto.IMDetailsResponseDto;
import com.scf.dto.ResponseDto;
import com.scf.model.IM;
import com.scf.model.UserEntity;
import com.scf.repository.IMRepository;
import com.scf.service.IMService;

import lombok.extern.slf4j.Slf4j;


/**
 * @author Naseem
 *
 */
@Service
@Slf4j
public class IMServiceImpl implements IMService
{
	@Autowired
	IMRepository imRepository;

	@Autowired
	UserEntityService userEntityService;


	/**
	 * Getting all im record by using the method findaAll()
	 * @return im
	 */
	public IMDetailsResponseDto getAllIM() {
		IMDetailsResponseDto response = new IMDetailsResponseDto();
		List<IM> im = new ArrayList<IM>();
		try {
		imRepository.findAll().forEach(ims -> im.add(ims));
		if(!im.isEmpty()) {
			response.setStatusCode("200");
			response.setStatus("Success");
			response.setMsg("List of IM retreived successfully !");
			response.setListOfIM(im);
		}
		else {
			response.setStatusCode("200");
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
	 * @param id
	 * @return im
	 */
	public Optional<IM> getIMByCode(String imCode) 
	{
		return imRepository.findById(imCode);
	}


	/**
	 * Saving a specific record by using the method save()
	 * @param im
	 */
	public void addIm(IM im) 
	{
		Optional<UserEntity> userEntityData =userEntityService.getEntityType("IM");
		String prefix=userEntityData.get().getPrefix();
		try {
		Long maxId=imRepository.getTopId();
		System.out.println("maxId:" +maxId);
		if(maxId==null){
		int stratValue=userEntityData.get().getStratValue();
		im.setImCode(prefix+stratValue);	
		}else {
			
			im.setImCode(prefix+(maxId+1));
		}
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
	    imRepository.save(im);
	}


/**
	 * Deleting a specific record by using the method deleteById()
	 * @param id
	 */
	public ResponseDto delete(String imCode) {
		ResponseDto responseDto= new ResponseDto();
		try {
		log.info("delet IM started for given imcode "+imCode);
		imRepository.deleteIMById(imCode);
		responseDto.setStatus("Success");
		responseDto.setStatusCode("200");
		responseDto.setMsg("IM deleted successfully for "+imCode);
		}
		catch(Exception e) {
             log.error("Exception Occurred" +e.getMessage() );
			  responseDto.setMsg(e.getMessage());	
		}
		return responseDto;
	}

	public Long getTopId() {
		return imRepository.getTopId();
	}

	public ResponseDto isImInactive(IMDeactivateReq request)
	{
		ResponseDto responseDto= new ResponseDto();
		String imCode =request.getImCode();
		
		try{
			imRepository.isImInactive(imCode,request.getIsImInactive());
			log.info("IM deactivated successfully"+imCode);
			responseDto.setStatus("Success");
			responseDto.setStatusCode("200");
			responseDto.setMsg("IM deactivated successfully for "+imCode);
			
		}
		catch(Exception e) {
			log.error("Exception Occurred" +e.getMessage() );
			
			responseDto.setMsg(e.getMessage());
			}
		return responseDto;
	}


	@Override
	public ResponseDto updateIm(IM imUpdateRequest) {
		ResponseDto responseDto= new ResponseDto();
		String imCode =imUpdateRequest.getImCode();
		
		return responseDto;
	}


}