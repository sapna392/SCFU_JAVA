package com.scf.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.scf.model.IMHistory;


/**
 * @author Naseem
 *
 */
public interface IMHistoryRepository extends JpaRepository<IMHistory, String>
{
	
}
