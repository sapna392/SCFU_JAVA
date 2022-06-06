package onb.com.scf.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import onb.com.scf.entity.VendorEntity;
/**
 * @author Sapna Singh
 *
 */
@Data
public class VendorDetailsResponseDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 724731592282454754L;
	private String status;
	 private String statusCode;
	 private String msg;
	 List<VendorEntity>listOfVendor;

}
