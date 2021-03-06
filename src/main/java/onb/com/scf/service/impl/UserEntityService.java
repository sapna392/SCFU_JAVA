package onb.com.scf.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import onb.com.scf.entity.UserEntity;
import onb.com.scf.repository.UserEntityRepository;

/**
 * @author Naseem
 *
 */
@Service
public class UserEntityService {
	@Autowired
	UserEntityRepository userEntityRepository;

	/**
	 * Getting a specific record by using the method findById()
	 * 
	 * @param entityType
	 * @return entity type
	 */
	public Optional<UserEntity> getEntityType(String entityType) {
		return userEntityRepository.findById(entityType);
	}
}