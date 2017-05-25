package com.vehliclefootmark.repair;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vehliclefootmark.R;
import com.vehliclefootmark.constants.ErrorConstants;
import com.vehliclefootmark.constants.StringConstants;
import com.vehliclefootmark.util.UIUtils;

import java.util.Calendar;

public class RepairEntryActivity extends Activity  implements View.OnClickListener, AdapterView.OnItemSelectedListener, OnRepairEntryServiceHandlerListener{

    private static final int DATE_PROBLEM_DIALOG_ID = 111;
    private static final int DATE_REPAIR_DIALOG_ID = 112;
    private ImageView mBackButton;
    private TextView mTxtHeader;
    private Button mBtnSave;
    private EditText mETLaborCost;
    private EditText mETMaterialCost;
    private EditText mETRepairSummary;
    private EditText mETSummary;
    private EditText mETDateOfProblem;
    private EditText mETDateOfRepair;
    private int year;
    private int month;
    private int day;
    private String userID;
    private long mProblemDateInMills;
    private long mRepairDateInMills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_entry);
        userID = getIntent().getStringExtra(StringConstants.EXTRA_USER_ID);
        initUI();
    }

    private void initUI() {
        setHeader();
        mBtnSave = (Button) findViewById(R.id.btn_repair_save);
        mBtnSave.setOnClickListener(this);
        mETLaborCost = (EditText) findViewById(R.id.et_labour_cost);
        mETMaterialCost = (EditText) findViewById(R.id.et_material_cost);
        mETRepairSummary = (EditText) findViewById(R.id.et_service_summary);
        mETSummary = (EditText) findViewById(R.id.et_summary);

        mETDateOfProblem = (EditText) findViewById(R.id.et_date_of_problem);
        mETDateOfProblem.setOnClickListener(this);

        mETDateOfRepair = (EditText) findViewById(R.id.et_date_of_repair);
        mETDateOfRepair.setOnClickListener(this);

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
        mTxtHeader.setText(getString(R.string.lbl_service_entry_form));
    }

    @Override
    public void onClick(View view) {
        if (view == mETDateOfProblem) {
            showDialog(DATE_PROBLEM_DIALOG_ID);
        } else if(view == mETDateOfRepair) {
            showDialog(DATE_REPAIR_DIALOG_ID);
        } else if (view == mBackButton){
            finish();
        } else if(view == mBtnSave){
            RepairEntryServiceHandler fuelEntryServiceHandler = new RepairEntryServiceHandler(RepairEntryActivity.this);
            fuelEntryServiceHandler.doRepairEntryRequest(RepairEntryActivity.this, userID, mETLaborCost.getText().toString(),
            mETMaterialCost.getText().toString(),
            mETRepairSummary.getText().toString(),
            mETSummary.getText().toString(),
            mProblemDateInMills,
            mRepairDateInMills);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_PROBLEM_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, dateOfProblemPickerListener,
                        year, month, day);
            case DATE_REPAIR_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, dateOfRepairPickerListener,
                        year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener dateOfProblemPickerListener
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
            mProblemDateInMills = calendar.getTimeInMillis();
            // set selected date into textview
            mETDateOfProblem.setText(new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year)
                    .append(" "));

        }
    };

    private DatePickerDialog.OnDateSetListener dateOfRepairPickerListener
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
            mRepairDateInMills = calendar.getTimeInMillis();
            // set selected date into textview
            mETDateOfRepair.setText(new StringBuilder().append(month + 1)
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
        Toast.makeText(RepairEntryActivity.this, ErrorConstants.ERROR_LIST.get(errorCode),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessRepairEntry() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(RepairEntryActivity.this, getString(R.string.lbl_fuel_entry_saved),
                        Toast.LENGTH_SHORT).show();
                clearAllFields();
            }
        });
    }

    private void clearAllFields() {
        mETLaborCost.setText("");
        mETMaterialCost.setText("");
        mETRepairSummary.setText("");
        mETSummary.setText("");
        mETDateOfProblem.setText("");
        mETDateOfRepair.setText("");
        mRepairDateInMills = 0;
        mProblemDateInMills = 0;
        setCalendarDate();
    }
}
