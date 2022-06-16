package onb.com.scf.repository;

import java.sql.Timestamp;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import onb.com.scf.entity.IMHistoryEntity;

/**
 * @author Naseem
 *
 */
@Repository
public interface IMHistoryRepository extends JpaRepository<IMHistoryEntity, String> {
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "update onb_im_master_history set status=:status,remark=:remark,authorized_by =:authorisedBy ,authorized_date=:authorisedDate where im_code=:imCode")
	public void authoriseIM(String imCode,String status,String remark,String authorisedBy,Date authorisedDate);
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true,value ="Update onb_im_master_history SET is_im_inactive=:isImInactive,last_modification_date_time=:lastModificationDateTime WHERE im_code=:imCode")
	public void activateIm(@Param("imCode") String imCode, @Param("isImInactive") Boolean isImInactive,
			Timestamp lastModificationDateTime);
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value ="update onb_im_master_history SET is_im_inactive=:isImInactive,last_modification_date_time=:lastModificationDateTime WHERE im_code=:imCode")
	public void isImInactive(@Param("imCode") String imCode, @Param("isImInactive") Boolean isImInactive,
			Timestamp lastModificationDateTime);
}
