package onb.com.scf.constant;

public class StatusConstant {

	private StatusConstant() {
	}

	public static final String STATUS_SUCCESS_CODE = "200";
	public static final String STATUS_FAILURE_CODE = "500";
	public static final String STATUS_DATA_NOT_FOUND_CODE = "404";
	public static final String STATUS_SUCCESS = "Success";
	public static final String STATUS_FAILURE = "Failure";
	public static final String STATUS_DESCRIPTION_IM_LIST_RETRIVED_SUCESSFULLY = "List of IM retreived successfully !";
	public static final String STATUS_DESCRIPTION_IM_RETRIVED_SUCESSFULLY = "IM retreived successfully !";
	public static final String STATUS_DESCRIPTION_IM_ADDED_SUCESSFULLY = "IM added successfully !";
	public static final String STATUS_DATA_NOT_AVAILAIBLE = "Data not available !";
	public static final String STATUS_PREFIX_NOT_AVAILAIBLE = "Prefix Details Not Found";
	public static final String STATUS_IM_UPDATED_SUCCESSFULLY = "IM updated successfully !";
	public static final String STATUS_IM_DEACTIVATED_SUCCESSFULLY = "IM Deactivated successfully !";
	public static final String STATUS_IM_ACTIVATED_SUCCESSFULLY = "IM Activated successfully !";
	public static final String STATUS_IM_DELETED_SUCCESSFULLY = "IM Deleted successfully !";

	public static final String EXCEPTION_OCCURRED = "Exception Occurred!";

	public static final String STATUS_DESCRIPTION_VENDOR_LIST_RETRIVED_SUCESSFULLY = "List of Vendors retreived successfully !";
	public static final String STATUS_DESCRIPTION_VENDOR_DEACTIVATED_SUCESSFULLY = "Vendor Deactivated successfully for  !";
	public static final String STATUS_DESCRIPTION_VENDOR_UPDATED_SUCESSFULLY = "Vendor Details Updated successfully !";
	public static final String STATUS_DESCRIPTION_VENDOR_DELETED_SUCESSFULLY = "Vendor Details Deleted successfully for!";
	public static final String STATUS_DESCRIPTION_VENDOR_RETRIVED_SUCESSFULLY = "Vendor Details Retrived successfully !";
	public static final String STATUS_DESCRIPTION_VENDOR_ADDED_SUCESSFULLY = "Vendor Added successfully !";
	public static final String STATUS_IM_CODE_NOT_FOUND = "IM Code Not Found !";
	public static final String IM_PENDING_STATUS = "Pending";
	public static final String IM_AUTHORISED_STATUS = "Authorised";
	public static final String VENDOR_PENDING_STATUS = "Pending";
	public static final String VENDOR_AUTHORISED_STATUS = "Authorised";
	public static final String STATUS_DESCRIPTION_VENDOR_ACTIVATED_SUCESSFULLY = "Vendor Activated successfully for  !";

	public static final String STATUS_DESCRIPTION_PREAUTH_IM_RETRIVED_SUCESSFULLY = "Preauth IM retreived successfully !";
	public static final String STATUS_DESCRIPTION_PREAUTH_IM_AUTHORISED_SUCESSFULLY = "IM Authorised successfully !";
	public static final String STATUS_DATA_NOT_AVAILAIBLE_FOR_APPROVE_IM = "Data Not Availaible for approve!";

	public static final String STATUS_DESCRIPTION_PREAUTH_VENDOR_RETRIVED_SUCESSFULLY = "Preauth Vendor retreived successfully !";

	public static final String STATUS_DESCRIPTION_PREAUTH_VENDOR_AUTHORISED_SUCESSFULLY = "Vendor Authorised successfully !";

	public static final short VENDOR_ONB_FROM_SCF = 1;
	public static final short VENDOR_ONB_FROM_LLMS = 2;

	public static final String STATUS_DESCRIPTION_VENDOR_ONBOARDED_FROM_LLMS = "Vendor Onboarded from LLMS so Edit Not Allowed !";

	public static final String ACTION_ADD_IM_STATUS = "waiting for created approval";
	public static final String ACTION_UPDATE_IM_STATUS = "waiting for edited approval";
	public static final String ACTION_DELETE_IM_STATUS = "waiting for deleted approval";
	public static final String ACTION_ACTIVATE_IM_STATUS = "ACTIVATE IM";
	public static final String ACTION_DEACTIVATE_IM_STATUS = "waiting for deactivate  approval";

	public static final String ACTION_ADD_IM = "Add IM";
	public static final String ACTION_UPDATE_IM = "Edit IM";
	public static final String ACTION_DELETE_IM = "Delete IM";
	public static final String ACTION_ACTIVATE_IM = "ACTIVATE IM";
	public static final String ACTION_DEACTIVATE_IM = "Deactivate IM";

	public static final String ACTION_ADD_VENDOR = "Add Vendor";
	public static final String ACTION_UPDATE_VENDOR = "Edit Vendor";
	public static final String ACTION_DELETE_VENDOR = "Delete Vendor";
	public static final String ACTION_ACTIVATE_VENDOR = "ACTIVATE Vendor";
	public static final String ACTION_DEACTIVATE_VENDOR = "Deactivate Vendor";

	public static final String STATUS_IM_NOT_DEACTIVATE = "IM Not Authorised so cant deactivate  !";

	public static final String STATUS_VENDOR_CODE_NOT_FOUND = "Vendor Code Not Found !";

	public static final String ACTION_ADD_VENDOR_STATUS = "waiting for created approval";
	public static final String ACTION_UPDATE_VENDOR_STATUS = "waiting for edited approval";
	public static final String ACTION_DELETE_VENDOR_STATUS = "waiting for deleted approval";
	public static final String ACTION_ACTIVATE_VENDOR_STATUS = "ACTIVATE IM";
	public static final String ACTION_DEACTIVATE_VENDOR_STATUS = "waiting for deactivate  approval";

	public static final String STATUS_VENDOR_NOT_DEACTIVATE = "Vendor Not Authorised so cant deactivate  !";

	public static final String PAN_CARD_ALREADY_MAPPED = "Pand Card Already Exist";

	public static final String STATUS_PAN_NUMBER_NOT_FOUND = "PAN Card Number Not Found !";
	
	public static final short IM_DATA_IDENTIFIER     = 1;
	public static final short VENDOR_DATA_IDENTIFIER = 2;
	
	public static final String DATA_IDENTIFIER_NOT_FOUND = "Data Identifier Not Found !";
	
	
}
