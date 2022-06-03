package com.scf.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.scf.model.IM;


/**
 * @author Naseem
 *
 */
public interface IMRepository extends JpaRepository<IM, String>
{

	public List<IM> findByImCode(String imCode);
	@Query(nativeQuery = true,value="SELECT im_id from ONB_IM_MASTER order by IM_ID desc limit 1")
	public Long getTopId();

	@Transactional
	@Modifying
	@Query("Update IM SET isImInactive=:isImInactive WHERE imCode=:imCode")
	public void isImInactive(@Param("imCode") String imCode,@Param("isImInactive") Boolean isImInactive);

	@Transactional
	@Modifying
	@Query(nativeQuery = true,value="Delete from ONB_IM_MASTER where IM_CODE=:imCode")
	public void deleteIMById(@Param("imCode") String imCode);
}
