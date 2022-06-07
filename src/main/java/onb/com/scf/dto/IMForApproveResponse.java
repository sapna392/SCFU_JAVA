package onb.com.scf.dto;

import java.util.List;

import lombok.Data;
import onb.com.scf.entity.IMPreauthEntity;

@Data
public class IMForApproveResponse {
	private String status;
	private String statusCode;
	private String msg;
	List<IMPreauthEntity> listOfPreAuthIM;
}