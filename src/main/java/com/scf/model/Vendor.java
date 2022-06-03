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

	//new feilds added
	@Column
	private String imCode;
	@Column
	private String businessGroup;
	@Column
	private String creditAccBank;
	@Column
	private String creditAcctbrccode;
	@Column
	private String creditAcctname;
	@Column
	private String creditAcctno;
	@Column
	private String creditCommisionChargeAcctbrccode;
	@Column
	private String creditCommisionChargeAcctname;
	@Column
	private String creditCommisionChargeAcctno;
	@Column
	private String creditInterestAcctbrccode;
	@Column
	private String creditInterestAcctname;
	@Column
	private String creditInterestAcctno;
	@Column
	private String debitAcctbrccode;
	@Column
	private String debitAcctname;
	@Column
	private String debitAcctno;
	@Column
	private String debitCommisionChargeAcctbrccode;
	@Column
	private String debitCommisionChargeAcctname;
	@Column
	private String debitCommisionChargeAcctno;
	@Column
	private String debitInterestAcctbrccode;
	@Column
	private String debitInterestAcctname;
	@Column
	private String debitInterestAcctno;
	@Column
	private String debitProcessingChargeAcctname;
	@Column
	private String debitProcessingChargeAcctbrccode;
	@Column
	private String debitProcessingChargeAcctno;
	@Column
	private String defaultCreditAccBank;
	@Column
	private String defaultCreditAcctbrccode;
	@Column
	private String	defaultCreditAcctname;
	@Column
	private String	defaultCreditAcctno;
	@Column
	private String 	isVendorAutoOnboarding;

	@Column
	private String 	loanAccNoOwnedBy;
	@Column
	private String 	primaryEmail;

	@Column
	private String 	vendorAdvanceAcctbrccode;
	@Column
	private String 		vendorAdvanceAcctname;
	@Column
	private String 		vendorAdvanceAcctno;
	@Column
	private String 		vendorCommissionCharges;
	@Column
	private String 	vendorDueDate;
	@Column
	private String 	vendorInterestRecoverOn;
	@Column
	private String 	vendorLimit;
	@Column
	private String 	vendorLimitExpireDate;
	@Column
	private String 	 vendorNarrativeCount;
	@Column
	private String 	vendorPostageCharges;
	@Column
	private String 	vendorTaxCharges;
	@Column
	private String name;
	@Column
	private String nickName;
	@Column
	private Number vendorOnboardedFromSourceId;
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