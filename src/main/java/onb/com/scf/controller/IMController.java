package onb.com.scf.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import onb.com.scf.dto.IMActivateRequest;
import onb.com.scf.dto.IMAndVendorResponse;
import onb.com.scf.dto.IMDeactivateReq;
import onb.com.scf.dto.IMDetailsResponseDto;
import onb.com.scf.dto.IMForApproveResponse;
import onb.com.scf.dto.ResponseDto;
import onb.com.scf.entity.IMEntity;
import onb.com.scf.repository.IMRepository;
import onb.com.scf.service.IMService;
import onb.com.scf.service.impl.UserEntityService;

/**
 * @author Naseem
 *
 */
@RestController
@RequestMapping("scfu/api")
@CrossOrigin
public class IMController {

	@Autowired
	IMService imService;

	@Autowired
	UserEntityService userEntityService;

	@Autowired
	IMRepository repo;

	/**
	 * This api retrieves all the im detail from the database
	 * 
	 * @return success or failure response
	 */
	@GetMapping("/im/getImDetails")
	public ResponseEntity<IMDetailsResponseDto> getAllIM() {
		IMDetailsResponseDto response = imService.getAllIM();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * This api retrieves the detail of a specific im based on
	 * 
	 * @param imId
	 * @return success or failure response
	 */
	@GetMapping("/im/getImByCode/{imid}")
	public ResponseEntity<IMDetailsResponseDto> getIM(@PathVariable("imid") String imCode) {
		IMDetailsResponseDto response = imService.getIMByCode(imCode);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	/**
	 * This api creates all the im detail in the database
	 * 
	 * @return imCode
	 */
	@PostMapping("/im/addImByMaker")
	public ResponseEntity<ResponseDto> addImByMaker(@RequestBody IMEntity im) {
		ResponseDto response = imService.addImByMaker(im);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * This api activates the im detail in the database
	 * 
	 * @param im
	 * @return im
	 */
	@PutMapping("/im/activate")
	public ResponseEntity<ResponseDto> activateIM(@RequestBody IMActivateRequest request) {
		ResponseDto responseDto = imService.activeIM(request);
		return new ResponseEntity<>(responseDto, HttpStatus.OK);
	}

	//makers method
	@PutMapping("/im/updateIMByMaker")
	public ResponseEntity<ResponseDto> updateIMByMaker(@RequestBody IMEntity im) {
		ResponseDto response = imService.updateImByMaker(im);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("/im/deleteIMByMaker/{imid}")
	public ResponseEntity<ResponseDto> deleteIMByMaker(@PathVariable("imid") String imCode) {
		ResponseDto response = imService.deleteIMByMaker(imCode);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PutMapping("/im/deactivateIMByMaker")
	public ResponseEntity<ResponseDto> deactivateIMByMaker(@RequestBody IMDeactivateReq request) {
		ResponseDto responseDto = imService.deactivateIMByMaker(request);
		return new ResponseEntity<>(responseDto, HttpStatus.OK);
	}
	//here we will get all unauthorised request for checker
	@GetMapping("/im/getAllUnAuthorisedIMRequests")
	public ResponseEntity<IMForApproveResponse> getAllUnAuthorisedIM() {
		IMForApproveResponse response = imService.getAllUnAuthorisedTxnOfIM();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	//checker methods
	@PostMapping("/im/authoriseAllIMRequests")
	public ResponseEntity<ResponseDto> authoriseIM(@RequestBody List<IMEntity> approvedIMList) {
		ResponseDto response = imService.authoriseIM(approvedIMList);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	@GetMapping("/im/getAllIMCodeAndVendorCode/{dataIdentifier}")
	public ResponseEntity<IMAndVendorResponse> getAllIMCodeAndVendorCode(@PathVariable("dataIdentifier") short dataIdentifier) {
		IMAndVendorResponse response = imService.getAllIMCodeAndVendorCode(dataIdentifier);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}