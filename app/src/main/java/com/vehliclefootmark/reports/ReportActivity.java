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
import com.vehliclefootmark.login.LoginResponseDTO;
import com.vehliclefootmark.registration.RegistrationActivity;
import com.vehliclefootmark.registration.SeedInfo;
import com.vehliclefootmark.reports.fuel.Fuel;
import com.vehliclefootmark.reports.fuel.FuelFetchDTO;
import com.vehliclefootmark.reports.fuel.FuelFetchServiceHandler;
import com.vehliclefootmark.reports.fuel.OnFuelFetchServiceHandlerListener;
import com.vehliclefootmark.reports.repair.OnRepairFetchServiceHandlerListener;
import com.vehliclefootmark.reports.repair.Repair;
import com.vehliclefootmark.reports.repair.RepairFetchServiceHandler;
import com.vehliclefootmark.reports.service.OnServiceFetchServiceHandlerListener;
import com.vehliclefootmark.reports.service.ServiceDTO;
import com.vehliclefootmark.reports.service.ServiceFetchServiceHandler;
import com.vehliclefootmark.reports.users.OnUserFetchServiceHandlerListener;
import com.vehliclefootmark.reports.users.UserFetchServiceHandler;
import com.vehliclefootmark.util.UIUtils;

import java.util.Calendar;
import java.util.List;

public class ReportActivity extends Activity implements View.OnClickListener, OnFuelFetchServiceHandlerListener ,
        AdapterView.OnItemSelectedListener, OnServiceFetchServiceHandlerListener, OnRepairFetchServiceHandlerListener, OnUserFetchServiceHandlerListener {

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
    private Object users;
    private TableLayout mTableService;
    private TableLayout mTableFuel;
    private TableLayout mTableRepair;

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
        mTableService = (TableLayout) findViewById(R.id.table_service);
        mTableRepair = (TableLayout) findViewById(R.id.table_repair);
        mTableFuel = (TableLayout) findViewById(R.id.table_fuel);

        mSpinnerReportType = (Spinner) findViewById(R.id.spinner_report_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.report_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerReportType.setAdapter(adapter);
        mSpinnerReportType.setOnItemSelectedListener(this);

        mSpinnerReportUsers = (Spinner) findViewById(R.id.spinner_report_users);

        setCalendarDate();
        getUsers();
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
    public void onSuccessUsersFetch(final List<LoginResponseDTO> userList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                UIUtils.cancelProgressDialog();
                String[] users = new String[userList.size()];
                for (int i=0;i<userList.size();i++) {
                    LoginResponseDTO user = userList.get(i);
                    users[i] = user.getUserNumber()+"-"+user.getFirstName();
                }
                ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(ReportActivity.this, android.R.layout.simple_spinner_item, users );
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinnerReportUsers.setAdapter(adapter);
                mSpinnerReportUsers.setOnItemSelectedListener(ReportActivity.this);
            }
        });
    }

    @Override
    public void onSuccessRepairFetch(final List<Repair> repairList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                UIUtils.cancelProgressDialog();
                createRepairTable(repairList);
            }
        });
    }

    private void createRepairTable(List<Repair> repairList) {
        mTableRepair.setVisibility(View.VISIBLE);
        mTableService.setVisibility(View.GONE);
        mTableFuel.setVisibility(View.GONE);

        for (int i = 0; i < repairList.size(); i++) {

            TableRow tableRow = new TableRow(ReportActivity.this);
            tableRow.setLayoutParams(new TableRow.LayoutParams( TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT ));
            Repair repair = repairList.get(i);

            TextView qty0 = new TextView(this);
            qty0.setText((i+1)+"");
            qty0.setBackground(getDrawable(R.drawable.cell_background));
            tableRow.addView(qty0);

            TextView qty = new TextView(this);
            qty.setText(UIUtils.convertMillsToDate(repair.getRepairFoundDate())+"");
            qty.setBackground(getDrawable(R.drawable.cell_background));
            tableRow.addView(qty);

            TextView qty1 = new TextView(this);
            qty1.setText(UIUtils.convertMillsToDate(repair.getDateOfRepair())+"");
            qty1.setBackground(getDrawable(R.drawable.cell_background));
            tableRow.addView(qty1);

            TextView qty2 = new TextView(this);
            qty2.setText(repair.getProblemSummary()+"");
            qty2.setBackground(getDrawable(R.drawable.cell_background));
            tableRow.addView(qty2);

            TextView qty3 = new TextView(this);
            qty3.setText(repair.getRepairSummary()+"");
            qty3.setBackground(getDrawable(R.drawable.cell_background));
            tableRow.addView(qty3);

            TextView qty4 = new TextView(this);
            qty4.setText(repair.getMaterialCost()+"");
            qty4.setBackground(getDrawable(R.drawable.cell_background));
            tableRow.addView(qty4);

            TextView qty5 = new TextView(this);
            qty5.setText(repair.getLabourCost()+"");
            qty5.setBackground(getDrawable(R.drawable.cell_background));
            tableRow.addView(qty5);

            mTableRepair.addView(tableRow, i+1);
        }
    }

    @Override
    public void onSuccessServiceFetch(final List<ServiceDTO> serviceList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                UIUtils.cancelProgressDialog();
                createServiceTable(serviceList);
            }
        });
    }

    private void createServiceTable(List<ServiceDTO> serviceDTOList) {
        mTableService.setVisibility(View.VISIBLE);
        mTableRepair.setVisibility(View.GONE);
        mTableFuel.setVisibility(View.GONE);

        for (int i = 0; i < serviceDTOList.size(); i++) {

            TableRow tableRow = new TableRow(ReportActivity.this);
            tableRow.setLayoutParams(new TableRow.LayoutParams( TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT ));
            ServiceDTO serviceDTO = serviceDTOList.get(i);

            TextView qty0 = new TextView(this);
            qty0.setText((i+1)+"");
            qty0.setBackground(getDrawable(R.drawable.cell_background));
            tableRow.addView(qty0);

            TextView qty = new TextView(this);
            qty.setText(serviceDTO.getType());
            qty.setBackground(getDrawable(R.drawable.cell_background));
            tableRow.addView(qty);

            TextView qty1 = new TextView(this);
            qty1.setText(serviceDTO.getLabourcost()+"");
            qty1.setBackground(getDrawable(R.drawable.cell_background));
            tableRow.addView(qty1);

            TextView qty2 = new TextView(this);
            qty2.setText(serviceDTO.getPartscost()+"");
            qty2.setBackground(getDrawable(R.drawable.cell_background));
            tableRow.addView(qty2);

            TextView qty3 = new TextView(this);
            qty3.setText(serviceDTO.getTax()+"");
            qty3.setBackground(getDrawable(R.drawable.cell_background));
            tableRow.addView(qty3);

            TextView qty4 = new TextView(this);
            qty4.setText(UIUtils.convertMillsToDate(serviceDTO.getCompletedDate())+"");
            qty4.setBackground(getDrawable(R.drawable.cell_background));
            tableRow.addView(qty4);

            TextView qty5 = new TextView(this);
            qty5.setText(UIUtils.convertMillsToDate(serviceDTO.getNextDue())+"");
            qty5.setBackground(getDrawable(R.drawable.cell_background));
            tableRow.addView(qty5);

            TextView qty6 = new TextView(this);
            qty6.setText(serviceDTO.getServicedBy());
            qty6.setBackground(getDrawable(R.drawable.cell_background));
            tableRow.addView(qty6);

            TextView qty7 = new TextView(this);
            qty7.setText(serviceDTO.getComments());
            qty7.setBackground(getDrawable(R.drawable.cell_background));
            tableRow.addView(qty7);

            mTableService.addView(tableRow, i+1);
        }
    }

    @Override
    public void onSuccessFuelFetch(final List<Fuel> fuelList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                UIUtils.cancelProgressDialog();
                createFuelTable(fuelList);
            }
        });
    }

    private void createFuelTable(List<Fuel> fuelList) {
        mTableFuel.setVisibility(View.VISIBLE);
        mTableRepair.setVisibility(View.GONE);
        mTableService.setVisibility(View.GONE);

        for (int i = 0; i < fuelList.size(); i++) {

            TableRow tableRow = new TableRow(ReportActivity.this);
            tableRow.setLayoutParams(new TableRow.LayoutParams( TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT ));
            Fuel fuel = fuelList.get(i);

            TextView qty0 = new TextView(this);
            qty0.setText((i+1)+"");
            qty0.setBackground(getDrawable(R.drawable.cell_background));
            tableRow.addView(qty0);

            TextView qty = new TextView(this);
            qty.setText(UIUtils.convertMillsToDate(fuel.getDate())+"");
            qty.setBackground(getDrawable(R.drawable.cell_background));
            tableRow.addView(qty);

            TextView qty1 = new TextView(this);
            qty1.setText(fuel.getFuelType()+"");
            qty1.setBackground(getDrawable(R.drawable.cell_background));
            tableRow.addView(qty1);

            TextView qty2 = new TextView(this);
            qty2.setText(fuel.getPlace()+"");
            qty2.setBackground(getDrawable(R.drawable.cell_background));
            tableRow.addView(qty2);

            TextView qty3 = new TextView(this);
            qty3.setText(fuel.getTotalFuel()+"");
            qty3.setBackground(getDrawable(R.drawable.cell_background));
            tableRow.addView(qty3);

            TextView qty4 = new TextView(this);
            qty4.setText(fuel.getAmount()+"");
            qty4.setBackground(getDrawable(R.drawable.cell_background));
            tableRow.addView(qty4);

            mTableFuel.addView(tableRow, i+1);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId() == mSpinnerReportType.getId()){
            mReportType = i;
        } else if (adapterView.getId() == mSpinnerReportUsers.getId()){
            String user = (String)adapterView.getAdapter().getItem(i);
            mUserID = Integer.parseInt(user.split("-")[0]);
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

    public void getUsers() {
        UserFetchServiceHandler userFetchServiceHandler = new UserFetchServiceHandler(ReportActivity.this);
        userFetchServiceHandler.getUsersListRequest(ReportActivity.this);

    }
}
