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
import com.scf.model.IMHistory;
import com.scf.model.UserEntity;
import com.scf.repository.IMHistoryRepository;
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
	IMHistoryRepository imHistoryRepository;

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
	 * @param id
	 * @return im
	 */
	public IMDetailsResponseDto getIMByCode(String imCode) 
	{
		IMDetailsResponseDto response = new IMDetailsResponseDto();
		try {
			List<IM> im =imRepository.findByImCode(imCode);
		if(!im.isEmpty()) {
			response.setStatusCode("200");
			response.setStatus("Success");
			response.setMsg("IM retreived successfully !");
			response.setListOfIM(im);
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
	 * Saving a specific record by using the method save()
	 * @param im
	 */
	public ResponseDto addIm(IM im) {
        ResponseDto responseDto= new ResponseDto();
		Optional<UserEntity> userEntityData =userEntityService.getEntityType("IM");
		String prefix=userEntityData.get().getPrefix();
		try {
		log.info("add IM started ");
		Long maxId=imRepository.getTopId();
		System.out.println("maxId:" +maxId);
		if(maxId==null){
		Long stratValue=userEntityData.get().getStratValue();
		im.setImId(stratValue);
		im.setImCode(prefix+stratValue);	
		}else {
			im.setImId(maxId+1);
			im.setImCode(prefix+(maxId+1));
		}
		imRepository.save(im);
		// Im details added to onb_im_master_history  
		 IMHistory iHis = new IMHistory(im);
		 imHistoryRepository.save(iHis);
		 
		responseDto.setStatus("Success");
		responseDto.setStatusCode("200");
		responseDto.setMsg("IM Added successfully");
		}
		catch(Exception e) {
             log.error("Exception Occurred" +e.getMessage() );
			  responseDto.setMsg(e.getMessage());	
		}
		return responseDto;
	}


/**
	 * Deleting a specific record by using the method deleteById()
	 * @param id
	 */
	public ResponseDto delete(String imCode) {
		ResponseDto responseDto= new ResponseDto();
		try {
		log.info("delete IM started for given imcode "+imCode);
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
	public ResponseDto updateIm(IM im) {
		
		
		ResponseDto responseDto= new ResponseDto();
		
		try{
			List<IM> im2 = imRepository.findByImCode(im.getImCode());
			im.setImId(im2.get(0).getImId());
			imRepository.save(im);
			log.info("IM updated successfully");
			responseDto.setStatus("Success");
			responseDto.setStatusCode("200");
			responseDto.setMsg("IM updated successfully");
			
		}
		catch(Exception e) {
			log.error("Exception Occurred" +e.getMessage() );
			
			responseDto.setMsg(e.getMessage());
			}
		return responseDto;
		
	}


}