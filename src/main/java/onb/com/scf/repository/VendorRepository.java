package onb.com.scf.repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import onb.com.scf.entity.VendorEntity;

/**
 * @author Naseem
 *
 */
public interface VendorRepository extends CrudRepository<VendorEntity, Long> {
	@Transactional
	@Modifying
	@Query(nativeQuery = true,value ="Update onb_vendor_master SET is_vendor_inactive=:deactivateFlag,last_modification_date_time=:lastModificationDateTime,status=:status,action=:action WHERE vendor_code=:vendorCode")
	public void deactvateVendor(String vendorCode, boolean deactivateFlag, Timestamp lastModificationDateTime,String status,String action);

	public List<VendorEntity> findByVendorCode(String id);

	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "Delete from onb_vendor_master where vendor_code=:vendorId")
	public void deleteIMById(String vendorId);

	@Query(nativeQuery = true, value = "SELECT vendor_seq_id from ONB_VENDOR_MASTER order by vendor_seq_id desc limit 1")
	public Long getTopId();

	public List<VendorEntity> findByImCode(String imCode);

	@Transactional
	@Modifying
	@Query(nativeQuery = true,value ="Update onb_vendor_master SET is_vendor_inactive=:activateFlag,last_mod_time=:lastModTime WHERE vendor_code=:vendorCode")
	public void actvateVendor(String vendorCode, boolean activateFlag, Date lastModTime);
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "update onb_vendor_master set status=:status,remark=:remark,authorized_by =:authorisedBy ,authorized_date=:authorisedDate where vendor_code=:vendorCode")
	public void authoriseVendor(String vendorCode,String status,String remark,String authorisedBy,Timestamp authorisedDate);
	
	@Query(nativeQuery = true, value = "select  vendor_seq_id from ONB_VENDOR_MASTER where vendor_code=:vendorCode")
	public Long getVendorIDByImCode(String vendorCode);
	
	@Query(nativeQuery = true, value = "select  vendor_seq_id from ONB_VENDOR_MASTER where vendor_pan=:panCardNumber")
	public Long getVendorIDByPancard(String panCardNumber);

	@Query(nativeQuery = true, value = "select  vendor_pan from ONB_VENDOR_MASTER where vendor_code=:vendorCode")
	public String getPanNumberByVendorCode(String vendorCode);
}
