package com.vehliclefootmark.fuel;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.vehliclefootmark.login.LoginResponseDTO;
import com.vehliclefootmark.login.LoginServiceHandler;
import com.vehliclefootmark.util.UIUtils;

import java.util.Calendar;

public class FuelEntryActivity extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener, OnFuelEntryServiceHandlerListener {

    private final int DATE_COMPLETED_DIALOG_ID = 999;
    private Button mBtnSave;
    private EditText mETDateFuelEntered;
    private int year;
    private EditText mETFuelPlace;
    private EditText mETFuelAmount;
    private EditText mETFuelTotal;
    private int month;
    private int day;
    private ImageView mBackButton;
    private TextView mTxtHeader;
    private int userID;
    private String mFuelType;
    private long mFuelEnteredDateInMills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_entry);
        initUI();
        userID = getIntent().getIntExtra(StringConstants.EXTRA_USER_ID, 0);
    }

    private void initUI() {
        setHeader();
        mBtnSave = (Button) findViewById(R.id.btn_fuel_save);
        mBtnSave.setOnClickListener(this);
        mETFuelPlace = (EditText) findViewById(R.id.et_fuel_place);
        mETFuelAmount = (EditText) findViewById(R.id.et_fuel_amount);
        mETFuelTotal = (EditText) findViewById(R.id.et_fuel_total);

        mETDateFuelEntered = (EditText) findViewById(R.id.et_date_fuel_entered);
        mETDateFuelEntered.setOnClickListener(this);

        Spinner spinner = (Spinner) findViewById(R.id.spinner_fuel_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.fuel_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        setCalendarDate();
    }

    private void setCalendarDate() {
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
    }

    private void setHeader() {
        mBackButton = (ImageView) findViewById(R.id.img_back);
        mBackButton.setOnClickListener(this);
        mTxtHeader = (TextView) findViewById(R.id.txt_header_title);
        mTxtHeader.setText(getString(R.string.lbl_fuel_entry_form));
    }

    @Override
    public void onClick(View view) {
        if (view == mETDateFuelEntered) {
            showDialog(DATE_COMPLETED_DIALOG_ID);
        } else if (view == mBackButton) {
            finish();
        } else if (view == mBtnSave) {
            FuelEntryServiceHandler fuelEntryServiceHandler = new FuelEntryServiceHandler(FuelEntryActivity.this);
            fuelEntryServiceHandler.doFuelEntryRequest(FuelEntryActivity.this, userID, mETFuelPlace.getText().toString(),
                    mETFuelAmount.getText().toString(), mETFuelTotal.getText().toString(),
                    mFuelEnteredDateInMills, mFuelType);
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
        switch (pos) {
            case 0:
                mFuelType = getString(R.string.lbl_ethanol);
                break;
            case 1:
                mFuelType = getString(R.string.lbl_methanol);
                break;
            case 2:
                mFuelType = getString(R.string.lbl_gasoline);
                break;
            case 3:
                mFuelType = getString(R.string.lbl_diesel);
                break;
            case 4:
                mFuelType = getString(R.string.lbl_biodiesel);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_COMPLETED_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, dateFuelEnteredPickerListener,
                        year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener dateFuelEnteredPickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;
            Calendar calendar = Calendar.getInstance();
            calendar.set(selectedYear, selectedMonth, selectedDay,
                    0, 0, 0);
            mFuelEnteredDateInMills = calendar.getTimeInMillis();
            // set selected date into textview
            mETDateFuelEntered.setText(new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year)
                    .append(" "));

        }
    };

    @Override
    public void onResponseError(int errorCode) {
        showErrorDialog(errorCode);
    }

    @Override
    public void showErrorDialog(int errorCode) {
        UIUtils.cancelProgressDialog();
        Toast.makeText(FuelEntryActivity.this, ErrorConstants.ERROR_LIST.get(errorCode),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessFuelEntry() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                UIUtils.cancelProgressDialog();
                Toast.makeText(FuelEntryActivity.this, getString(R.string.lbl_fuel_entry_saved),
                        Toast.LENGTH_SHORT).show();
                //clearAllFields();
            }
        });

    }

    private void clearAllFields() {
        mETFuelPlace.setText("");
        mETFuelAmount.setText("");
        mETFuelTotal.setText("");
        mETDateFuelEntered.setText("");
        mFuelEnteredDateInMills = 0;
        setCalendarDate();
    }
}
