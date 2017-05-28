package com.vehliclefootmark.reports.repair;

import com.vehliclefootmark.net.OnServiceRequestHandlerListener;

import java.util.List;

public interface OnRepairFetchServiceHandlerListener extends OnServiceRequestHandlerListener {

    void showErrorDialog(int errorCode);

    void onSuccessRepairFetch(List<Repair> repairList);
}