package com.scf.serviceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scf.model.Dealer;
import com.scf.repository.DealerRepository;


/**
 * @author Naseem
 *
 */
@Service
public class DealerService 
{
@Autowired
DealerRepository dealerRepository;


/**
 * Getting all dealer record by using the method findaAll()
 * @return dealer
 */
public List<Dealer> getAllDealer() 
{
List<Dealer> dealer = new ArrayList<Dealer>();
dealerRepository.findAll().forEach(dealers -> dealer.add(dealers));
return dealer;
}


/**
 * Getting a specific record by using the method findById()
 * @param id
 * @return dealer
 */
public Optional<Dealer> getDealerByCode(int id) 
{
return dealerRepository.findById(id);
}


/**
 * Saving a specific record by using the method save()
 * @param dealer
 */
public void saveOrUpdate(Dealer dealer) 
{
	dealerRepository.save(dealer);
}


/**
 * Deleting a specific record by using the method deleteById()
 * @param id
 */
public void delete(int id) 
{
	dealerRepository.deleteById(id);
}


}