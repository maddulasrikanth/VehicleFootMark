package com.vehliclefootmark.reports.fuel;


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

public class FuelFetchServiceHandler extends GenericServiceHandler {
    private final String TAG = "FuelEntryServiceHandler";
    OnFuelFetchServiceHandlerListener mOnFuelFetchServiceHandlerListener;

    public FuelFetchServiceHandler(Context context) {
        super(context);
        mOnFuelFetchServiceHandlerListener =(OnFuelFetchServiceHandlerListener) context;
    }

    public void getFuelReportRequest(Context context, int userID, long fromDate, long toDate) {

        String url = URLConstant.BASE_URL + URLConstant.FUEL_FETCH_PATH;
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
        FuelFetchDTO fuelFetchDTO = new Gson().fromJson(result,new TypeToken<FuelFetchDTO>() {}.getType());
        Log.i(TAG, "FuelFetchDTO:: " + fuelFetchDTO.fuelList.size());
        mOnFuelFetchServiceHandlerListener.onSuccessFuelFetch(fuelFetchDTO.fuelList);
    }

}
