package com.vehliclefootmark;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.vehliclefootmark.constants.StringConstants;
import com.vehliclefootmark.fuel.FuelEntryActivity;
import com.vehliclefootmark.login.LoginActivity;
import com.vehliclefootmark.registration.RegistrationActivity;
import com.vehliclefootmark.repair.RepairEntryActivity;
import com.vehliclefootmark.reports.ReportActivity;
import com.vehliclefootmark.serviceentry.ServiceEntryActivity;

public class HomeActivity extends Activity implements AdapterView.OnItemClickListener, OnClickListener{

    private ImageView mBackButton;
    private TextView mTxtHeader;
    private ImageView mLogoutButton;
    private int mUserID;
    private boolean mIsAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mUserID = getIntent().getIntExtra(StringConstants.EXTRA_USER_ID, 0);
        mIsAdmin = getIntent().getBooleanExtra(StringConstants.EXTRA_IS_ADMIN, false);
        initUI();
    }

    private void initUI() {
        setHeader();
        ListView listView_selector = (ListView) findViewById(R.id.listview_selector);
        ArrayAdapter<String> adapter;
        if(mIsAdmin) {
            adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, getResources().getStringArray(R.array.selection_admin));
        } else {
            adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, getResources().getStringArray(R.array.selection));
        }
        listView_selector.setAdapter(adapter);
        listView_selector.setOnItemClickListener(this);
    }

    private void setHeader() {
        mBackButton = (ImageView) findViewById(R.id.img_back);
        mBackButton.setVisibility(View.INVISIBLE);
        mTxtHeader = (TextView) findViewById(R.id.txt_header_title);
        mTxtHeader.setText(getString(R.string.lbl_home));
        mLogoutButton = (ImageView) findViewById(R.id.img_logout);
        mLogoutButton.setVisibility(View.VISIBLE);
        mLogoutButton.setOnClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i){
            case 0:
                Intent serviceIntent = new Intent(HomeActivity.this, ServiceEntryActivity.class);
                serviceIntent.putExtra(StringConstants.EXTRA_USER_ID, mUserID);
                startActivity(serviceIntent);
                break;
            case 1:
                Intent repairIntent = new Intent(HomeActivity.this, RepairEntryActivity.class);
                repairIntent.putExtra(StringConstants.EXTRA_USER_ID, mUserID);
                startActivity(repairIntent);
                break;
            case 2:
                Intent fuelIntent = new Intent(HomeActivity.this, FuelEntryActivity.class);
                fuelIntent.putExtra(StringConstants.EXTRA_USER_ID, mUserID);
                startActivity(fuelIntent);
                break;
            case 3:
                Intent reportIntent = new Intent(HomeActivity.this, ReportActivity.class);
                reportIntent.putExtra(StringConstants.EXTRA_USER_ID, mUserID);
                startActivity(reportIntent);
                break;
            case 4:
                Intent registrationIntent = new Intent(HomeActivity.this, RegistrationActivity.class);
                registrationIntent.putExtra(StringConstants.EXTRA_USER_ID, mUserID);
                startActivity(registrationIntent);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        if(view == mLogoutButton){
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }
}
