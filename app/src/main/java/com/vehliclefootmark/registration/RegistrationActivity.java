package com.vehliclefootmark.registration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vehliclefootmark.HomeActivity;
import com.vehliclefootmark.R;
import com.vehliclefootmark.constants.ErrorConstants;
import com.vehliclefootmark.login.LoginActivity;
import com.vehliclefootmark.login.LoginServiceHandler;
import com.vehliclefootmark.util.UIUtils;

public class RegistrationActivity extends Activity implements View.OnClickListener, OnRegistrationServiceHandlerListener {

    private ImageView mBackButton;
    private TextView mTxtHeader;
    private Button mBtnRegister;
    private EditText mETPassword;
    private EditText mETConfirmPassword;
    private EditText mETEmailID;
    private EditText mETFirstName;
    private EditText mETLastName;
    private EditText mETVehicleModel;
    private EditText mETVehicleNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initUI();
    }

    private void initUI() {
        setHeader();
        mBtnRegister = (Button) findViewById(R.id.btn_Register);
        mBtnRegister.setOnClickListener(this);
        mETPassword = (EditText) findViewById(R.id.et_password);
        mETConfirmPassword = (EditText) findViewById(R.id.et_confirm_password);
        mETEmailID = (EditText) findViewById(R.id.et_email);
        mETFirstName = (EditText) findViewById(R.id.et_firstname);
        mETLastName = (EditText) findViewById(R.id.et_lastname);
        mETVehicleModel = (EditText) findViewById(R.id.et_vehicle_model);
        mETVehicleNumber = (EditText) findViewById(R.id.et_vehicle_number);
    }

    private void setHeader() {
        mBackButton = (ImageView) findViewById(R.id.img_back);
        mBackButton.setOnClickListener(this);
        mTxtHeader = (TextView) findViewById(R.id.txt_header_title);
        mTxtHeader.setText(getString(R.string.lbl_register_form));
    }

    @Override
    public void onClick(View view) {
        if (view == mBackButton) {
            finish();
        } else if (view == mBtnRegister) {
            if (isAnyFieldEmpty()) {
                Toast.makeText(RegistrationActivity.this, R.string.lbl_all_fields_manditory, Toast.LENGTH_SHORT).show();
            } else if (!isPasswordSame(mETPassword.getText().toString(), mETConfirmPassword.getText().toString())) {
                Toast.makeText(RegistrationActivity.this, R.string.lbl_password_not_match, Toast.LENGTH_SHORT).show();
            } else {
                RegistrationServiceHandler registrationServiceHandler = new RegistrationServiceHandler(RegistrationActivity.this);

                registrationServiceHandler.doRegisterRequest(RegistrationActivity.this,
                        mETFirstName.getText().toString(), mETLastName.getText().toString(),
                        mETEmailID.getText().toString(), mETPassword.getText().toString(),
                        mETVehicleModel.getText().toString(), mETVehicleNumber.getText().toString());

                Toast.makeText(RegistrationActivity.this, R.string.lbl_trying_to_register, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isAnyFieldEmpty() {
        if (mETFirstName.getText().toString().isEmpty() || mETLastName.getText().toString().isEmpty() ||
                mETEmailID.getText().toString().isEmpty() || mETPassword.getText().toString().isEmpty() ||
                mETConfirmPassword.getText().toString().isEmpty() || mETVehicleModel.getText().toString().isEmpty() ||
                mETVehicleNumber.getText().toString().isEmpty())
            return true;
        return false;
    }

    boolean isPasswordSame(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    @Override
    public void onResponseError(int errorCode) {
        showErrorDialog(errorCode);
    }

    @Override
    public void showErrorDialog(int errorCode) {
        UIUtils.cancelProgressDialog();
        Toast.makeText(RegistrationActivity.this, ErrorConstants.ERROR_LIST.get(errorCode),
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRegistrationSuccess() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(RegistrationActivity.this, getString(R.string.lbl_user_registered_successfully),
                        Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                UIUtils.cancelProgressDialog();
                finish();
            }
        });
    }

}
