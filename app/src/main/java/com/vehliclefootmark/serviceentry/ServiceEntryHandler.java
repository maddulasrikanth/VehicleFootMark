package com.vehliclefootmark.serviceentry;


import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.vehliclefootmark.R;
import com.vehliclefootmark.constants.URLConstant;
import com.vehliclefootmark.customexception.VehicleFootMarkException;
import com.vehliclefootmark.login.LoginResponseDTO;
import com.vehliclefootmark.net.GenericServiceHandler;
import com.vehliclefootmark.net.NetworkManager;
import com.vehliclefootmark.net.RequestPacket;
import com.vehliclefootmark.util.UIUtils;

import java.util.HashMap;

public class ServiceEntryHandler extends GenericServiceHandler {
    private final String TAG = "ServiceEntryHandler";
    OnServiceEntryHandlerListener mOnServiceEntryHandlerListener;

    public ServiceEntryHandler(Context context) {
        super(context);
        mOnServiceEntryHandlerListener =(OnServiceEntryHandlerListener) context;
    }

    public void doServiceEntryRequest(Context context, int userID, String type, String labourCost, String partsCost,
                                      String tax, long completedDate, long nextDue, String servicedBy, String comments) {

        UIUtils.showProgressDialog(context, context.getString(R.string.lbl_progress_bar_message));
        String url = URLConstant.BASE_URL + URLConstant.SERVICE_PATH;
        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("loggedUser", userID+"");
        parameters.put("type", type);
        parameters.put("labourcost", labourCost);
        parameters.put("partscost", partsCost);
        parameters.put("tax", tax);
        parameters.put("completedDate", completedDate+"");
        parameters.put("nextDue", nextDue+"");
        parameters.put("servicedBy", servicedBy);
        parameters.put("comments", comments);

        RequestPacket requestPacket = new RequestPacket(url, NetworkManager.METHOD[1], parameters, "");
        execute(requestPacket);
    }

    @Override
    protected void processResult(String result) throws JsonSyntaxException, VehicleFootMarkException {
        Log.i(TAG, "LOGIN RESULT_1:: " + result);
        LoginResponseDTO loginResponse = new Gson().fromJson(result,new TypeToken<LoginResponseDTO>() {}.getType());
        Log.i(TAG, "LOGIN getEmail:: " + loginResponse.getEmail());
        mOnServiceEntryHandlerListener.onSuccessEntry();
    }
    private void showDialog(int errorCode) {
        mOnServiceEntryHandlerListener.showErrorDialog(errorCode);
    }

}
