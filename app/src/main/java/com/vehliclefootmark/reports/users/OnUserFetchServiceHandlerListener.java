package com.vehliclefootmark.reports.users;

import com.vehliclefootmark.login.LoginResponseDTO;
import com.vehliclefootmark.net.OnServiceRequestHandlerListener;

import java.util.List;

public interface OnUserFetchServiceHandlerListener extends OnServiceRequestHandlerListener {

    void showErrorDialog(int errorCode);

    void onSuccessUsersFetch(List<LoginResponseDTO> userList);
}