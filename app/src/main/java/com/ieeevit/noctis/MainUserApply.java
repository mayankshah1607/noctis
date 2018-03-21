package com.ieeevit.noctis;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by Mayank on 3/20/2018.
 */

public class MainUserApply extends Fragment {
    Button fromDate,fromTime,toDate,toTime;
    DatePickerDialog.OnDateSetListener pick1,pick2;
    TimePickerDialog.OnTimeSetListener pick3,pick4;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_user_apply,container,false);
        fromDate = (Button) view.findViewById(R.id.fromdatebutton);
        fromTime = (Button) view.findViewById(R.id.fromtimebutton);
        toDate = (Button) view.findViewById(R.id.todatebutton);
        toTime = (Button) view.findViewById(R.id.totimebutton);
        setfromdate();
        settodate();
        setfromtime();
        settotime();
        return view;
    }

    private void settodate() {
    }

    private void setfromtime() {
    }

    private void settotime() {
    }

    private void setfromdate() {
        fromDate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar cal = Calendar.getInstance();
                        int year = cal.get(Calendar.YEAR);
                        int day = cal.get(Calendar.DAY_OF_MONTH);
                        int month = cal.get(Calendar.MONTH);

                        DatePickerDialog dialog = new DatePickerDialog(
                                getActivity(),R.style.DialogTheme,pick1,year,month,day);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    }
                });
        pick1 = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = day + "-" + month + "year";
                fromDate.setText(date);
                fromDate.setBackgroundColor(getResources().getColor(R.color.ButtonGreen));
            }
        };
    }
}
