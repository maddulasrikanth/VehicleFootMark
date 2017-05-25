package com.vehliclefootmark.serviceentry;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.vehliclefootmark.R;
import com.vehliclefootmark.constants.ErrorConstants;
import com.vehliclefootmark.login.LoginActivity;
import com.vehliclefootmark.net.OnServiceRequestHandlerListener;
import com.vehliclefootmark.util.UIUtils;

import java.util.Calendar;

public class ServiceEntryActivity extends Activity implements OnClickListener, AdapterView.OnItemSelectedListener, OnServiceEntryHandlerListener {

    private final int DATE_NEXT_DUE_DIALOG_ID = 998;
    private final int DATE_COMPLETED_DIALOG_ID = 999;
    private ImageView mBackButton;
    private TextView mTxtHeader;
    private EditText mETDateCompleted;
    private int year;
    private int month;
    private int day;
    private EditText mETDateNextDue;
    private Button mBtnSave;
    private EditText mETPartsCost;
    private EditText mETTax;
    private EditText mETServicedBy;
    private EditText mETComments;
    private String mTypeOfService;
    private EditText mETLabourCost;
    private long completedDateInMills;
    private long nextDueInMills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_entry);
        initUI();
    }

    private void initUI() {
        setHeader();
        mBtnSave = (Button) findViewById(R.id.btn_service_save);
        mBtnSave.setOnClickListener(this);
        mETLabourCost = (EditText) findViewById(R.id.et_service_labour_cost);
        mETPartsCost = (EditText) findViewById(R.id.et_parts_cost);
        mETTax = (EditText) findViewById(R.id.et_tax);
        mETServicedBy = (EditText) findViewById(R.id.et_serviced_by);
        mETComments = (EditText) findViewById(R.id.et_comments);

        Spinner spinner = (Spinner) findViewById(R.id.service_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.services_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        mETDateCompleted = (EditText) findViewById(R.id.et_date_completed);
        mETDateCompleted.setOnClickListener(this);

        mETDateNextDue = (EditText) findViewById(R.id.et_date_next_due);
        mETDateNextDue.setOnClickListener(this);

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
    }

    private void setHeader() {
        mBackButton = (ImageView) findViewById(R.id.img_back);
        mBackButton.setOnClickListener(this);
        mTxtHeader = (TextView) findViewById(R.id.txt_header_title);
        mTxtHeader.setText(getString(R.string.lbl_service_entry_form));
    }

    @Override
    public void onClick(View view) {
        if (view == mETDateCompleted) {
            showDialog(DATE_COMPLETED_DIALOG_ID);
        } else if (view == mETDateNextDue) {
            showDialog(DATE_NEXT_DUE_DIALOG_ID);
        } else if (view == mBackButton) {
            finish();
        } else if (view == mBtnSave) {
            saveServiceEntry();
        }

    }

    private void saveServiceEntry() {
        ServiceEntryHandler loginService = new ServiceEntryHandler(ServiceEntryActivity.this);
        loginService.doServiceEntryRequest(ServiceEntryActivity.this, mTypeOfService,
                mETLabourCost.getText().toString(), mETPartsCost.getText().toString(),
                mETTax.getText().toString(), completedDateInMills, nextDueInMills,
                mETServicedBy.getText().toString(), mETComments.getText().toString());
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
        switch (pos) {
            case 0:
                mTypeOfService = getString(R.string.lbl_interim_service);
                break;
            case 1:
                mTypeOfService = getString(R.string.lbl_full_service);
                break;
            case 2:
                mTypeOfService = getString(R.string.lbl_mojor_service);
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
                return new DatePickerDialog(this, dateCompletedPickerListener,
                        year, month, day);
            case DATE_NEXT_DUE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, dateNextDuePickerListener,
                        year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener dateCompletedPickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(selectedYear, selectedMonth, selectedDay,
                    0, 0, 0);
            completedDateInMills = calendar.getTimeInMillis();
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            // set selected date into textview
            mETDateCompleted.setText(new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year)
                    .append(" "));

        }
    };

    private DatePickerDialog.OnDateSetListener dateNextDuePickerListener
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
            nextDueInMills = calendar.getTimeInMillis();
            // set selected date into textview
            mETDateNextDue.setText(new StringBuilder().append(month + 1)
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
        Toast.makeText(ServiceEntryActivity.this, ErrorConstants.ERROR_LIST.get(errorCode),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessEntry() {
        UIUtils.cancelProgressDialog();
        Toast.makeText(ServiceEntryActivity.this, getString(R.string.lbl_service_entry_saved),
                Toast.LENGTH_SHORT).show();
    }
}
