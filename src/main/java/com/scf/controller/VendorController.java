package com.scf.controller;
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

import com.scf.dto.ResponseDto;
import com.scf.dto.VendorDeactivateRequest;
import com.scf.dto.VendorDetailsResponseDto;
import com.scf.model.Vendor;
import com.scf.service.VendorService;


/**
 * @author Sapna Singh
 *
 */
@RestController
@RequestMapping("scfu/api")
@CrossOrigin
public class VendorController 
{

	@Autowired
	VendorService vendorService;


	/**
	 * This api retrieves all the vendor detail from the database 
	 * @return success or failure response
	 */
	@GetMapping("/getVendorDetails")
	public ResponseEntity<VendorDetailsResponseDto> getAllVendor() {
		VendorDetailsResponseDto response = vendorService.getAllVendor();
		return new ResponseEntity<>(response,HttpStatus.OK);
	}


	/**
	 * This api retrieves the detail of a specific vendor based on 
	 * @param vendorId
	 * @return success or failure response
	 */
	@GetMapping("/getVendorByVendorCode/{vendorid}")
	public ResponseEntity<VendorDetailsResponseDto> getVendor(@PathVariable("vendorid") String vendorCode) 
	{
		VendorDetailsResponseDto vendorData = vendorService.getVendorByCode(vendorCode);
		return new ResponseEntity<>(vendorData,HttpStatus.OK);
	}


	/**
	 * This api deletes the detail of a specific vendor based on 
	 * @param vendorId
	 * @return success or failure response
	 */
	@DeleteMapping("/deleteVendor/{vendorid}")
	public ResponseEntity<ResponseDto>  deleteVendor(@PathVariable("vendorid") String vendorId) 
	{
		ResponseDto response =vendorService.deleteById(vendorId);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	/**
	 * This api Add all the vendor detail in the database
	 * @return vendorCode
	 */
	@PostMapping("/addVendor")
	public ResponseEntity<ResponseDto> saveVendor(@RequestBody Vendor vendor) 
	{
		ResponseDto response =  vendorService.addVendor(vendor);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	/**
	 * This api updates the vendor detail in the database
	 * @param vendor
	 * @return vendor
	 */
	@PutMapping("/updateVendor")
	public Vendor update(@RequestBody Vendor vendor) 
	{
		vendorService.update(vendor);
		return vendor;
	}
	/**
	 * This api deactivates the vendor detail in the database
	 * @param vendor
	 * @return vendor
	 */
	@PutMapping("/deactivateVendor")
	public ResponseEntity<ResponseDto> deActivate(@RequestBody VendorDeactivateRequest requets) 
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
