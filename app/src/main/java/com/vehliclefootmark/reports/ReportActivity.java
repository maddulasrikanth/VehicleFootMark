package com.vehliclefootmark.reports;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.vehliclefootmark.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReportActivity extends Activity implements View.OnClickListener{

    private Button mBtnGenerate;
    private int year;
    private int month;
    private int day;
    private ImageView mBackButton;
    private TextView mTxtHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        initUI();
        List<String> data = new ArrayList<>();
        data.add("1");
        data.add("1");
        data.add("1");
        data.add("1");
        data.add("1");
        data.add("2");
        data.add("2");
        data.add("2");
        data.add("2");
        data.add("2");
        data.add("3");
        data.add("3");
        data.add("3");
        data.add("3");
        data.add("3");
        data.add("4");
        data.add("4");
        data.add("4");
        data.add("4");
        data.add("4");
        data.add("5");
        data.add("5");
        data.add("5");
        data.add("5");
        data.add("5");
        data.add("6");
        data.add("6");
        data.add("6");
        data.add("6");
        data.add("6");
        data.add("6");
        data.add("7");
        data.add("7");
        data.add("7");
        data.add("7");
        data.add("7");
        data.add("7");
        createTable(data);
    }
    private void initUI() {
        setHeader();
        mBtnGenerate = (Button) findViewById(R.id.btn_generate);
        mBtnGenerate.setOnClickListener(this);
        /*mETFuelPlace = (EditText) findViewById(R.id.et_fuel_place);
        mETFuelAmount = (EditText) findViewById(R.id.et_fuel_amount);
        mETFuelTotal = (EditText) findViewById(R.id.et_fuel_total);

        mETDateFuelEntered = (EditText) findViewById(R.id.et_date_fuel_entered);
        mETDateFuelEntered.setOnClickListener(this);*/

        Spinner spinner = (Spinner) findViewById(R.id.spinner_report_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.fuel_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //spinner.setOnItemSelectedListener(this);

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
        /*TableRow row1 = new TableRow(this);
        row1.setLayoutParams(new TableRow.LayoutParams( TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT ));
        TextView qty0 = new TextView(this);
        qty0.setText("0");
        row1.addView(qty0);
        ll.addView(row1, 0);*/
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

    }
}
