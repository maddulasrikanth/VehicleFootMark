package com.vehliclefootmark.login;

import java.util.HashMap;

import android.content.Context;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.vehliclefootmark.ErrorConstants;
import com.vehliclefootmark.GenericServiceHandler;
import com.vehliclefootmark.URLConstant;
import com.vehliclefootmark.customexception.VehicleFootMarkException;
import com.vehliclefootmark.net.NetworkManager;
import com.vehliclefootmark.net.RequestPacket;

public class LoginServiceHandler extends GenericServiceHandler {
	private final String TAG = "LoginServiceHandler";
	OnLoginServiceHandlerListener mOnLoginServiceHandlerListener;

	public LoginServiceHandler(Context context) {
		super(context);
		mOnLoginServiceHandlerListener=(OnLoginServiceHandlerListener) context;
	}

	public void doLoginRequest(String deviceId,String emaiId,String password,String deviceTypeCode,
			String deviceToken,String advertisingId){
		Log.i(TAG, "advertisingidentifier : "+advertisingId);
		if(emaiId.length()==0 && password.length()==0){
			showDialog(ErrorConstants.MSG_EMAIL_PWD_BLANK);
		}else if(emaiId.length()==0) {
			showDialog(ErrorConstants.MSG_EMAIL_BLANK);
		}else if(password.length()==0){
			showDialog(ErrorConstants.MSG_PASSWORD_BLANK);
		}else{
			/*if(!UIUtils.isValidEmail(emaiId) && !UIUtils.isValidPassword(password)){
				showDialog(ErrorConstants.MSG_EMAIL_PWD_INVALID);
			}else if (!UIUtils.isValidEmail(emaiId)) {
				showDialog(ErrorConstants.MSG_EMAIL_INVALID);
			}else if(!UIUtils.isValidPassword(password)){
				showDialog(ErrorConstants.MSG_PASSWORD_INVALID);
			}else{*/
				String url= URLConstant.BASE_URL+URLConstant.LOGIN_PATH;
				HashMap<String, String> paramters = new HashMap<String, String>();
				paramters.put("deviceid", deviceId);
				paramters.put("email", emaiId);
				paramters.put("userpwd", password);
				paramters.put("devicetypecode", deviceTypeCode);
				paramters.put("devicetoken", deviceToken);
				paramters.put("advertisingidentifier", advertisingId);

				RequestPacket requestPacket = new RequestPacket(url, NetworkManager.METHOD[0], paramters,"");
				/*UIUtils.showProgressDialog(context, "Please Wait...");*/
				execute(requestPacket);
			//}
		}
	}

	@Override
	protected void processResult(String result) throws JsonSyntaxException, VehicleFootMarkException {
		LoginStringResponse loginResponse = new Gson().fromJson(result,new TypeToken<LoginStringResponse>() {}.getType());
		Log.i(TAG, "LOGIN RESULT_1:: " + result);
		//Log.i(TAG, "LOGIN getActiveshoppinglistid():: " + loginResponse.getCustomers().getActiveshoppinglistid());
	}
	private void showDialog(int errorCode) {
		mOnLoginServiceHandlerListener.showErrorDialog(errorCode);
	}

}
