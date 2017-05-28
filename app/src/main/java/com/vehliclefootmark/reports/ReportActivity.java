package com.vehliclefootmark.reports;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.vehliclefootmark.R;
import com.vehliclefootmark.constants.ErrorConstants;
import com.vehliclefootmark.reports.fuel.FuelFetchDTO;
import com.vehliclefootmark.reports.fuel.FuelFetchServiceHandler;
import com.vehliclefootmark.reports.fuel.OnFuelFetchServiceHandlerListener;
import com.vehliclefootmark.reports.repair.OnRepairFetchServiceHandlerListener;
import com.vehliclefootmark.reports.repair.Repair;
import com.vehliclefootmark.reports.repair.RepairFetchServiceHandler;
import com.vehliclefootmark.reports.service.OnServiceFetchServiceHandlerListener;
import com.vehliclefootmark.reports.service.ServiceDTO;
import com.vehliclefootmark.reports.service.ServiceFetchServiceHandler;
import com.vehliclefootmark.util.UIUtils;

import java.util.Calendar;
import java.util.List;

public class ReportActivity extends Activity implements View.OnClickListener, OnFuelFetchServiceHandlerListener ,
        AdapterView.OnItemSelectedListener, OnServiceFetchServiceHandlerListener, OnRepairFetchServiceHandlerListener {

    private Button mBtnGenerate;
    private int year;
    private int month;
    private int day;
    private ImageView mBackButton;
    private TextView mTxtHeader;
    private Spinner mSpinnerReportType;
    private EditText mETDateFrom;
    private EditText mETDateTo;
    private static final int FROM_DATE_DIALOG_ID = 111;
    private static final int TO_DATE_DIALOG_ID = 112;
    private long mFromDateInMills;
    private long mToDateInMills;
    private int mReportType = 0;
    private Spinner mSpinnerReportUsers;
    private int mUserID = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        initUI();

    }
    private void initUI() {
        setHeader();
        mBtnGenerate = (Button) findViewById(R.id.btn_generate);
        mBtnGenerate.setOnClickListener(this);
        mETDateFrom = (EditText) findViewById(R.id.et_date_report_from);
        mETDateFrom.setOnClickListener(this);
        mETDateTo = (EditText) findViewById(R.id.et_date_report_to);
        mETDateTo.setOnClickListener(this);
        mSpinnerReportType = (Spinner) findViewById(R.id.spinner_report_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.report_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerReportType.setAdapter(adapter);
        mSpinnerReportType.setOnItemSelectedListener(this);

        mSpinnerReportUsers = (Spinner) findViewById(R.id.spinner_report_users);

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
        mTxtHeader.setText(getString(R.string.lbl_reports));
    }

    public void createTable(List<String> data) {
        TableLayout tableLayout = (TableLayout) findViewById(R.id.table_service);
        for (int i = 0; i < data.size(); i++) {

            TableRow tableRow = new TableRow(ReportActivity.this);
            tableRow.setLayoutParams(new TableRow.LayoutParams( TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT ));

            TextView qty = new TextView(this);
            qty.setText(data.get(i)+"");
            tableRow.addView(qty);

            TextView qty1 = new TextView(this);
            qty1.setText(data.get(i)+"");
            tableRow.addView(qty1);

            TextView qty2 = new TextView(this);
            qty2.setText(data.get(i)+"");
            tableRow.addView(qty2);

            TextView qty3 = new TextView(this);
            qty3.setText(data.get(i)+"");
            tableRow.addView(qty3);

            TextView qty4 = new TextView(this);
            qty4.setText(data.get(i)+"");
            tableRow.addView(qty4);

            TextView qty5 = new TextView(this);
            qty5.setText(data.get(i)+"");
            tableRow.addView(qty5);

            TextView qty6 = new TextView(this);
            qty6.setText(data.get(i)+"");
            tableRow.addView(qty6);

            TextView qty7 = new TextView(this);
            qty7.setText(data.get(i)+"");
            tableRow.addView(qty7);

            tableLayout.addView(tableRow, i+1);
            Log.i("srikanth", i+"");
        }
    }

    @Override
    public void onClick(View view) {
        if (view == mETDateFrom) {
            showDialog(FROM_DATE_DIALOG_ID);
        } else if(view == mETDateTo) {
            showDialog(TO_DATE_DIALOG_ID);
        } else if (view == mBackButton){
            finish();
        } else if(view == mBtnGenerate){
            switch (mReportType){
                case 0:
                    ServiceFetchServiceHandler serviceFetchServiceHandler = new ServiceFetchServiceHandler(ReportActivity.this);
                    serviceFetchServiceHandler.getServiceReportRequest(ReportActivity.this, mUserID, mFromDateInMills, mToDateInMills);
                    break;
                case 1:
                    RepairFetchServiceHandler repairFetchServiceHandler = new RepairFetchServiceHandler(ReportActivity.this);
                    repairFetchServiceHandler.getRepairReportRequest(ReportActivity.this, mUserID, mFromDateInMills, mToDateInMills);
                    break;
                case 2:
                    FuelFetchServiceHandler fuelFetchServiceHandler = new FuelFetchServiceHandler(ReportActivity.this);
                    fuelFetchServiceHandler.getFuelReportRequest(ReportActivity.this, mUserID, mFromDateInMills, mToDateInMills);
                    break;
            }
        }
    }

    @Override
    public void onResponseError(int errorCode) {
        showErrorDialog(errorCode);
    }

    @Override
    public void showErrorDialog(int errorCode) {
        UIUtils.cancelProgressDialog();
        Toast.makeText(ReportActivity.this, ErrorConstants.ERROR_LIST.get(errorCode),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessRepairFetch(final List<Repair> repairList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                UIUtils.cancelProgressDialog();
                Toast.makeText(ReportActivity.this, getString(R.string.lbl_fuel_entry_saved),
                        Toast.LENGTH_SHORT).show();
                createRepairTable(repairList);
            }
        });
    }

    private void createRepairTable(List<Repair> repairList) {

    }

    @Override
    public void onSuccessServiceFetch(final List<ServiceDTO> serviceList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                UIUtils.cancelProgressDialog();
                Toast.makeText(ReportActivity.this, getString(R.string.lbl_fuel_entry_saved),
                        Toast.LENGTH_SHORT).show();
                createServiceTable(serviceList);
            }
        });
    }

    private void createServiceTable(List<ServiceDTO> serviceList) {

    }

    @Override
    public void onSuccessFuelFetch(final List<FuelFetchDTO> fuelList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                UIUtils.cancelProgressDialog();
                Toast.makeText(ReportActivity.this, getString(R.string.lbl_fuel_entry_saved),
                        Toast.LENGTH_SHORT).show();
                createFuelTable(fuelList);
            }
        });
    }

    private void createFuelTable(List<FuelFetchDTO> fuelList) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId() == mSpinnerReportType.getId()){
            mReportType = i;
        } else if (adapterView.getId() == mSpinnerReportUsers.getId()){
            mUserID = 2;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case FROM_DATE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, fromDatePickerListener,
                        year, month, day);
            case TO_DATE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, toDatePickerListener,
                        year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener fromDatePickerListener
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
            mFromDateInMills = calendar.getTimeInMillis();
            // set selected date into textview
            mETDateFrom.setText(new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year)
                    .append(" "));

        }
    };

    private DatePickerDialog.OnDateSetListener toDatePickerListener
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
            mToDateInMills = calendar.getTimeInMillis();
            // set selected date into textview
            mETDateTo.setText(new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year)
                    .append(" "));

        }
    };
}
