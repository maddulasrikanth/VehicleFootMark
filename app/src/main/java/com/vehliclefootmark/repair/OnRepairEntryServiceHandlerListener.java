package com.vehliclefootmark.repair;


import com.vehliclefootmark.net.OnServiceRequestHandlerListener;

public interface OnRepairEntryServiceHandlerListener  extends OnServiceRequestHandlerListener {

    void showErrorDialog(int errorCode);

    void onSuccessRepairEntry();
}
