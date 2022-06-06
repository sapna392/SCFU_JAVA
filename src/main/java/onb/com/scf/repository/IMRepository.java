package onb.com.scf.repository;

import java.util.Date;
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

	@Query(nativeQuery = true, value = "SELECT im_id from onb_im_master order by IM_ID desc limit 1")
	public Long getTopId();

	@Transactional
	@Modifying
	@Query(nativeQuery = true, value ="update onb_im_master SET is_im_inactive=:isImInactive,last_mod_time=:lastModificationTime WHERE im_code=:imCode")
	public void isImInactive(@Param("imCode") String imCode, @Param("isImInactive") Boolean isImInactive,
			Date lastModificationTime);

	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "Delete from onb_im_master where im_code=:imCode")
	public void deleteIMById(@Param("imCode") String imCode);

	public List<IMEntity> findByImCode(String imCode);

	@Transactional
	@Modifying
	@Query(nativeQuery = true,value ="Update onb_im_master SET is_im_inactive=:isImInactive,last_mod_time=:lastModificationTime WHERE im_code=:imCode")
	public void activateIm(@Param("imCode") String imCode, @Param("isImInactive") Boolean isImInactive,
			Date lastModificationTime);
}
