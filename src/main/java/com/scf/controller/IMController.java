package com.scf.controller;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scf.dto.IMDeactivateReq;
import com.scf.dto.IMDetailsResponseDto;
import com.scf.dto.ResponseDto;
import com.scf.model.IM;
import com.scf.service.IMService;
import com.scf.serviceImpl.IMServiceImpl;
import com.scf.serviceImpl.UserEntityService;

/**
 * @author Naseem
 *
 */
@RestController
@RequestMapping("scfu/api")

public class IMController 
{

@Autowired
IMService imService;

@Autowired
UserEntityService userEntityService;

/**
 * This api retrieves all the im detail from the database 
 * @return success or failure response
 */
@GetMapping("/im/getImDetails")
private ResponseEntity<IMDetailsResponseDto > getAllIM() 
{
	
	
		IMDetailsResponseDto response = imService.getAllIM();
        return new ResponseEntity<>(response,HttpStatus.OK);
	

	
}


/**
 * This api retrieves the detail of a specific im based on 
 * @param imId
 * @return success or failure response
 */
@GetMapping("/im/{imid}")
private ResponseEntity<IM> getIM(@PathVariable("imid") String imCode) 
{
	Optional<IM> imData = imService.getIMByCode(imCode);
	if (imData.isPresent()) {
		return new ResponseEntity<>(imData.get(), HttpStatus.OK);
	} else {
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}


/**
 * This api deletes the detail of a specific im based on 
 * @param imId
 * @return success or failure response
 */
@DeleteMapping("/im/{imid}")
private ResponseEntity<ResponseDto> deleteIM(@PathVariable("imid") String imCode) 
{
	
		ResponseDto response =imService.delete(imCode);
		return new ResponseEntity<>(response,HttpStatus.OK);
	
}


/**
 * This api creates all the im detail in the database
 * @return imCode
 */
@PostMapping("/im/addIm")
private String saveIM(@RequestBody IM im) 
{
	imService.addIm(im);
return im.getImCode();
}

/**
 * This api updates the im detail in the database
 * @param im
 * @return im
 */
@PutMapping("/im/updateIm")
private ResponseEntity<ResponseDto> update(@RequestBody IM im) 
{
	
	ResponseDto responseDto=imService.updateIm(im);
	return new ResponseEntity<>(responseDto,HttpStatus.OK);
}


/**
 * This api deactivates the im detail in the database
 * @param im
 * @return im
 */
@PutMapping("/im/deactivate")
private ResponseEntity<ResponseDto> deActivate(@RequestBody IMDeactivateReq request) 
{
	
		ResponseDto responseDto=imService.isImInactive(request);
		return new ResponseEntity<>(responseDto,HttpStatus.OK);
	
}


/**
 * This api activates the im detail in the database
 * @param im
 * @return im
 */
@PutMapping("/im/activate")
private IM activate(@RequestBody IM im) 
{
	im.setIsImInactive(true);
//	imService.saveOrUpdate(im);
return im;
}
}

