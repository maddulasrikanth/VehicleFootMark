package com.vehliclefootmark.repair;


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

public class RepairEntryServiceHandler  extends GenericServiceHandler {

    private final String TAG = "RepairEntryHandler";
    OnRepairEntryServiceHandlerListener mOnRepairEntryServiceHandlerListener;

    public RepairEntryServiceHandler(Context context) {
        super(context);
        mOnRepairEntryServiceHandlerListener =(OnRepairEntryServiceHandlerListener) context;
    }

    public void doRepairEntryRequest(Context context, int userID, String laborCost, String materialCost,
                                   String repairSummary, String summary, long problemDate, long repairDate) {

        String url = URLConstant.BASE_URL + URLConstant.REPAIR_ENTRY_PATH;
        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("loggedUser", userID+"");
        parameters.put("labourCost", laborCost);
        parameters.put("repairFoundDate", problemDate+"");
        parameters.put("dateOfRepair", repairDate+"");
        parameters.put("problemSummary", summary);
        parameters.put("repairSummary", repairSummary);
        parameters.put("materialCost", materialCost);

        RequestPacket requestPacket = new RequestPacket(url, NetworkManager.METHOD[1], parameters, "");
        UIUtils.showProgressDialog(context, context.getString(R.string.lbl_progress_bar_message));
        execute(requestPacket);
    }

    @Override
    protected void processResult(String result) throws JsonSyntaxException, VehicleFootMarkException {
        Log.i(TAG, "RESULT_1:: " + result);
        /*LoginResponseDTO loginResponse = new Gson().fromJson(result,new TypeToken<LoginResponseDTO>() {}.getType());
        Log.i(TAG, "LOGIN getEmail:: " + loginResponse.getEmail());*/
        mOnRepairEntryServiceHandlerListener.onSuccessRepairEntry();
    }

}
