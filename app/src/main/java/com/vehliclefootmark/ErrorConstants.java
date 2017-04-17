package com.vehliclefootmark;

import android.annotation.SuppressLint;
import java.util.HashMap;
import java.util.Map;

@SuppressLint("UseSparseArrays")
public class ErrorConstants {

	public static final int SOCKET_TIMEOUT_EXCEPTION = 10001;
	public static final int NETWORK_ERROR = 10002;
	public static final int URL_NOT_FOUND = 10003;
	public static final int AUTHENTICATION_ERROR = 10004;
	
	public static final int PASSWORD_NOT_MATCH = 50001;
	public static final int NOT_VALID_NAME = 50002;
	public static final int NOT_VALID_EMAIL = 50003;
	public static final int PASSWORD_LESS_THAN_4 = 50004;
	public static final int PASSWORD_MORE_THAN_50 = 50005;
	public static final int CONF_PASSWORD_LESS_THAN_4 = 50006;
	public static final int CONF_PASSWORD_MORE_THAN_50 = 50007;
	public static final int ZIP_EMPTY = 50008;
	public static final int ZIP_LESS_THAN_4 = 50009;
	public static final int ZIP_MORE_THAN_20 = 500010;
	public static final int PASSWORD_EMPTY = 500011;
	public static final Map<Integer, String> hmErrorList;
	public static final int NOT_VALID_LAST_NAME = 500012;
	
	/*Login*/
	
	public static final int MSG_EMAIL_BLANK = 600001;
	public static final int MSG_EMAIL_INVALID = 600002;
	public static final int MSG_PASSWORD_BLANK = 600003;
	public static final int MSG_PASSWORD_INVALID = 600004;
	public static final int MSG_EMAIL_PWD_BLANK = 600005;
	public static final int MSG_EMAIL_PWD_INVALID= 600006;
	public static final int MSG_USER_DOESNOT_EXIST= 600007;
	public static final int MSG_NO_INTERNET= 600008;
	
	
	
	
	
	
	static {
		hmErrorList = new HashMap<Integer, String>();
		hmErrorList.put(SOCKET_TIMEOUT_EXCEPTION, "Unable to reach Server. Please try after some time.");
		hmErrorList.put(NETWORK_ERROR, "Unable to reach Server. Please try after some time.");
		hmErrorList.put(URL_NOT_FOUND, "Unable to reach Server. Please try after some time.");
		hmErrorList.put(AUTHENTICATION_ERROR, "Unable to reach Server. Please try after some time.");
		hmErrorList.put(PASSWORD_NOT_MATCH, " Password and Confirm Password do not match.");
		hmErrorList.put(NOT_VALID_NAME, "Name Not Valid.");
		hmErrorList.put(NOT_VALID_LAST_NAME, "Last Name is Not Valid.");
		hmErrorList.put(NOT_VALID_EMAIL, "Please enter a valid Email Address.");
		hmErrorList.put(PASSWORD_LESS_THAN_4, "Password should contain minimum of 4 characters.");
		hmErrorList.put(PASSWORD_MORE_THAN_50, "Password should not exceed 50 characters.");
		hmErrorList.put(CONF_PASSWORD_LESS_THAN_4, "Confirm Password should contain minimum of 4 characters.");
		hmErrorList.put(CONF_PASSWORD_MORE_THAN_50, "Confirm Password should not exceed 50 characters.");
		hmErrorList.put(ZIP_EMPTY, "ZIP field is required.");
		hmErrorList.put(ZIP_LESS_THAN_4, "ZIP should contain minimum of 4 digits.");
		hmErrorList.put(ZIP_MORE_THAN_20, "ZIP should not exceed 20 digits.");
		hmErrorList.put(PASSWORD_EMPTY, "Password is blank.");
		
		hmErrorList.put(MSG_EMAIL_BLANK, "Email Address is required.");
		hmErrorList.put(MSG_EMAIL_INVALID, "Please enter valid Email Address.");
		hmErrorList.put(MSG_PASSWORD_BLANK, "Password is required.");
		hmErrorList.put(MSG_PASSWORD_INVALID, "Please enter valid Password.");
		hmErrorList.put(MSG_EMAIL_PWD_BLANK, "Email Address and Password are blank.");
		hmErrorList.put(MSG_EMAIL_PWD_INVALID, "Please enter valid Email Address and Password.");
		hmErrorList.put(MSG_USER_DOESNOT_EXIST, "User does not exist.");
		hmErrorList.put(MSG_NO_INTERNET, "Internet connectivity appears to be offline.");
		
		
	} 

}
