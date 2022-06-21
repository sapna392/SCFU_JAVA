package onb.com.scf.dto;

import java.util.List;

import lombok.Data;
import onb.com.scf.entity.VendorEntity;
import onb.com.scf.entity.VendorFileReadEntity;
import onb.com.scf.entity.VendorPreAuthEntity;


@Data
public class VendorEntityResponse {

	private String status;
	private String statusCode;
	private String msg; 
	List<VendorFileReadEntity> data;
}
