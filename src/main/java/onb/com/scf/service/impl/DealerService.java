package onb.com.scf.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import onb.com.scf.entity.DealerEntity;
import onb.com.scf.repository.DealerRepository;

/**
 * @author Naseem
 *
 */
@Service
public class DealerService {
	@Autowired
	DealerRepository dealerRepository;

	/**
	 * Getting all dealer record by using the method findaAll()
	 * 
	 * @return dealer
	 */
	public List<DealerEntity> getAllDealer() {
		List<DealerEntity> dealer = new ArrayList<>();
		dealerRepository.findAll().forEach(dealer::add);
		return dealer;
	}

	/**
	 * Getting a specific record by using the method findById()
	 * 
	 * @param id
	 * @return dealer
	 */
	public Optional<DealerEntity> getDealerByCode(int id) {
		return dealerRepository.findById(id);
	}

	/**
	 * Saving a specific record by using the method save()
	 * 
	 * @param dealer
	 */
	public void saveOrUpdate(DealerEntity dealer) {
		dealerRepository.save(dealer);
	}

	/**
	 * Deleting a specific record by using the method deleteById()
	 * 
	 * @param id
	 */
	public void delete(int id) {
		dealerRepository.deleteById(id);
	}
}