package com.scf.repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.scf.model.Vendor;


/**
 * @author Naseem
 *
 */
public interface VendorRepository extends CrudRepository<Vendor, Long>
{
	@Transactional
	@Modifying
	@Query("Update Vendor SET isVendorInactive=:deactivateFlag WHERE vendorCode=:vendorCode")
	public void deactvateVendor(String vendorCode, boolean deactivateFlag);
	
	
}
