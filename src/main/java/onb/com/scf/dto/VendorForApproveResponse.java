package onb.com.scf.dto;

import java.util.List;

import lombok.Data;
import onb.com.scf.entity.VendorEntity;

@Data
public class VendorForApproveResponse {
	private String status;
	private String statusCode;
	private String msg;
	List<VendorEntity> data;
}