package onb.com.scf.repository;

import java.sql.Timestamp;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import onb.com.scf.entity.VendorPreAuthEntity;
@Repository
public interface VendorPreAuthRepository extends JpaRepository<VendorPreAuthEntity, Long> {

	@Query(nativeQuery = true, value = "select *  from ONB_PREAUTH_VENDOR_MASTER where status = 'Pending' and authorized_by is null and authorized_date is null order by last_modification_date_time desc")
	public List<VendorPreAuthEntity> getAllUnAuthorisedVednor();
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "delete from ONB_PREAUTH_VENDOR_MASTER where vendor_code=:vendorCode")
	public void deletePreAuthVendor(String vendorCode);
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true,value ="Update ONB_PREAUTH_VENDOR_MASTER SET is_vendor_inactive=:deactivateFlag,last_modification_date_time=:lastModificationDateTime WHERE vendor_code=:vendorCode")
	public void deactvateVendor(String vendorCode, boolean deactivateFlag, Timestamp lastModificationDateTime);
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true,value ="Update ONB_PREAUTH_VENDOR_MASTER SET is_vendor_inactive=:activateFlag,last_modification_date_time=:lastModificationDateTime WHERE vendor_code=:vendorCode")
	public void actvateVendor(String vendorCode, boolean activateFlag, Timestamp lastModificationDateTime);
	    
	@Query(nativeQuery = true, value = "select pre_authvendor_id from ONB_PREAUTH_VENDOR_MASTER where vendor_code=:vendorCode")
	public Long getVendorPreauthId(String vendorCode);
	
	@Query(nativeQuery = true, value = "select * from ONB_PREAUTH_VENDOR_MASTER where vendor_code=:vendorCode")
	public VendorPreAuthEntity getVendorPreauthByVendorCode(String vendorCode);
	
	@Query(nativeQuery = true, value = "select  pre_authvendor_id from ONB_PREAUTH_VENDOR_MASTER where vendor_pan=:panCardNumber")
	public Long getVendorIDByPancard(String panCardNumber);
	
	@Query(nativeQuery = true, value = "SELECT vendor_code from ONB_PREAUTH_VENDOR_MASTER order by pre_authvendor_id desc limit 1")
	public String getTopVendorCode();
}
