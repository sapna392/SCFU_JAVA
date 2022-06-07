package onb.com.scf.dto;

import java.util.List;

import lombok.Data;
import onb.com.scf.entity.VendorPreAuthEntity;

@Data
public class VendorPreAuthResponse {
	private String status;
	private String statusCode;
	private String msg;
	List<VendorPreAuthEntity> listOfPreAuthVendor;
}