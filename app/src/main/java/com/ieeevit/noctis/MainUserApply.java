package com.ieeevit.noctis;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.Timer;

/**
 * Created by Mayank on 3/20/2018.
 */

public class MainUserApply extends Fragment {
    Button fromDate,fromTime,toDate,toTime,Apply;
    DatePickerDialog.OnDateSetListener pick1,pick2;
    TimePickerDialog.OnTimeSetListener pick3,pick4;
    String dateToday,finalToDate="",finalFromdate="",finalToTime="",finalFromTime="";
    String curName,curReg,curEmail,curPh;
    EditText adminEmail,roomNo;
    //FirebaseAuth mAuth;
    //DatabaseReference curUserDb;
    String currentuser;
    DatabaseReference nameRef,emailRef,phRef,regRef;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_user_apply,container,false);
        fromDate = (Button) view.findViewById(R.id.fromdatebutton);
        fromTime = (Button) view.findViewById(R.id.fromtimebutton);
        toDate = (Button) view.findViewById(R.id.todatebutton);
        toTime = (Button) view.findViewById(R.id.totimebutton);
        Apply = (Button) view.findViewById(R.id.applybutton);
        adminEmail = (EditText) view.findViewById(R.id.adminemail);
        roomNo = (EditText) view.findViewById(R.id.roomno);
        currentuser = FirebaseAuth.getInstance().getUid();
        getUserData();
        setfromdate();
        settodate();
        setfromtime();
        settotime();
        apply();
        return view;
    }


    private void getUserData() {
        nameRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentuser).child("Name");
        nameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                curName = dataSnapshot.getValue().toString();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(),"No Internet!", Toast.LENGTH_SHORT).show();
            }
        });

        emailRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentuser).child("Email");
        emailRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                curEmail = dataSnapshot.getValue().toString();
                ;

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(),"No Internet!", Toast.LENGTH_SHORT).show();
            }
        });

        phRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentuser).child("Phone Number");
        phRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                curPh = dataSnapshot.getValue().toString();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(),"No Internet!", Toast.LENGTH_SHORT).show();
            }
        });

        regRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentuser).child("Registration Number");
        regRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                curReg = dataSnapshot.getValue().toString();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(),"No Internet!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void apply() {

        Apply.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (finalFromdate.isEmpty() || finalToDate.isEmpty() || finalFromTime.isEmpty() || finalToTime.isEmpty() || adminEmail.getText().toString().trim().isEmpty() || roomNo.getText().toString().trim().isEmpty()) {
                            Toast.makeText(getActivity(),"Please fill in all the fields", Toast.LENGTH_SHORT).show();
                            return ;
                        }
                        else {
                            Apply.setText("Please Wait...");
                        String AdminEmail = adminEmail.getText().toString().trim();
                        String RoomNo = roomNo.getText().toString().trim();
                        DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentuser).child("History").child(dateToday);
                        Map newMap = new HashMap();
                        Map map2 = new HashMap();
                        newMap.put("From Date",finalFromdate);
                        newMap.put("From Time",finalFromTime);
                        newMap.put("To Date",finalToDate);
                        newMap.put("To Time",finalToTime);
                        newMap.put("Admin",AdminEmail);
                        currentUserDb.setValue(newMap);

                        DatabaseReference currentLeaveDb = FirebaseDatabase.getInstance().getReference().child("Leave Data").child(AdminEmail).child(dateToday).child(curName);
                        map2.put("Name",curName);
                        map2.put("Phone Number",curPh);
                        map2.put("Email ID",curEmail);
                        map2.put("Room Number",RoomNo);
                        currentLeaveDb.setValue(map2);
                        Apply.setText("Apply");
                        Toast.makeText(getActivity(),"Successfully Applied!", Toast.LENGTH_SHORT).show();
                        fromDate.setText("Pick date");
                        fromDate.setBackground(getResources().getDrawable(R.drawable.darkbutton));
                        toDate.setText("Pick date");
                        toDate.setBackground(getResources().getDrawable(R.drawable.darkbutton));
                        fromTime.setText("Pick Time");
                        fromTime.setBackground(getResources().getDrawable(R.drawable.darkbutton));
                        toTime.setText("Pick Time");
                        toTime.setBackground(getResources().getDrawable(R.drawable.darkbutton));
                        adminEmail.setText("");
                        roomNo.setText("");
                    } // else ends Here
                    }
                }
        );
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
                finalToDate=date;
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
                else {hour = ""+h;}
                if (m<10) {mins = "0"+m;}
                else {mins = ""+m;}
                time = hour+":"+mins+"hrs";
                finalFromTime=time;
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
                else {hour = ""+h;}
                if (m<10) {mins = "0"+m;}
                else {mins = ""+m;}
                time = hour+":"+mins+"hrs";
                finalToTime=time;
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
                finalFromdate=date;
                fromDate.setText(date);
                fromDate.setBackground(getResources().getDrawable(R.drawable.button_green));
            }
        };
    }
}
