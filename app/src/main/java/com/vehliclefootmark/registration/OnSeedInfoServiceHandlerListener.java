package com.vehliclefootmark.registration;


import com.vehliclefootmark.net.OnServiceRequestHandlerListener;

import java.util.List;

public interface OnSeedInfoServiceHandlerListener  extends OnServiceRequestHandlerListener {

    void showErrorDialog(int errorCode);

    void onSuccessResponse(List<SeedInfo> seedInfoList);
}
