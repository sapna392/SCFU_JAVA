package onb.com.scf.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import onb.com.scf.entity.IMPreauthEntity;
@Repository
public interface IMPreauthRepository extends CrudRepository<IMPreauthEntity, Long> {

	@Query(nativeQuery = true, value = "select *  from onb_im_pre_auth_master where status = 'Pending' and authorized_by is null and authorized_date is null")
	public List<IMPreauthEntity> getAllUnAuthorisedIM();
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "delete from onb_im_pre_auth_master where im_code=:imCode")
	public void deletePreAuthIM(String imCode);
}
