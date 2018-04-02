package com.ieeevit.noctis.Fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ieeevit.noctis.R;

import java.util.Calendar;

/**
 * Created by Mayank on 3/20/2018.
 */

public class ViewRequests extends Fragment {
    String currentuser,AdminReg,Date;
    DatabaseReference regRef;
    Button PickDate;
    DatePickerDialog.OnDateSetListener pick;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_requests,container,false);
        currentuser = FirebaseAuth.getInstance().getUid();
        PickDate = (Button) view.findViewById(R.id.pickdate);
        PickDate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar cal = Calendar.getInstance();
                        int year = cal.get(Calendar.YEAR);
                        int day = cal.get(Calendar.DAY_OF_MONTH);
                        int month = cal.get(Calendar.MONTH);
                        DatePickerDialog dialog = new DatePickerDialog(
                                getActivity(),R.style.DialogTheme,pick,year,month,day);
                        dialog.getWindow();
                        dialog.show();
                    }
                }
        );
        pick = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = day + "-" + month + "-" + year;
                Date=date;
                PickDate.setText(date);
                PickDate.setBackground(getResources().getDrawable(R.drawable.button_green));
            }
        };
        getAdminReg();
        return view;

    }

    private void getAdminReg() {

        regRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentuser).child("RegistrationNumber");
        regRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                AdminReg = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(),"No Internet!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
