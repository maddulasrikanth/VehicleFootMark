package com.vehliclefootmark.fuel;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
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

import com.vehliclefootmark.R;

import java.util.Calendar;

public class FuelEntryActivity extends Activity  implements View.OnClickListener, AdapterView.OnItemSelectedListener{

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_entry);
        initUI();
    }

    private void initUI() {
        setHeader();
        mBtnSave = (Button) findViewById(R.id.btn_fuel_save);
        mBtnSave.setOnClickListener(this);
        mETFuelPlace = (EditText) findViewById(R.id.et_fuel_place);
        mETFuelAmount = (EditText) findViewById(R.id.et_fuel_amount);
        mETFuelTotal = (EditText) findViewById(R.id.et_fuel_total);

        Spinner spinner = (Spinner) findViewById(R.id.spinner_fuel_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.fuel_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        mETDateFuelEntered = (EditText) findViewById(R.id.et_date_fuel_entered);
        mETDateFuelEntered.setOnClickListener(this);

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
        } else if (view == mBackButton){
            finish();
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
        switch (pos) {
            case 0:
                Toast.makeText(FuelEntryActivity.this, R.string.lbl_ethanol, Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(FuelEntryActivity.this, R.string.lbl_methanol, Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(FuelEntryActivity.this, R.string.lbl_gasoline, Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(FuelEntryActivity.this, R.string.lbl_diesel, Toast.LENGTH_SHORT).show();
                break;
            case 4:
                Toast.makeText(FuelEntryActivity.this, R.string.lbl_biodiesel, Toast.LENGTH_SHORT).show();
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

            // set selected date into textview
            mETDateFuelEntered.setText(new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year)
                    .append(" "));

        }
    };

}
