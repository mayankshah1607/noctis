package com.ieeevit.noctis;

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

import java.util.Calendar;

/**
 * Created by Mayank on 3/20/2018.
 */

public class ViewRequests extends Fragment {

    private DatabaseReference mDatabase;
    private RecyclerView mRecyclerView;
    String Date,AdminEmail,currentuser;
    Button PickDate;
    DatePickerDialog.OnDateSetListener pick;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_requests,container,false);
        PickDate = (Button) view.findViewById(R.id.pickdate);
        currentuser = FirebaseAuth.getInstance().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Leave Data").child(AdminEmail).child(Date);
        mDatabase.keepSynced(true);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview2);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getAdminEmail();
        getDate();
        return view;

    }


    private void viewData() {
        FirebaseRecyclerAdapter<CardClass_Admin,viewMyCard>firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<CardClass_Admin, viewMyCard>
                (CardClass_Admin.class,R.layout.card_admin,viewMyCard.class,mDatabase) {
            @Override
            protected void populateViewHolder(viewMyCard viewHolder, CardClass_Admin model, int position) {
                viewHolder.setName(model.getName());
                viewHolder.setEmailID(model.getEmailID());
                viewHolder.setPhoneNumber(model.getPhoneNumber());
                viewHolder.setRoomNumber(model.getPhoneNumber());
                viewHolder.setRegistrationNumber(model.getRegistrationNumber());
            }
        };
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class viewMyCard extends RecyclerView.ViewHolder {
        View mView;
        public viewMyCard(View itemView) {
            super(itemView);
            mView=itemView;
        }

        public void setName(String Name) {
            TextView name = (TextView)mView.findViewById(R.id.nametext);
            name.setText(Name);
        }

        public void setRegistrationNumber(String RegistrationNumber) {
            TextView reg = (TextView)mView.findViewById(R.id.regtext);
            reg.setText(RegistrationNumber);
        }

        public void setPhoneNumber(String PhoneNumber) {
            TextView ph = (TextView)mView.findViewById(R.id.phtext);
            ph.setText(PhoneNumber);
        }

        public void setEmailID(String Email) {
            TextView email = (TextView)mView.findViewById(R.id.emailtext);
            email.setText(Email);
        }

        public void setRoomNumber(String RoomNumber) {
            TextView room = (TextView)mView.findViewById(R.id.roomtext);
            room.setText(RoomNumber);
        }

    }

    private void getAdminEmail() {

        DatabaseReference emailRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentuser).child("Email");
        emailRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                AdminEmail = dataSnapshot.getValue().toString();
                Toast.makeText(getActivity(),AdminEmail, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(),"No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDate() {

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
                });
        pick = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = day + "-" + month + "-" + year;
                Date=date;
                PickDate.setText(date);
                PickDate.setBackground(getResources().getDrawable(R.drawable.button_green));
                viewData();
            }
        };
    }
}
