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

import com.vehliclefootmark.fuel.FuelEntryActivity;
import com.vehliclefootmark.login.LoginActivity;
import com.vehliclefootmark.repair.RepairEntryActivity;
import com.vehliclefootmark.serviceentry.ServiceEntryActivity;

public class HomeActivity extends Activity implements AdapterView.OnItemClickListener, OnClickListener{

    private ImageView mBackButton;
    private TextView mTxtHeader;
    private ImageView mLogoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initUI();

    }

    private void initUI() {
        setHeader();
        ListView listView_selector = (ListView) findViewById(R.id.listview_selector);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, getResources().getStringArray(R.array.selection));
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
                startActivity(new Intent(HomeActivity.this, ServiceEntryActivity.class));
                break;
            case 1:
                startActivity(new Intent(HomeActivity.this, RepairEntryActivity.class));
                break;
            case 2:
                startActivity(new Intent(HomeActivity.this, FuelEntryActivity.class));
                break;
        }
    }

    @Override
    public void onClick(View view) {
        if(view == mLogoutButton){
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }
}
