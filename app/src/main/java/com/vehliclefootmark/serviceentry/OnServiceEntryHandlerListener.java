package com.vehliclefootmark.serviceentry;


import com.vehliclefootmark.net.OnServiceRequestHandlerListener;

public interface OnServiceEntryHandlerListener  extends OnServiceRequestHandlerListener {

    void showErrorDialog(int errorCode);

    void onSuccessEntry();
}
