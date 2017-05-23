package com.vehliclefootmark.registration;

import com.vehliclefootmark.net.OnServiceRequestHandlerListener;

public interface OnRegistrationServiceHandlerListener  extends OnServiceRequestHandlerListener {

    void showErrorDialog(int errorCode);

    void onRegistrationSuccess();
}

