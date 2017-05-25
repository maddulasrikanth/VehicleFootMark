package com.vehliclefootmark.login;

import com.vehliclefootmark.net.OnServiceRequestHandlerListener;

public interface OnLoginServiceHandlerListener extends OnServiceRequestHandlerListener {
	
	void showErrorDialog(int errorCode);

	void onSuccessLogin(LoginResponseDTO loginResponse);
}
