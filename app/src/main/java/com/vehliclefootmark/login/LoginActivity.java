package com.vehliclefootmark.login;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vehliclefootmark.ErrorConstants;
import com.vehliclefootmark.R;


public class LoginActivity extends Activity implements OnClickListener, OnLoginServiceHandlerListener {
    private String TAG = "LoginActivity";

    private EditText et_UserName, et_Password;
    private Button btn_Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }


    private void init() {
        et_UserName = (EditText) findViewById(R.id.et_UserName);
        et_Password = (EditText) findViewById(R.id.et_UserPwd);
        btn_Login = (Button) findViewById(R.id.btn_Login);

		/*Listeners*/
        btn_Login.setOnClickListener(this);
    }


    private void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et_UserName.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(et_Password.getWindowToken(), 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Login:
                hideSoftKeyboard();
                LoginServiceHandler loginService = new LoginServiceHandler(LoginActivity.this);
                //loginService.doLoginRequest(strDeviceId, strUserName, strPassword, strDeviceTypeCode, strDeviceToken, strAdvertisingId);
                break;
        }

    }

    @Override
    public void onResponseError(int errorCode) {
        showErrorDialog(errorCode);
    }

    @Override
    public void showErrorDialog(int errorCode) {
        Toast.makeText(LoginActivity.this, ErrorConstants.hmErrorList.get(errorCode),
                Toast.LENGTH_SHORT).show();

    }


}
