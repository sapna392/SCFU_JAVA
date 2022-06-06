package onb.com.scf.validate;

import onb.com.scf.entity.IMEntity;

public class IMValidate {

	private IMValidate() {
	}

	public static final String STATUS_SUCCESS = "success";

	public static String validateIMData(IMEntity imModel) {
		String validateMessage = "";
		String[] imMobileNumbersArr = null;
		try {
			if (imModel.getName() == null || imModel.getName().isEmpty() || imModel.getName().equals("")
					|| imModel.getName().length() > 30) {
				validateMessage = "IM Name Cannot be empty or length cannot be greater than 30";
				return validateMessage;
			}
			if (imModel.getNickName() == null || imModel.getNickName().isEmpty() || imModel.getNickName().equals("")
					|| imModel.getNickName().length() > 30) {
				validateMessage = "IM Nickname Cannot be empty or length cannot be greater than 30 ";
				return validateMessage;
			}

			if (imModel.getPhoneNo() == null || imModel.getPhoneNo().isEmpty() || imModel.getPhoneNo().equals("")) {
				validateMessage = "Mobile Number Missing";
				return validateMessage;
			}
			imMobileNumbersArr = imModel.getPhoneNo().split(",");
			if (imMobileNumbersArr.length > 4) {
				validateMessage = "Maximum 4 Mobile Numbers Allowed only";
				return validateMessage;
			}
			validateMessage = STATUS_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return validateMessage;
	}
}
