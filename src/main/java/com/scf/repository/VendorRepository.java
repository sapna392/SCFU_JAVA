package com.scf.repository;
import java.util.Date;
import java.util.List;

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
	@Query("Update Vendor SET isVendorInactive=:deactivateFlag,last_mod_time=:lastModTime WHERE vendorCode=:vendorCode")
	public void deactvateVendor(String vendorCode, boolean deactivateFlag,Date lastModTime);

	public List<Vendor> findByVendorCode(String id);

	@Transactional
	@Modifying
	@Query(nativeQuery = true,value="Delete from ONB_VENDOR_MASTER where VENDOR_CODE=:vendorId")
	public void deleteIMById(String vendorId);
	
	@Query(nativeQuery = true,value="SELECT vendor_seq_code from ONB_VENDOR_MASTER order by vendor_seq_code desc limit 1")
	public Long getTopId();
	
	public List<Vendor> findByImCode(String imCode);
	
	@Transactional
	@Modifying
	@Query("Update Vendor SET isVendorInactive=:activateFlag,last_mod_time=:lastModTime WHERE vendorCode=:vendorCode")
	public void actvateVendor(String vendorCode, boolean activateFlag,Date lastModTime);
}
