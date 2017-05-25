package com.vehliclefootmark.login;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.vehliclefootmark.constants.ErrorConstants;
import com.vehliclefootmark.HomeActivity;
import com.vehliclefootmark.R;
import com.vehliclefootmark.util.UIUtils;


public class LoginActivity extends Activity implements OnClickListener, OnLoginServiceHandlerListener {
    private String TAG = "LoginActivity";

    private EditText mETUsername, mETPassword;
    private Button btn_Login;
    private ImageView mBackButton;
    private CheckBox mCheckIsAdmin;
    private boolean mIsAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }


    private void init() {
        mETUsername = (EditText) findViewById(R.id.et_UserName);
        mETPassword = (EditText) findViewById(R.id.et_UserPwd);
        btn_Login = (Button) findViewById(R.id.btn_Login);
        mCheckIsAdmin = (CheckBox) findViewById(R.id.check_is_admin);
        mCheckIsAdmin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mIsAdmin = b;
            }
        });
        mBackButton = (ImageView) findViewById(R.id.img_back);
        mBackButton.setOnClickListener(this);

        btn_Login.setOnClickListener(this);
    }


    private void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mETUsername.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(mETPassword.getWindowToken(), 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Login:
                hideSoftKeyboard();
                LoginServiceHandler loginService = new LoginServiceHandler(LoginActivity.this);
                loginService.doLoginRequest(LoginActivity.this, mETUsername.getText().toString(),
                        mETPassword.getText().toString(), mIsAdmin);
                break;
            case R.id.img_back:
                finish();
                break;
        }

    }

    @Override
    public void onResponseError(int errorCode) {
        showErrorDialog(errorCode);
    }

    @Override
    public void showErrorDialog(int errorCode) {
        UIUtils.cancelProgressDialog();
        Toast.makeText(LoginActivity.this, ErrorConstants.ERROR_LIST.get(errorCode),
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onSuccessLogin() {
        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        UIUtils.cancelProgressDialog();
        finish();
    }


}
