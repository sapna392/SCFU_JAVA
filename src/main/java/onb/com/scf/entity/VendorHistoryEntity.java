package onb.com.scf.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "ONB_VENDOR_HISTORY")
public class VendorHistoryEntity {
	@Column
	@Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long vendorHistoryId;

	@Column
	private String vendorCode;

	// new feilds added
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
	private String defaultCreditAcctname;
	@Column
	private String defaultCreditAcctno;
	@Column
	private String isVendorAutoOnboarding;

	@Column
	private String loanAccNoOwnedBy;
	@Column
	private String primaryEmail;

	@Column
	private String vendorAdvanceAcctbrccode;
	@Column
	private String vendorAdvanceAcctname;
	@Column
	private String vendorAdvanceAcctno;
	@Column
	private String vendorCommissionCharges;
	@Column
	private String vendorDueDate;
	@Column
	private String vendorInterestRecoverOn;
	@Column
	private String vendorLimit;
	@Column
	private String vendorLimitExpireDate;
	@Column
	private String vendorNarrativeCount;
	@Column
	private String vendorPostageCharges;
	@Column
	private String vendorTaxCharges;
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
	@Column
	private String remark;

	public VendorHistoryEntity(VendorEntity vendor) {
		this.vendorCode = vendor.getVendorCode();
		this.imCode = vendor.getImCode();
		this.businessGroup = vendor.getBusinessGroup();
		this.creditAccBank = vendor.getCreditAccBank();
		this.creditAcctbrccode = vendor.getCreditAcctbrccode();
		this.creditAcctname = vendor.getCreditAcctname();
		this.creditAcctno = vendor.getCreditAcctno();
		this.creditCommisionChargeAcctbrccode = vendor.getCreditCommisionChargeAcctbrccode();
		this.creditCommisionChargeAcctname = vendor.getCreditCommisionChargeAcctname();
		this.creditCommisionChargeAcctno = vendor.getCreditCommisionChargeAcctno();
		this.creditInterestAcctbrccode = vendor.getCreditInterestAcctbrccode();
		this.creditInterestAcctname = vendor.getCreditInterestAcctname();
		this.creditInterestAcctno = vendor.getCreditInterestAcctno();

		this.debitAcctbrccode = vendor.getDebitAcctbrccode();
		this.debitAcctname = vendor.getDebitAcctname();
		this.debitAcctno = vendor.getDebitAcctno();
		this.debitCommisionChargeAcctbrccode = vendor.getDebitCommisionChargeAcctbrccode();
		this.debitCommisionChargeAcctname = vendor.getDebitCommisionChargeAcctname();
		this.debitCommisionChargeAcctno = vendor.getDebitCommisionChargeAcctno();
		this.debitInterestAcctbrccode = vendor.getDebitInterestAcctbrccode();
		this.debitInterestAcctname = vendor.getDebitInterestAcctname();
		this.debitInterestAcctno = vendor.getDebitInterestAcctno();
		this.debitProcessingChargeAcctname = vendor.getDebitProcessingChargeAcctname();
		this.debitProcessingChargeAcctbrccode = vendor.getDebitProcessingChargeAcctbrccode();
		this.debitProcessingChargeAcctno = vendor.getDebitProcessingChargeAcctno();
		this.defaultCreditAccBank = vendor.getDefaultCreditAccBank();

		this.defaultCreditAcctbrccode = vendor.getDefaultCreditAcctbrccode();
		this.defaultCreditAcctname = vendor.getDefaultCreditAcctname();
		this.defaultCreditAcctno = vendor.getDefaultCreditAcctno();
		this.loanAccNoOwnedBy = vendor.getLoanAccNoOwnedBy();
		this.primaryEmail = vendor.getPrimaryEmail();
		this.vendorAdvanceAcctbrccode = vendor.getVendorAdvanceAcctbrccode();
		this.vendorAdvanceAcctname = vendor.getVendorAdvanceAcctname();
		this.vendorAdvanceAcctno = vendor.getVendorAdvanceAcctno();
		this.vendorCommissionCharges = vendor.getVendorCommissionCharges();
		this.vendorDueDate = vendor.getVendorDueDate();
		this.vendorInterestRecoverOn = vendor.getVendorInterestRecoverOn();
		this.vendorLimit = vendor.getVendorLimit();
		this.vendorLimitExpireDate = vendor.getVendorLimitExpireDate();

		this.vendorNarrativeCount = vendor.getVendorNarrativeCount();
		this.vendorPostageCharges = vendor.getVendorPostageCharges();
		this.vendorTaxCharges = vendor.getVendorTaxCharges();
		this.name = vendor.getName();
		this.nickName = vendor.getNickName();
		this.vendorOnboardedFromSourceId = vendor.getVendorOnboardedFromSourceId();
		this.status = vendor.getStatus();
		this.authorizedBy = vendor.getAuthorizedBy();
		this.authorizedDate = vendor.getAuthorizedDate();
		this.deactivatedBy = vendor.getDeactivatedBy();
		this.deactivatedDate = vendor.getDeactivatedDate();
		this.creationTime = vendor.getCreationTime();
		this.createdBy = vendor.getCreatedBy();

		this.lastModTime = vendor.getLastModTime();
		this.email = vendor.getEmail();
		this.address1 = vendor.getAddress1();
		this.address2 = vendor.getAddress2();
		this.city = vendor.getCity();
		this.pincode = vendor.getPincode();
		this.district = vendor.getDistrict();
		this.state = vendor.getState();
		this.country = vendor.getCountry();
		this.vendorPan = vendor.getVendorPan();
		this.vendorTan = vendor.getVendorTan();
		this.isVendorInactive = vendor.getIsVendorInactive();
		this.phoneNo = vendor.getPhoneNo();
		this.fax = vendor.getFax();
		this.h2hopted = vendor.getH2hopted();
		this.remark = vendor.getRemark();
	}
}
