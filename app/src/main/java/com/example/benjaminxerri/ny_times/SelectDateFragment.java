package com.example.benjaminxerri.ny_times;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by benjaminxerri on 10/5/17.
 */

public class SelectDateFragment  implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    TextView _editText;
    private int date;
    private int month;
    private int year;
    private Context _context;

    public SelectDateFragment(Context context, TextView _editText)
    {
        //Activity act = (Activity)context;
        this._editText = _editText;
        this._editText.setOnClickListener(this);
        this._context = context;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        this.year = year;
        month = monthOfYear;
        date = dayOfMonth;
        updateDisplay();
    }
    @Override
    public void onClick(View v) {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());


        DatePickerDialog dialog = new DatePickerDialog(_context, this,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        dialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
        dialog.show();

    }

    // updates the date in the birth date EditText
    private void updateDisplay() {

        _editText.setText(new StringBuilder()
                // Month is 0 based so add 1
                .append(month+1).append("/").append(date).append("/").append(year).append(" "));
    }
}
