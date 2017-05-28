package com.vehliclefootmark.registration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.vehliclefootmark.HomeActivity;
import com.vehliclefootmark.R;
import com.vehliclefootmark.constants.ErrorConstants;
import com.vehliclefootmark.constants.StringConstants;
import com.vehliclefootmark.login.LoginActivity;
import com.vehliclefootmark.login.LoginServiceHandler;
import com.vehliclefootmark.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

public class RegistrationActivity extends Activity implements View.OnClickListener, OnRegistrationServiceHandlerListener,
        AdapterView.OnItemSelectedListener, OnSeedInfoServiceHandlerListener {

    private ImageView mBackButton;
    private TextView mTxtHeader;
    private Button mBtnRegister;
    private EditText mETPassword;
    private EditText mETConfirmPassword;
    private EditText mETEmailID;
    private EditText mETFirstName;
    private EditText mETLastName;
    private EditText mETVehicleNumber;
    private int mUserID;
    private Spinner mSpinnerVehicleModel;
    private List<SeedInfo> models;
    private String mModelID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mUserID = getIntent().getIntExtra(StringConstants.EXTRA_USER_ID, 0);
        initUI();
    }

    private void initUI() {
        setHeader();
        getModels();
        mBtnRegister = (Button) findViewById(R.id.btn_Register);
        mBtnRegister.setOnClickListener(this);
        mETPassword = (EditText) findViewById(R.id.et_password);
        mETConfirmPassword = (EditText) findViewById(R.id.et_confirm_password);
        mETEmailID = (EditText) findViewById(R.id.et_email);
        mETFirstName = (EditText) findViewById(R.id.et_firstname);
        mETLastName = (EditText) findViewById(R.id.et_lastname);
        mETVehicleNumber = (EditText) findViewById(R.id.et_vehicle_number);
        mSpinnerVehicleModel = (Spinner) findViewById(R.id.spinner_vehicle_model);
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
                        mModelID, mETVehicleNumber.getText().toString());
            }
        }
    }

    private boolean isAnyFieldEmpty() {
        if (mETFirstName.getText().toString().isEmpty() || mETLastName.getText().toString().isEmpty() ||
                mETEmailID.getText().toString().isEmpty() || mETPassword.getText().toString().isEmpty() ||
                mETConfirmPassword.getText().toString().isEmpty() ||
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
    public void onSuccessResponse(final List<SeedInfo> seedInfoList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                UIUtils.cancelProgressDialog();
                models = seedInfoList;
                String[] models = new String[seedInfoList.size()];
                for (int i = 0; i < seedInfoList.size(); i++) {
                    SeedInfo seedInfo = seedInfoList.get(i);
                    models[i] = seedInfo.getSeedInfoId() + "-" + seedInfo.getName();
                }
                ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(RegistrationActivity.this, android.R.layout.simple_spinner_item, models);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinnerVehicleModel.setAdapter(adapter);
                mSpinnerVehicleModel.setOnItemSelectedListener(RegistrationActivity.this);
            }
        });
    }

    @Override
    public void onRegistrationSuccess() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(RegistrationActivity.this, getString(R.string.lbl_user_registered_successfully),
                        Toast.LENGTH_SHORT).show();
                UIUtils.cancelProgressDialog();
                finish();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
        String user = (String) adapterView.getAdapter().getItem(pos);
        mModelID = user.split("-")[0];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void getModels() {
        SeedInfoServiceHandler seedInfoServiceHandler = new SeedInfoServiceHandler(this);
        seedInfoServiceHandler.getModelListRequest(RegistrationActivity.this, mUserID);
    }
}
