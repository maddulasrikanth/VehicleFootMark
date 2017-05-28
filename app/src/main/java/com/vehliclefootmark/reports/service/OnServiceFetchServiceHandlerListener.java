package com.vehliclefootmark.reports.service;

import com.vehliclefootmark.net.OnServiceRequestHandlerListener;

import java.util.List;

public interface OnServiceFetchServiceHandlerListener extends OnServiceRequestHandlerListener {

    void showErrorDialog(int errorCode);

    void onSuccessServiceFetch(List<ServiceDTO> serviceList);
}