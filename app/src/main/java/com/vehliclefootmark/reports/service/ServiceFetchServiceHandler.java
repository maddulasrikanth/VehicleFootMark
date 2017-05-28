package com.vehliclefootmark.reports.service;

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
import com.vehliclefootmark.util.UIUtils;

import java.util.HashMap;

public class ServiceFetchServiceHandler extends GenericServiceHandler {
    private final String TAG = "ServiceFetchHandler";
    OnServiceFetchServiceHandlerListener mOnServiceFetchServiceHandlerListener;

    public ServiceFetchServiceHandler(Context context) {
        super(context);
        mOnServiceFetchServiceHandlerListener =(OnServiceFetchServiceHandlerListener) context;
    }

    public void getServiceReportRequest(Context context, int userID, long fromDate, long toDate) {

        String url = URLConstant.BASE_URL + URLConstant.SERVICE_FETCH_PATH;
        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("rUserId", userID+"");
        parameters.put("fromDate", fromDate+"");
        parameters.put("toDate", toDate+"");

        RequestPacket requestPacket = new RequestPacket(url, NetworkManager.METHOD[1], parameters, "");
        UIUtils.showProgressDialog(context, context.getString(R.string.lbl_progress_bar_message));
        execute(requestPacket);
    }

    @Override
    protected void processResult(String result) throws JsonSyntaxException, VehicleFootMarkException {
        Log.i(TAG, "RESULT_1:: " + result);
        ServiceFetchDTO serviceFetchDTO = new Gson().fromJson(result,new TypeToken<ServiceFetchDTO>() {}.getType());
        Log.i(TAG, "ServiceFetchDTO:: " + serviceFetchDTO.serviceList.size());
        mOnServiceFetchServiceHandlerListener.onSuccessServiceFetch(serviceFetchDTO.serviceList);
    }

}

