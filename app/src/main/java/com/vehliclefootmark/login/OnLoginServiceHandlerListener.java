package com.vehliclefootmark.login;

public interface OnLoginServiceHandlerListener extends OnServiceRequestHandlerListener{
	
	void showErrorDialog(int errorCode);
}
