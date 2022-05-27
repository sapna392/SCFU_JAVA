package com.scf.serviceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scf.model.Vendor;
import com.scf.repository.VendorRepository;


/**
 * @author Naseem
 *
 */
@Service
public class VendorService 
{
@Autowired
VendorRepository vendorRepository;


/**
 * Getting all vendor record by using the method findaAll()
 * @return vendor
 */
public List<Vendor> getAllVendor() 
{
List<Vendor> vendor = new ArrayList<Vendor>();
vendorRepository.findAll().forEach(vendors -> vendor.add(vendors));
return vendor;
}


/**
 * Getting a specific record by using the method findById()
 * @param id
 * @return vendor
 */
public Optional<Vendor> getVendorByCode(int id) 
{
return vendorRepository.findById(id);
}


/**
 * Saving a specific record by using the method save()
 * @param vendor
 */
public void saveOrUpdate(Vendor vendor) 
{
	vendorRepository.save(vendor);
}


/**
 * Deleting a specific record by using the method deleteById()
 * @param id
 */
public void delete(int id) 
{
	vendorRepository.deleteById(id);
}


}