package onb.com.scf.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import onb.com.scf.entity.VendorPreAuthEntity;
@Repository
public interface VendorPreAuthRepository extends JpaRepository<VendorPreAuthEntity, Long> {

	@Query(nativeQuery = true, value = "select *  from ONB_PREAUTH_VENDOR_MASTER where status = 'Pending' and authorized_by is null and authorized_date is null")
	public List<VendorPreAuthEntity> getAllUnAuthorisedVednor();
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "delete from ONB_PREAUTH_VENDOR_MASTER where vendor_code=:vendorCode")
	public void deletePreAuthVendor(String vendorCode);
}
