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

import com.scf.model.Vendor;
import com.scf.serviceImpl.VendorService;


/**
 * @author Naseem
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
@GetMapping("/vendor")
private ResponseEntity<List<Vendor>> getAllVendor() 
{
	try {
		List<Vendor> vendor = vendorService.getAllVendor();

		if (vendor.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(vendorService.getAllVendor(), HttpStatus.OK);
	} catch (Exception e) {
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}


/**
 * This api retrieves the detail of a specific vendor based on 
 * @param vendorId
 * @return success or failure response
 */
@GetMapping("/vendor/{vendorid}")
private ResponseEntity<Vendor> getVendor(@PathVariable("vendorid") int vendorId) 
{
	Optional<Vendor> vendorData = vendorService.getVendorByCode(vendorId);

	if (vendorData.isPresent()) {
		return new ResponseEntity<>(vendorData.get(), HttpStatus.OK);
	} else {
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}


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
 * This api creates all the vendor detail in the database
 * @return vendorCode
 */
@PostMapping("/vendor")
private int saveVendor(@RequestBody Vendor vendor) 
{
	vendorService.saveOrUpdate(vendor);
return vendor.getVendorCode();
}


/**
 * This api updates the vendor detail in the database
 * @param vendor
 * @return vendor
 */
@PutMapping("/vendor")
private Vendor update(@RequestBody Vendor vendor) 
{
	vendorService.saveOrUpdate(vendor);
return vendor;
}
 

/**
 * This api deactivates the vendor detail in the database
 * @param vendor
 * @return vendor
 */
@PutMapping("/vendor/deactivate")
private Vendor deActivate(@RequestBody Vendor vendor) 
{
	vendor.setIsVendorInactive(false);
	vendorService.saveOrUpdate(vendor);
return vendor;
}


/**
 * This api activates the vendor detail in the database
 * @param vendor
 * @return vendor
 */
@PutMapping("/vendor/activate")
private Vendor activate(@RequestBody Vendor vendor) 
{
	vendor.setIsVendorInactive(true);
	vendorService.saveOrUpdate(vendor);
return vendor;
}
}
