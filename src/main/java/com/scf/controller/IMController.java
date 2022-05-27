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

import com.scf.model.IM;
import com.scf.model.UserEntity;
import com.scf.serviceImpl.IMService;
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
@GetMapping("/im")
private ResponseEntity<List<IM>> getAllIM() 
{
	try {
	List<IM> im = imService.getAllIM();

	if (im.isEmpty()) {
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	return new ResponseEntity<>(imService.getAllIM(), HttpStatus.OK);
   } catch (Exception e) {
	return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
}
}


/**
 * This api retrieves the detail of a specific im based on 
 * @param imId
 * @return success or failure response
 */
@GetMapping("/im/{imid}")
private ResponseEntity<IM> getIM(@PathVariable("imid") int imId) 
{
	Optional<IM> imData = imService.getIMByCode(imId);
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
private ResponseEntity<HttpStatus> deleteIM(@PathVariable("imid") int imId) 
{
	try {
		imService.delete(imId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	} catch (Exception e) {
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}


/**
 * This api creates all the im detail in the database
 * @return imCode
 */
@PostMapping("/im")
private String saveIM(@RequestBody IM im) 
{
	imService.saveOrUpdate(im);
return im.getImCode();
}

/**
 * This api updates the im detail in the database
 * @param im
 * @return im
 */
@PutMapping("/im")
private IM update(@RequestBody IM im) 
{
	imService.saveOrUpdate(im);
return im;
}


/**
 * This api deactivates the im detail in the database
 * @param im
 * @return im
 */
@PutMapping("/im/deactivate")
private IM deActivate(@RequestBody IM im) 
{
	im.setIsImInactive(false);
	imService.saveOrUpdate(im);
return im;
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
	imService.saveOrUpdate(im);
return im;
}
}

