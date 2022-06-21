package onb.com.scf.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
public class VendorFileReadEntity {
	
	@Column(name = "VENDOR_NAME")
	private String vendorname;
	
	@Column(name="IM_CODE")
	private String imcode;

//	@Column(name="UPLOADED_BY")
//	private String uploadedby;
//	
//	@Column(name="UPLOAD_FILE_LOCATION")
//	private String uploadfilelocation;
	
//	@Column(name="CREATION_TIME")
//	@Temporal(value = TemporalType.TIMESTAMP)
//	private Date creationtime;

	@Column
	private String address;
	
	@Column
    private String city;
	
	@Column
	private String district;
	
	@Column
	private String state;
	
	@Column
	private String country;
	
	@Column
	private int pincode;
	
	@Column
	private String emailid;
	
	@Column
	private String mobileno;
	
	@Column
	private String fax;
	
	@Column
	private String additionalfields;
	
	@Column
	private String businessgroup;
	
	@Column
	private String creditaccountnumberwithsbi;
	
	@Column
	private String creditaccountnumber;
	
	@Column
	private String branchifscode;
	
	@Column
	private String defaultcreditaccountnnmberwithdsbiother;

}
