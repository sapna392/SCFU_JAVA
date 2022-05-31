package com.scf.model;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
 

/**
 * @author Naseem
 *
 */
@Entity
@Data
@Table (name = "ONB_VENDOR_MASTER")
public class Vendor
{
	
	@Column
	  @Id
//	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long vendorSeqCode;

@Column
private String vendorCode;

@Column
private String name;
@Column
private String nickName;
@Column
private String status;
@Column
private String authorizedBy;
@Column
private Date authorizedDate;
@Column
private String deactivatedBy;
@Column
private Date deactivatedDate;
@Column
private Date creationTime;
@Column
private String createdBy;
@Column
private Date lastModTime;
@Column
private String email;
@Column
private String address1;
@Column
private String address2;
@Column
private String city;
@Column
private int pincode;
@Column
private String district;
@Column
private String state;
@Column
private String country;
@Column
private String vendorPan;
@Column
private String vendorTan;

@Column
private Boolean isVendorInactive;

@Column
private String phoneNo;
@Column
private String fax;
@Column
private String h2hopted;


}