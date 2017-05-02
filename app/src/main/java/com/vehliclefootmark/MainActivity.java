package com.vehliclefootmark;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.vehliclefootmark.login.LoginActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTxtViewLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTxtViewLogin = (TextView) findViewById(R.id.txt_login);
        TextView txtViewTitle = (TextView) findViewById(R.id.txt_header_title);
        txtViewTitle.setText(getString(R.string.app_name));
        ImageView imgback = (ImageView) findViewById(R.id.img_back);
        imgback.setVisibility(View.INVISIBLE);
        mTxtViewLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == mTxtViewLogin) {
            redirectToLoginScreen();
        }
    }

    private void redirectToLoginScreen() {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }
}
