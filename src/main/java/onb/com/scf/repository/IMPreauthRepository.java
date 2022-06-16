package onb.com.scf.repository;

import java.sql.Timestamp;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import onb.com.scf.entity.IMPreauthEntity;
@Repository
public interface IMPreauthRepository extends CrudRepository<IMPreauthEntity, Long> {

	@Query(nativeQuery = true, value = "select *  from onb_im_pre_auth_master where status = 'Pending' and authorized_by is null and authorized_date is null order by last_modification_date_time desc")
	public List<IMPreauthEntity> getAllUnAuthorisedIM();
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "delete from onb_im_pre_auth_master where im_code=:imCode")
	public void deletePreAuthIM(String imCode);
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true,value ="Update onb_im_pre_auth_master SET is_im_inactive=:isImInactive,last_modification_date_time=:lastModificationDateTime WHERE im_code=:imCode")
	public void activateIm(@Param("imCode") String imCode, @Param("isImInactive") Boolean isImInactive,
			Timestamp lastModificationDateTime);
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value ="update onb_im_pre_auth_master SET is_im_inactive=:isImInactive,last_modification_date_time=:lastModificationDateTime,status=:status,action=:action WHERE im_code=:imCode")
	public void isImInactive(@Param("imCode") String imCode, @Param("isImInactive") Boolean isImInactive,
			Timestamp lastModificationDateTime,String status,String action);
	
	@Query(nativeQuery = true, value = "select im_preauth_id from onb_im_pre_auth_master where im_code=:imCode")
	public Long getIMPreauthId(String imCode);
	
	@Query(nativeQuery = true, value = "select * from onb_im_pre_auth_master where im_code=:imCode")
	public IMPreauthEntity getIMPreauthByImCode(String imCode);
	
	@Query(nativeQuery = true, value = "select  im_preauth_id from onb_im_pre_auth_master where im_pan=:panCardNumber")
	public Long getIMIDByPancard(String panCardNumber);
	
	@Query(nativeQuery = true, value = "SELECT im_code from onb_im_pre_auth_master order by im_preauth_id desc limit 1")
	public String getTopIMCode();
	
}
