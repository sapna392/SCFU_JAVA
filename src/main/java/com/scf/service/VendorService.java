package com.scf.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.scf.dto.IMDetailsResponseDto;
import com.scf.dto.ResponseDto;
import com.scf.dto.VendorDeactivateRequest;
import com.scf.dto.VendorDetailsResponseDto;
import com.scf.model.Vendor;
/**
 * @author Sapna Singh
 *
 */
@Service
public interface VendorService {

	VendorDetailsResponseDto getAllVendor();

	VendorDetailsResponseDto getVendorByCode(String vendorCode);

	ResponseDto deleteById(String vendorId);

	ResponseDto addVendor(Vendor vendor);

	void update(Vendor vendor);

	ResponseDto deActivate(VendorDeactivateRequest request);

}
