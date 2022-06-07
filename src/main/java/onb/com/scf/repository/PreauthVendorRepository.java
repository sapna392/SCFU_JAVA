package onb.com.scf.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import onb.com.scf.entity.PreauthVendorEntity;
import onb.com.scf.entity.VendorPreAuthEntity;
@Repository
public interface PreauthVendorRepository extends CrudRepository<PreauthVendorEntity, Long> {
	@Query(nativeQuery = true, value = "select *  from onb_preauth_vendor where status = 'Pending' and authorized_by is null and authorized_date is null")
	public List<VendorPreAuthEntity> getAllUnAuthorisedVendor();
}
