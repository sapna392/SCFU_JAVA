package com.scf.dto;

import lombok.Data;

@Data
public class VendoActivateRequest {
	private String vendorCode;
	private Boolean isVendorInActive;
}