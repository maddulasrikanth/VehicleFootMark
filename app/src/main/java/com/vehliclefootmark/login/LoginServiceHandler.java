package com.vehliclefootmark.login;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.vehliclefootmark.net.GenericServiceHandler;
import com.vehliclefootmark.R;
import com.vehliclefootmark.constants.URLConstant;
import com.vehliclefootmark.customexception.VehicleFootMarkException;
import com.vehliclefootmark.net.NetworkManager;
import com.vehliclefootmark.net.RequestPacket;
import com.vehliclefootmark.util.UIUtils;

import java.util.HashMap;

public class LoginServiceHandler extends GenericServiceHandler {
	private final String TAG = "LoginServiceHandler";
	OnLoginServiceHandlerListener mOnLoginServiceHandlerListener;

	public LoginServiceHandler(Context context) {
		super(context);
		mOnLoginServiceHandlerListener=(OnLoginServiceHandlerListener) context;
	}

	public void doLoginRequest(Context context, String username, String password, boolean isAdmin) {

		String url = URLConstant.BASE_URL + URLConstant.LOGIN_PATH;
		HashMap<String, String> paramters = new HashMap<String, String>();
		paramters.put("un", username);
		paramters.put("pw", password);
		paramters.put("isadmin", isAdmin+"");

		RequestPacket requestPacket = new RequestPacket(url, NetworkManager.METHOD[1], paramters, "");
		UIUtils.showProgressDialog(context, context.getString(R.string.lbl_progress_bar_message));
		execute(requestPacket);
	}

	@Override
	protected void processResult(String result) throws JsonSyntaxException, VehicleFootMarkException {
        Log.i(TAG, "LOGIN RESULT_1:: " + result);
        LoginResponseDTO loginResponse = new Gson().fromJson(result,new TypeToken<LoginResponseDTO>() {}.getType());
		Log.i(TAG, "LOGIN getEmail:: " + loginResponse.getEmail());
		mOnLoginServiceHandlerListener.onSuccessLogin();
	}
	private void showDialog(int errorCode) {
		mOnLoginServiceHandlerListener.showErrorDialog(errorCode);
	}

}
