package onb.com.scf.dto;

import java.util.List;

import lombok.Data;
import onb.com.scf.entity.IMEntity;
import onb.com.scf.entity.VendorEntity;
@Data
public class IMAndVendorResponse {
	private String status;
	private String statusCode;
	private String msg;
	List<IMEntity> imdata;
	List<VendorEntity> vendorData;
}
