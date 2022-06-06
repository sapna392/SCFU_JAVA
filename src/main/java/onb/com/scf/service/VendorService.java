package onb.com.scf.service;

import org.springframework.stereotype.Service;

import onb.com.scf.dto.ResponseDto;
import onb.com.scf.dto.VendoActivateRequest;
import onb.com.scf.dto.VendorDeactivateRequest;
import onb.com.scf.dto.VendorDetailsResponseDto;
import onb.com.scf.entity.VendorEntity;

/**
 * @author Sapna Singh
 *
 */
@Service
public interface VendorService {

	VendorDetailsResponseDto getAllVendor();

	VendorDetailsResponseDto getVendorByCode(String vendorCode);

	ResponseDto deleteById(String vendorId);

	ResponseDto addVendor(VendorEntity vendor);

	void update(VendorEntity vendor);

	ResponseDto deActivate(VendorDeactivateRequest request);

	VendorDetailsResponseDto getAllVendorByImCode(String imCode);

	ResponseDto activateVendor(VendoActivateRequest request);

}
