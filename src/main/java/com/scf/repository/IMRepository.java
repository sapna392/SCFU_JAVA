package com.scf.repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.scf.model.IM;


/**
 * @author Naseem
 *
 */
public interface IMRepository extends CrudRepository<IM, Integer>
{

	@Query(nativeQuery = true,value="SELECT Top 1 im_id from ONB_IM_MASTER order by IM_ID desc")
	public Long getTopId();
}
