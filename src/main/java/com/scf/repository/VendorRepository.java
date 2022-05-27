package com.scf.repository;
import org.springframework.data.repository.CrudRepository;
import com.scf.model.Vendor;


/**
 * @author Naseem
 *
 */
public interface VendorRepository extends CrudRepository<Vendor, Integer>
{
}
