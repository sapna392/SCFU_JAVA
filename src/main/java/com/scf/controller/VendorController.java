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

import com.scf.dto.IMDetailsResponseDto;
import com.scf.dto.ResponseDto;
import com.scf.dto.VendorDeactivateRequest;
import com.scf.dto.VendorDetailsResponseDto;
import com.scf.model.Vendor;
import com.scf.service.VendorService;
import com.scf.serviceImpl.VendorServiceImpl;


/**
 * @author Sapna Singh
 *
 */
@RestController
@RequestMapping("scfu/api")
public class VendorController 
{

@Autowired
VendorService vendorService;


/**
 * This api retrieves all the vendor detail from the database 
 * @return success or failure response
 */
@GetMapping("/vendor/getVendorDetails")
private ResponseEntity<VendorDetailsResponseDto> getAllVendor() {
	
	VendorDetailsResponseDto response = vendorService.getAllVendor();
	return new ResponseEntity<>(response,HttpStatus.OK);
}


/**
 * This api retrieves the detail of a specific vendor based on 
 * @param vendorId
 * @return success or failure response
 */
/*@GetMapping("/vendor/{vendorid}")
private ResponseEntity<VendorDetailsResponseDto> getVendor(@PathVariable("vendorid") String vendorCode) 
{
	Optional<IMDetailsResponseDto> vendorData = vendorService.getVendorByCode(vendorCode);

	//return new ResponseEntity<>(vendorData,HttpStatus.OK);

}*/


/**
 * This api deletes the detail of a specific vendor based on 
 * @param vendorId
 * @return success or failure response
 */
@DeleteMapping("/vendor/{vendorid}")
private ResponseEntity<HttpStatus> deleteVendor(@PathVariable("vendorid") int vendorId) 
{
	try {
		vendorService.delete(vendorId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	} catch (Exception e) {
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}


/**
 * This api Add all the vendor detail in the database
 * @return vendorCode
 */
@PostMapping("/vendor/addVendor")
private ResponseEntity<ResponseDto> saveVendor(@RequestBody Vendor vendor) 
{
	ResponseDto response =  vendorService.addVendor(vendor);
	return new ResponseEntity<>(response,HttpStatus.OK);

}


/**
 * This api updates the vendor detail in the database
 * @param vendor
 * @return vendor
 */
@PutMapping("/vendor/updateVendor")
private Vendor update(@RequestBody Vendor vendor) 
{
	vendorService.update(vendor);
return vendor;
}
 

/**
 * This api deactivates the vendor detail in the database
 * @param vendor
 * @return vendor
 */
@PutMapping("/vendor/deactivate")
private ResponseEntity<ResponseDto> deActivate(@RequestBody VendorDeactivateRequest requets) 
{
	
	ResponseDto responseDto=vendorService.deActivate(requets);
	return new ResponseEntity<>(responseDto,HttpStatus.OK);
}


/**
 * This api activates the vendor detail in the database
 * @param vendor
 * @return vendor
 */
/*@PutMapping("/vendor/activate")
private Vendor activate(@RequestBody Vendor vendor) 
{
	vendor.setIsVendorInactive(true);
	vendorService.saveOrUpdate(vendor);
return vendor;
}*/
}
