package com.vehliclefootmark.registration;

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
import java.util.List;

public class SeedInfoServiceHandler  extends GenericServiceHandler {
    private final String TAG = "SeedInfoServiceHandler";
    OnSeedInfoServiceHandlerListener mOnSeedInfoServiceHandlerListener;

    public SeedInfoServiceHandler(Context context) {
        super(context);
        mOnSeedInfoServiceHandlerListener = (OnSeedInfoServiceHandlerListener) context;
    }

    public void getModelListRequest(Context context, int userID) {

        String url = URLConstant.BASE_URL + URLConstant.SEED_PROVIDER_PATH;
        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("loggedUser", userID+"");

        RequestPacket requestPacket = new RequestPacket(url, NetworkManager.METHOD[1], parameters, "");
        UIUtils.showProgressDialog(context, context.getString(R.string.lbl_progress_bar_message));
        execute(requestPacket);
    }

    @Override
    protected void processResult(String result) throws JsonSyntaxException, VehicleFootMarkException {
        Log.i(TAG, "LOGIN RESULT_1:: " + result);
        SeedInfoList seedInfoList = new Gson().fromJson(result, new TypeToken<SeedInfoList>() {}.getType());
        Log.i(TAG, "LOGIN getEmail:: " + seedInfoList.seedInfoList.size());
        mOnSeedInfoServiceHandlerListener.onSuccessResponse(seedInfoList.seedInfoList);
    }

    private void showDialog(int errorCode) {
        mOnSeedInfoServiceHandlerListener.showErrorDialog(errorCode);
    }
}
