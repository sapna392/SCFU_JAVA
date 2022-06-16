package onb.com.scf.repository;

import java.sql.Timestamp;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import onb.com.scf.entity.VendorHistoryEntity;
@Repository
public interface VendorHistoryRepository extends CrudRepository<VendorHistoryEntity, Long> {
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "update onb_vendor_history set status=:status,remark=:remark,authorized_by =:authorisedBy ,authorized_date=:authorisedDate where vendor_code=:vendorCode")
	public void authoriseVendor(String vendorCode,String status,String remark,String authorisedBy,Date authorisedDate);
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true,value ="Update onb_vendor_history SET is_vendor_inactive=:deactivateFlag,last_modification_date_time=:lastModificationDateTime WHERE vendor_code=:vendorCode")
	public void deactvateVendor(String vendorCode, boolean deactivateFlag, Timestamp lastModificationDateTime);
}
