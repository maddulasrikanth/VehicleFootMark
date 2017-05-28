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


public class RegistrationServiceHandler  extends GenericServiceHandler {
    private final String TAG = "RegServiceHandler";
    OnRegistrationServiceHandlerListener mOnRegistrationServiceHandlerListener;

    public RegistrationServiceHandler(Context context) {
        super(context);
        mOnRegistrationServiceHandlerListener = (OnRegistrationServiceHandlerListener) context;
    }

    public void doRegisterRequest(Context context, String firstName, String lastname, String emailid, String password,
                                  String vehicleModel, String vehicleNumber) {

        String url = URLConstant.BASE_URL + URLConstant.REGISTRATION_PATH;
        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("userFirstName", firstName);
        parameters.put("userLastName", lastname);
        parameters.put("userPassword", password);
        parameters.put("userEmail", emailid);
        parameters.put("userVehicleModel", vehicleModel);
        parameters.put("userVehicleNumber", vehicleNumber);

        RequestPacket requestPacket = new RequestPacket(url, NetworkManager.METHOD[1], parameters, "");
        UIUtils.showProgressDialog(context, context.getString(R.string.lbl_progress_bar_message));
        execute(requestPacket);
    }

    @Override
    protected void processResult(String result) throws JsonSyntaxException, VehicleFootMarkException {
        mOnRegistrationServiceHandlerListener.onRegistrationSuccess();
    }

    private void showDialog(int errorCode) {
        mOnRegistrationServiceHandlerListener.showErrorDialog(errorCode);
    }
}
