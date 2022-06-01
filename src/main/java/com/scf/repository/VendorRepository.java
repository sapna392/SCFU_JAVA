package com.scf.repository;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.scf.model.IM;
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

	//public List<IM> findByVendorCode(int id);
	@Query("select * from Vendor  WHERE vendorCode=:id")
	public List<Vendor> findByVendorCode(String id);
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true,value="Delete from ONB_VENDOR_MASTER where VENDOR_CODE=:vendorId")
	public void deleteIMById(String vendorId);
	
	
}
