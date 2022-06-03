package com.scf.dto;

import java.io.Serializable;
import java.util.List;
import com.scf.model.Vendor;
import lombok.Data;
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
	 List<Vendor>listOfVendor;

}
