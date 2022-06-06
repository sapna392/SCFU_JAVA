package onb.com.scf.entity;

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
@Table(name = "ONB_IM_MASTER")
public class IMEntity {

	@Column
	@Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long imId;

	@Column
	private String imCode;
	@Column
	private String dealerCode;
	@Column
	private String vendorCode;
	@Column
	private String name;
	@Column
	private String nickName;
	@Column
	private String groupId;
	@Column
	private String groupName;
	@Column
	private String allowedDomain;
	@Column
	private String primaryEmail;
	@Column
	private String scheduleRecptid;

	@Column
	private String debitProcessingChargeAcctname;
	@Column
	private String debitProcessingChargeAcctno;
	@Column
	private String debitProcessingChargeAcctbrccode;

	@Column
	private String debitCommisionChargeAcctname;
	@Column
	private String debitCommisionChargeAcctno;
	@Column
	private String debitCommisionChargeAcctbrccode;

	@Column
	private String interbnkCommisionAcctname;
	@Column
	private String interbnkCommisionAcctno;
	@Column
	private String interbnkCommisionAcctbrccode;

	@Column
	private String debitInterestAcctname;
	@Column
	private String debitInterestAcctno;
	@Column
	private String debitInterestAcctbrccode;

	@Column
	private String creditAcctname;
	@Column
	private String creditAcctno;
	@Column
	private String creditAcctbrccode;

	@Column
	private String debitAcctbrccode;
	@Column
	private String debitAcctname;
	@Column
	private String debitAcctno;

	@Column
	private String advanceAcctname;
	@Column
	private String advanceAcctno;
	@Column
	private String advanceAcctbrccode;

	@Column
	private Number historyId;

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
	private String phoneNo;

	@Column
	private String imPan;
	@Column
	private String imTan;

	@Column
	private Boolean isImInactive;

	@Column
	private String fax;
	@Column
	private String hostUniken;
	@Column
	private String action;
	@Column
	private String loanAccNoOwnedBy;
	@Column
	private String loanAccNosharedBy;
	@Column
	private String businessGroup;
	@Column
	private String backDatedInvoiceDays;
	@Column
	private String backDatedInvoice;
	@Column
	private String transactionStatus;
	@Column
	private Boolean isVendorAutoOnboarding;
	@Column
	private String penalInterestRate;
	@Column
	private Date npaModTime;

	@Column
	private String h2hopted;

	@Column
	private Date makerTime;
	@Column
	private String createdByMakerId;

	@Column
	private Boolean isBusinessCorrespondentEnable;

	@Column
	private String typeBusinessCorrespondent;

	@Column
	private Date imMappingTime;
	@Column
	private String imMappingBy;
	@Column
	private Number imLimit;
	@Column
	private Date imLimitExpireDate;
	@Column
	private Number imPostageCharges;
	@Column
	private Number imCommissionCharges;
	@Column
	private Number imTaxCharges;
	@Column
	private String imDueDate;
	@Column
	private String imInterestRecoverOn;
	@Column
	private String imVendorAutoOnboarding;
	@Column
	private String sharedBy;
	@Column
	private String overrideCreditPeriodParameters;
	@Column
	private String creditPeriod;
	@Column
	private Number creditPeriodDefault;
	@Column
	private Number creditPeriodMin;
	@Column
	private Number creditPeriodMax;
	@Column
	private String overrideRateofInt;
	@Column
	private Number rateOfInt;
	@Column
	private Number effectiveIntRate;
	@Column
	private String selectParameterToOverride;
	@Column
	private String addorDeduct;
	@Column
	private Number rateOfInterestDefault;
	@Column
	private Number rateOfInterestMin;
	@Column
	private Number rateOfInterestMax;
	@Column
	private Number penalROI;
	@Column
	private String backDatedInvoicing;
	@Column
	private String noOfDaysFrom;
	@Column
	private String noOfDaysTo;

}