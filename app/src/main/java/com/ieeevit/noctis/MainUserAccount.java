package com.ieeevit.noctis;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Ref;

/**
 * Created by Mayank on 3/20/2018.
 */

public class MainUserAccount extends Fragment {

    String curName,curReg,curEmail,curPh,curAc,currentuser, updatedAccType;;
    DatabaseReference nameRef,emailRef,phRef,regRef,accRef;
    EditText Name,Reg,Email,Phone;
    Switch switch1;
    ProgressDialog progressDialog;
    Button SaveProfile,SavePassword;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_user_account,container,false);
        currentuser = FirebaseAuth.getInstance().getUid();
        Name = (EditText) view.findViewById(R.id.editTextName);
        Reg = (EditText) view.findViewById(R.id.editTextReg);
        Email = (EditText) view.findViewById(R.id.editTextEmail);
        Phone = (EditText)view.findViewById(R.id.editTextPh);
        SaveProfile = (Button) view.findViewById(R.id.savebutton);
        switch1 = (Switch) view.findViewById(R.id.switch1);
        getUserData();
        saveProfileData();
        return view;

    }

    private void saveProfileData() {
        SaveProfile.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String updatedName = Name.getText().toString();
                        String updatedReg = Reg.getText().toString();
                        String updatedEmail = Email.getText().toString();
                        String updatedPhone = Email.getText().toString();
                        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b == true) {
                                    updatedAccType="Admin";
                                }
                                else {
                                    updatedAccType = "Normal";
                                }
                            }
                        });


                    }
                }
        );
    }

    private void getUserData() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait..."); // Setting Message
        //progressDialog.setTitle("Please wait.."); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);

        nameRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentuser).child("Name");
        nameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                curName = dataSnapshot.getValue().toString();
                Name.setText(curName);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(),"No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });

        emailRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentuser).child("Email");
        emailRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                curEmail = dataSnapshot.getValue().toString();
                Email.setText(curEmail);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(),"No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });

        phRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentuser).child("Phone Number");
        phRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                curPh = dataSnapshot.getValue().toString();
                Phone.setText(curPh);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(),"No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });

        regRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentuser).child("Registration Number");
        regRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                curReg = dataSnapshot.getValue().toString();
                Reg.setText(curReg);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(),"No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });

        accRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentuser).child("Account Type");
        accRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                curAc = dataSnapshot.getValue().toString();
                if (curAc.equals("Admin")) {
                    switch1.setChecked(true);
                }
                else {
                    switch1.setChecked(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(),"No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
        progressDialog.dismiss();

    }
}
