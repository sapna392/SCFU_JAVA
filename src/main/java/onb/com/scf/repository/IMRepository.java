package onb.com.scf.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import onb.com.scf.entity.IMEntity;

/**
 * @author Naseem
 *
 */
public interface IMRepository extends JpaRepository<IMEntity, String> {

	@Query(nativeQuery = true, value = "SELECT im_seq_id from onb_im_master order by im_seq_id desc limit 1")
	public Long getTopId();

	@Transactional
	@Modifying
	@Query(nativeQuery = true, value ="update onb_im_master SET is_im_inactive=:isImInactive,last_modification_date_time=:lastModificationDateTime,status=:status,action=:action WHERE im_code=:imCode")
	public void isImInactive(@Param("imCode") String imCode, @Param("isImInactive") Boolean isImInactive,
			Timestamp lastModificationDateTime,String status,String action);

	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "Delete from onb_im_master where im_code=:imCode")
	public void deleteIMById(@Param("imCode") String imCode);

	public List<IMEntity> findByImCode(String imCode);

	@Transactional
	@Modifying
	@Query(nativeQuery = true,value ="Update onb_im_master SET is_im_inactive=:isImInactive,last_modification_date_time=:lastModificationDateTime WHERE im_code=:imCode")
	public void activateIm(@Param("imCode") String imCode, @Param("isImInactive") Boolean isImInactive,
			Timestamp lastModificationDateTime);
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "update onb_im_master set status=:status,remark=:remark,authorized_by =:authorisedBy ,authorized_date=:authorisedDate where im_code=:imCode")
	public void authoriseIM(String imCode,String status,String remark,String authorisedBy,Timestamp authorisedDate);
	
	@Query(nativeQuery = true, value = "select  im_seq_id from onb_im_master where im_code=:imCode")
	public Long getIMIDByImCode(String imCode);
	
	@Query(nativeQuery = true, value = "select  im_seq_id from onb_im_master where im_pan=:panCardNumber")
	public Long getIMIDByPancard(String panCardNumber);
	
	@Query(nativeQuery = true, value = "select  im_pan from onb_im_master where im_code=:imCode")
	public String getPanNumberByIMCode(String imCode);
		
}
