package onb.com.scf.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import onb.com.scf.entity.VendorHistoryEntity;
@Repository
public interface VendorHistoryRepository extends CrudRepository<VendorHistoryEntity, Long> {

}
