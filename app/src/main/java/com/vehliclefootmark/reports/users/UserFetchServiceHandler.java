package com.vehliclefootmark.reports.users;


import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.vehliclefootmark.R;
import com.vehliclefootmark.constants.URLConstant;
import com.vehliclefootmark.customexception.VehicleFootMarkException;
import com.vehliclefootmark.net.GenericServiceHandler;
import com.vehliclefootmark.net.NetworkManager;
import com.vehliclefootmark.net.RequestPacket;
import com.vehliclefootmark.reports.fuel.FuelFetchDTO;
import com.vehliclefootmark.util.UIUtils;

import java.util.HashMap;

public class UserFetchServiceHandler extends GenericServiceHandler {
    private final String TAG = "FuelFetchHandler";
    OnUserFetchServiceHandlerListener mOnUserFetchServiceHandlerListener;

    public UserFetchServiceHandler(Context context) {
        super(context);
        mOnUserFetchServiceHandlerListener =(OnUserFetchServiceHandlerListener) context;
    }

    public void getUsersListRequest(Context context) {

        String url = URLConstant.BASE_URL + URLConstant.NON_ADMIN_USERS_FETCH_PATH;
        HashMap<String, String> parameters = new HashMap<String, String>();

        RequestPacket requestPacket = new RequestPacket(url, NetworkManager.METHOD[1], parameters, "");
        UIUtils.showProgressDialog(context, context.getString(R.string.lbl_progress_bar_message));
        execute(requestPacket);
    }

    @Override
    protected void processResult(String result) throws JsonSyntaxException, VehicleFootMarkException {
        Log.i(TAG, "RESULT_1:: " + result);
        UserFetchDTO userFetchDTO = new Gson().fromJson(result,new TypeToken<UserFetchDTO>() {}.getType());
        Log.i(TAG, "UserFetchDTO:: " + userFetchDTO.userList.size());
        mOnUserFetchServiceHandlerListener.onSuccessUsersFetch(userFetchDTO.userList);
    }

}
