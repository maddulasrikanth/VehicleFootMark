package com.vehliclefootmark.reports.fuel;


import com.vehliclefootmark.net.OnServiceRequestHandlerListener;

import java.util.List;

public interface OnFuelFetchServiceHandlerListener extends OnServiceRequestHandlerListener {

    void showErrorDialog(int errorCode);

    void onSuccessFuelFetch(List<Fuel> fuelList);
}
