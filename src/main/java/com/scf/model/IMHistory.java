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
@Table (name = "ONB_IM_MASTER_HISTORY")
public class IMHistory
{




	@Column
	@Id
	//  	@SequenceGenerator(name = "IMSeqGenerator", sequenceName = "mySeq", initialValue = 100, allocationSize = 1000)
	//  	@GeneratedValue(generator = "IMSeqGenerator")
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
	private String advanceAcctname;
	@Column
	private String advanceAcctno;
	@Column
	private String advanceAcctbrccode;

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
	private String action;
	@Column
	private Number historyId;

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
	private Date imDueDate;
	@Column
	private Date imInterestRecoverOn;
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
	private Number defaults;
	@Column
	private Number min;
	@Column
	private Number max;
	@Column
	private Number penalROI;
	@Column
	private String backDatedInvoicing;
	@Column
	private Number mumberOfDaysFrom;
	@Column
	private Number mumberOfDaysTo;



	public IMHistory(IM im) {
		this.imId = im.getImId();
		this.imCode = im.getImCode();
		this.dealerCode = im.getDealerCode();
		this.vendorCode = im.getVendorCode();
		this.name = im.getName();
		this.nickName = im.getNickName();
		this.groupId = im.getGroupId();
		this.groupName = im.getGroupName();
		this.allowedDomain = im.getAllowedDomain();
		this.primaryEmail = im.getPrimaryEmail();
		this.scheduleRecptid = im.getScheduleRecptid();
		this.debitProcessingChargeAcctname = im.getDebitProcessingChargeAcctname();
		this.debitProcessingChargeAcctno = im.getDebitProcessingChargeAcctno();
		this.debitProcessingChargeAcctbrccode = im.getDebitProcessingChargeAcctbrccode();
		this.debitCommisionChargeAcctname = im.getDebitCommisionChargeAcctname();
		this.debitCommisionChargeAcctno = im.getDebitCommisionChargeAcctno();
		this.debitCommisionChargeAcctbrccode = im.getDebitCommisionChargeAcctbrccode();
		this.interbnkCommisionAcctname = im.getInterbnkCommisionAcctname();
		this.interbnkCommisionAcctno = im.getInterbnkCommisionAcctno();
		this.interbnkCommisionAcctbrccode = im.getInterbnkCommisionAcctbrccode();
		this.debitInterestAcctname = im.getDebitInterestAcctname();
		this.debitInterestAcctno = im.getDebitInterestAcctno();
		this.debitInterestAcctbrccode = im.getDebitInterestAcctbrccode();
		this.creditAcctname = im.getCreditAcctname();
		this.creditAcctno = im.getCreditAcctno();
		this.creditAcctbrccode = im.getCreditAcctbrccode();
		this.advanceAcctname = im.getAdvanceAcctname();
		this.advanceAcctno = im.getAdvanceAcctno();
		this.advanceAcctbrccode = im.getAdvanceAcctbrccode();
		this.status = im.getStatus();
		this.authorizedBy = im.getAuthorizedBy();
		this.authorizedDate = im.getAuthorizedDate();
		this.deactivatedBy = im.getDeactivatedBy();
		this.deactivatedDate = im.getDeactivatedDate();
		this.creationTime = im.getCreationTime();
		this.createdBy = im.getCreatedBy();
		this.lastModTime = im.getLastModTime();
		this.email = im.getEmail();
		this.address1 = im.getAddress1();
		this.address2 = im.getAddress2();
		this.city = im.getCity();
		this.pincode = im.getPincode();
		this.district = im.getDistrict();
		this.state = im.getState();
		this.country = im.getCountry();
		this.phoneNo = im.getPhoneNo();
		this.imPan = im.getImPan();
		this.imTan = im.getImTan();
		this.isImInactive = im.getIsImInactive();
		this.fax = im.getFax();
		this.action = im.getAction();
		this.historyId = im.getHistoryId();
		this.loanAccNosharedBy = im.getLoanAccNosharedBy();
		this.businessGroup = im.getBusinessGroup();
		this.backDatedInvoiceDays = im.getBackDatedInvoiceDays();
		this.backDatedInvoice = im.getBackDatedInvoice();
		this.transactionStatus = im.getTransactionStatus();
		this.isVendorAutoOnboarding = im.getIsVendorAutoOnboarding();
		this.penalInterestRate = im.getPenalInterestRate();
		this.npaModTime = im.getNpaModTime();
		this.h2hopted = im.getH2hopted();
		this.makerTime = im.getMakerTime();
		this.createdByMakerId = im.getCreatedByMakerId();
		this.isBusinessCorrespondentEnable = im.getIsBusinessCorrespondentEnable();
		this.typeBusinessCorrespondent = im.getTypeBusinessCorrespondent();
		this.imMappingTime = im.getImMappingTime();
		this.imMappingBy = im.getImMappingBy();
		this.imLimit = im.getImLimit();
		this.imLimitExpireDate = im.getImLimitExpireDate();
		this.imPostageCharges = im.getImPostageCharges();
		this.imCommissionCharges = im.getImCommissionCharges();
		this.imTaxCharges = im.getImTaxCharges();
		this.imDueDate = im.getImDueDate();
		this.imInterestRecoverOn = im.getImInterestRecoverOn();
		this.imVendorAutoOnboarding = im.getImVendorAutoOnboarding();
		this.sharedBy = im.getSharedBy();
		this.overrideCreditPeriodParameters = im.getOverrideCreditPeriodParameters();
		this.creditPeriod = im.getCreditPeriod();
		this.creditPeriodDefault = im.getCreditPeriodDefault();
		this.creditPeriodMin = im.getCreditPeriodMin();
		this.creditPeriodMax = im.getCreditPeriodMax();
		this.overrideRateofInt = im.getOverrideRateofInt();
		this.rateOfInt = im.getRateOfInt();
		this.effectiveIntRate = im.getEffectiveIntRate();
		this.selectParameterToOverride = im.getSelectParameterToOverride();
		this.addorDeduct = im.getAddorDeduct();
		this.defaults = im.getDefaults();
		this.min = im.getMin();
		this.max = im.getMax();
		this.penalROI = im.getPenalROI();
		this.backDatedInvoicing = im.getBackDatedInvoicing();
		this.mumberOfDaysFrom = im.getMumberOfDaysFrom();
		this.mumberOfDaysTo = im.getMumberOfDaysTo();
	}



	public IMHistory() {
		super();
	}








}