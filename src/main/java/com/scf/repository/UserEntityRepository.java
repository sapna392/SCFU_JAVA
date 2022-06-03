package com.scf.repository;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.scf.model.UserEntity;


/**
 * @author Naseem
 *
 */
public interface UserEntityRepository extends CrudRepository<UserEntity, String>
{
	Optional<UserEntity> findById(String id);
}
