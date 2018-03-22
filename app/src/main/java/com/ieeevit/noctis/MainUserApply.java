package com.ieeevit.noctis;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TimeUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.sql.Time;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.Timer;

/**
 * Created by Mayank on 3/20/2018.
 */

public class MainUserApply extends Fragment {
    Button fromDate,fromTime,toDate,toTime;
    DatePickerDialog.OnDateSetListener pick1,pick2;
    TimePickerDialog.OnTimeSetListener pick3,pick4;
    String dateToday;

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
        toDate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar cal = Calendar.getInstance();
                        int year = cal.get(Calendar.YEAR);
                        int day = cal.get(Calendar.DAY_OF_MONTH);
                        int month = cal.get(Calendar.MONTH);

                        DatePickerDialog dialog = new DatePickerDialog(
                                getActivity(),R.style.DialogTheme,pick2,year,month,day);
                        dialog.getWindow();
                        dialog.show();
                    }
                });
        pick2 = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = day + "-" + month + "-" + year;
                toDate.setText(date);
                toDate.setBackground(getResources().getDrawable(R.drawable.button_green));
            }
        };
    }

    private void setfromtime() {
        fromTime.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TimePickerDialog dialog = new TimePickerDialog(
                                getActivity(),R.style.DialogTheme,pick3,0,0,false);
                        dialog.getWindow();
                        dialog.show();
                    }
                });
        pick3 = new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker timePicker, int h, int m) {
                String hour="",mins="",time;
                if (h<10) {hour = "0"+h;}
                if (m<10) {mins = "0"+m;}
                time = hour+":"+mins+"hrs";
                fromTime.setText(time);
                fromTime.setBackground(getResources().getDrawable(R.drawable.button_green));

            }
        };
    }

    private void settotime() {
        toTime.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TimePickerDialog dialog = new TimePickerDialog(
                                getActivity(),R.style.DialogTheme,pick4,0,0,false);
                        dialog.getWindow();
                        dialog.show();
                    }
                });
        pick4 = new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker timePicker, int h, int m) {
                String hour="",mins="",time;
                if (h<10) {hour = "0"+h;}
                if (m<10) {mins = "0"+m;}
                time = hour+":"+mins+"hrs";
               toTime.setText(time);
                toTime.setBackground(getResources().getDrawable(R.drawable.button_green));

            }
        };
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
                        dateToday = day + "-" + month + "-" + year;
                        DatePickerDialog dialog = new DatePickerDialog(
                                getActivity(),R.style.DialogTheme,pick1,year,month,day);
                        dialog.getWindow();
                        dialog.show();
                    }
                });
        pick1 = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = day + "-" + month + "-" + year;
                fromDate.setText(date);
                fromDate.setBackground(getResources().getDrawable(R.drawable.button_green));
            }
        };
    }
}
