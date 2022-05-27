package com.scf.serviceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scf.model.IM;
import com.scf.model.UserEntity;
import com.scf.repository.IMRepository;


/**
 * @author Naseem
 *
 */
@Service
public class IMService 
{
	@Autowired
	IMRepository imRepository;

	@Autowired
	UserEntityService userEntityService;


	/**
	 * Getting all im record by using the method findaAll()
	 * @return im
	 */
	public List<IM> getAllIM() 
	{
		List<IM> im = new ArrayList<IM>();
		imRepository.findAll().forEach(ims -> im.add(ims));
		return im;
	}


	/**
	 * Getting a specific record by using the method findById()
	 * @param id
	 * @return im
	 */
	public Optional<IM> getIMByCode(int id) 
	{
		return imRepository.findById(id);
	}


	/**
	 * Saving a specific record by using the method save()
	 * @param im
	 */
	public void saveOrUpdate(IM im) 
	{
		Optional<UserEntity> userEntityData =userEntityService.getEntityType("IM");
		String prefix=userEntityData.get().getPrefix();
		try {
		Long maxId=imRepository.getTopId();
		System.out.println("maxId:" +maxId);
		if(maxId==null){
			int stratValue=userEntityData.get().getStratValue();
			im.setImCode(prefix+stratValue);	
		}else {
			im.setImCode(prefix+(maxId+1));
		}
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
	
		imRepository.save(im);
	}



	/**
	 * Deleting a specific record by using the method deleteById()
	 * @param id
	 */
	public void delete(int id) 
	{
		imRepository.deleteById(id);
	}

	public Long getTopId() 
	{
		return imRepository.getTopId();
	}
	//updating a record



}