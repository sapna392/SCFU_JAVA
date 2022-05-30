package com.scf.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.scf.model.IM;
import com.scf.model.IMHistory;


/**
 * @author Naseem
 *
 */
public interface IMHistoryRepository extends JpaRepository<IMHistory, String>
{
	
}
