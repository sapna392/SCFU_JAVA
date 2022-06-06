package onb.com.scf.dto;

import lombok.Data;

@Data
public class VendorDeactivateRequest {
	private String vendorCode;
	private Boolean isVendorDeativate;

}

