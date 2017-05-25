package com.vehliclefootmark.fuel;


import com.vehliclefootmark.net.OnServiceRequestHandlerListener;

public interface OnFuelEntryServiceHandlerListener extends OnServiceRequestHandlerListener{

    void showErrorDialog(int errorCode);

    void onSuccessFuelEntry();
}
