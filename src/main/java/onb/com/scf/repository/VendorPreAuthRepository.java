package onb.com.scf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import onb.com.scf.entity.VendorPreAuthEntity;
@Repository
public interface VendorPreAuthRepository extends JpaRepository<VendorPreAuthEntity, Long> {

}
