package com.vehliclefootmark.fuel;


import android.content.Context;
import android.util.Log;

import com.google.gson.JsonSyntaxException;
import com.vehliclefootmark.R;
import com.vehliclefootmark.constants.URLConstant;
import com.vehliclefootmark.customexception.VehicleFootMarkException;
import com.vehliclefootmark.net.GenericServiceHandler;
import com.vehliclefootmark.net.NetworkManager;
import com.vehliclefootmark.net.RequestPacket;
import com.vehliclefootmark.util.UIUtils;

import java.util.HashMap;

public class FuelEntryServiceHandler extends GenericServiceHandler {
    private final String TAG = "FuelEntryServiceHandler";
    OnFuelEntryServiceHandlerListener mOnFuelEntryServiceHandlerListener;

    public FuelEntryServiceHandler(Context context) {
        super(context);
        mOnFuelEntryServiceHandlerListener =(OnFuelEntryServiceHandlerListener) context;
    }

    public void doFuelEntryRequest(Context context, int userID, String fuelPlace, String fuelAmount,
                                   String fuelTotal, long filledDateInMills, String fuelType) {

        String url = URLConstant.BASE_URL + URLConstant.FUEL_ENTRY_PATH;
        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("loggedUser", userID+"");
        parameters.put("date", filledDateInMills+"");
        parameters.put("fuelType", fuelType);
        parameters.put("place", fuelPlace);
        parameters.put("totalFuel", fuelTotal);
        parameters.put("amount", fuelAmount+"");

        RequestPacket requestPacket = new RequestPacket(url, NetworkManager.METHOD[1], parameters, "");
        UIUtils.showProgressDialog(context, context.getString(R.string.lbl_progress_bar_message));
        execute(requestPacket);
    }

    @Override
    protected void processResult(String result) throws JsonSyntaxException, VehicleFootMarkException {
        Log.i(TAG, "RESULT_1:: " + result);
        /*LoginResponseDTO loginResponse = new Gson().fromJson(result,new TypeToken<LoginResponseDTO>() {}.getType());
        Log.i(TAG, "LOGIN getEmail:: " + loginResponse.getEmail());*/
        mOnFuelEntryServiceHandlerListener.onSuccessFuelEntry();
    }

}
