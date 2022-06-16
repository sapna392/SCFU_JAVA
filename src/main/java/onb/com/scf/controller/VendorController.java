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

import onb.com.scf.dto.ResponseDto;
import onb.com.scf.dto.VendoActivateRequest;
import onb.com.scf.dto.VendorDeactivateRequest;
import onb.com.scf.dto.VendorDetailsResponseDto;
import onb.com.scf.dto.VendorPreAuthResponse;
import onb.com.scf.entity.VendorEntity;
import onb.com.scf.service.VendorService;

/**
 * @author Sapna Singh
 *
 */
@RestController
@RequestMapping("scfu/api")
@CrossOrigin
public class VendorController {

	@Autowired
	VendorService vendorService;

	/**
	 * This api retrieves all the vendor detail from the database
	 * 
	 * @return success or failure response
	 */
	@GetMapping("/getVendorDetails")
	public ResponseEntity<VendorDetailsResponseDto> getAllVendor() {
		VendorDetailsResponseDto response = vendorService.getAllVendor();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * This api retrieves the detail of a specific vendor based on
	 * 
	 * @param vendorId
	 * @return success or failure response
	 */
	@GetMapping("/getVendorByVendorCode/{vendorid}")
	public ResponseEntity<VendorDetailsResponseDto> getVendor(@PathVariable("vendorid") String vendorCode) {
		VendorDetailsResponseDto vendorData = vendorService.getVendorByCode(vendorCode);
		return new ResponseEntity<>(vendorData, HttpStatus.OK);
	}

	/**
	 * This api deletes the detail of a specific vendor based on
	 * 
	 * @param vendorId
	 * @return success or failure response
	 */
	@DeleteMapping("/deleteVendorByIdByMaker/{vendorid}")
	public ResponseEntity<ResponseDto> deleteVendor(@PathVariable("vendorid") String vendorId) {
		ResponseDto response = vendorService.deleteVendorByIdByMaker(vendorId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * This api Add all the vendor detail in the database
	 * 
	 * @return vendorCode
	 */
	@PostMapping("/addVendorByMaker")
	public ResponseEntity<ResponseDto> saveVendor(@RequestBody VendorEntity vendor) {
		ResponseDto response = vendorService.addVendorByMaker(vendor);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * This api updates the vendor detail in the database
	 * 
	 * @param vendor
	 * @return vendor
	 */
	@PutMapping("/updateVendorByMaker")
	public ResponseEntity<ResponseDto> update(@RequestBody VendorEntity vendor) {
		ResponseDto response =vendorService.updateVendorByMaker(vendor);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * This api deactivates the vendor detail in the database
	 * 
	 * @param vendor
	 * @return vendor
	 */
	@PutMapping("/deActivateVendorByMaker")
	public ResponseEntity<ResponseDto> deActivate(@RequestBody VendorDeactivateRequest requets) {
		ResponseDto responseDto = vendorService.deActivateVendorByMaker(requets);
		return new ResponseEntity<>(responseDto, HttpStatus.OK);
	}

	@PutMapping("/activateVendor")
	public ResponseEntity<ResponseDto> deActivate(@RequestBody VendoActivateRequest requets) {
		ResponseDto responseDto = vendorService.activateVendor(requets);
		return new ResponseEntity<>(responseDto, HttpStatus.OK);
	}

	/*
	 * @GetMapping("/getAllVendorByIMCode/{imCode}") public
	 * ResponseEntity<VendorDetailsResponseDto>
	 * getAllVendorByImCode(@PathVariable("imCode") String imCode) {
	 * VendorDetailsResponseDto vendorData =
	 * vendorService.getAllVendorByImCode(imCode); return new
	 * ResponseEntity<>(vendorData,HttpStatus.OK); } /** This api activates the
	 * vendor detail in the database
	 * 
	 * @param vendor
	 * 
	 * @return vendor
	 */
	/*
	 * @PutMapping("/vendor/activate") private Vendor activate(@RequestBody Vendor
	 * vendor) { vendor.setIsVendorInactive(true);
	 * vendorService.saveOrUpdate(vendor); return vendor; }
	 */
	
	@GetMapping("/vendor/getAllUnAuthorisedVendor")
	public ResponseEntity<VendorPreAuthResponse> getAllUnAuthorisedVendor() {
		VendorPreAuthResponse response = vendorService.getAllUnAuthorisedVendor();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/vendor/authoriseVendor")
	public ResponseEntity<ResponseDto> authoriseIM(@RequestBody List<VendorEntity> approvedVendorList) {
		ResponseDto response = vendorService.authoriseVendorByCheker(approvedVendorList);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
