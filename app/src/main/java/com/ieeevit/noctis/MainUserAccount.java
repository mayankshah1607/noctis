package com.ieeevit.noctis;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

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

import java.sql.Ref;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mayank on 3/20/2018.
 */

public class MainUserAccount extends Fragment {

    String curName,curReg,curEmail,curPh,curAc,currentuser, updatedAccType;
    EditText Name,Reg,Email,Phone,oldPass,newPass,CnfNewPass;
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
        SavePassword = (Button) view.findViewById(R.id.savepass);
        switch1 = (Switch) view.findViewById(R.id.switch1);
        oldPass = (EditText) view.findViewById(R.id.curPass);
        newPass = (EditText) view.findViewById(R.id.newPass);
        CnfNewPass = (EditText) view.findViewById(R.id.cnfnewPass);
        updateUserdata();
        getUserData();
        SavePassword.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        updatePassword();
                    }
                }
        );

        return view;

    }

    private void updatePassword() {
        final String NewPassword = newPass.getText().toString();
        String OldPassword = oldPass.getText().toString();
        String CnfNewPassword = CnfNewPass.getText().toString();
        if (NewPassword.isEmpty() || OldPassword.isEmpty() || CnfNewPassword.isEmpty()) {
            Toast.makeText(getActivity(),"Please fill in all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!NewPassword.equals(CnfNewPassword)) {
            Toast.makeText(getActivity(),"Please re-confirm your password once again", Toast.LENGTH_SHORT).show();
            return;
        }

        SavePassword.setText("Please wait..");
        final FirebaseUser user;
        user = FirebaseAuth.getInstance().getCurrentUser();
        final String email = user.getEmail();
        AuthCredential credential = EmailAuthProvider.getCredential(email,OldPassword);
        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    user.updatePassword(NewPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(getActivity(),"Something went wrong. Try again later", Toast.LENGTH_SHORT).show();
                                SavePassword.setText("update password");
                            }else {
                                Toast.makeText(getActivity(),"Password has been successfully updated", Toast.LENGTH_SHORT).show();
                                SavePassword.setText("update password");
                                oldPass.setText("");
                                newPass.setText("");
                                CnfNewPass.setText("");
                            }
                        }
                    });
                }else {
                    Toast.makeText(getActivity(),"Your old password does not match", Toast.LENGTH_SHORT).show();
                    SavePassword.setText("update password");
                }
            }
        });
    }


    private void updateUserdata() {
        SaveProfile.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (Name.getText().toString().isEmpty() || Reg.getText().toString().isEmpty() || Phone.getText().toString().isEmpty()) {
                            Toast.makeText(getActivity(),"Please fill in all the details", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            if (switch1.isChecked()) {
                                updatedAccType= "Admin";
                            }
                            if (!switch1.isChecked()) {
                                updatedAccType = "Normal";
                            }
                        SaveProfile.setText("please wait...");
                        DatabaseReference nameRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentuser).child("Name");
                        nameRef.setValue(Name.getText().toString());
                        DatabaseReference regRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentuser).child("Registration Number");
                        regRef.setValue(Reg.getText().toString());
                        DatabaseReference phRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentuser).child("Phone Number");
                        phRef.setValue(Phone.getText().toString());

                            DatabaseReference accRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentuser).child("Account Type");
                            accRef.setValue(updatedAccType);

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        user.updateEmail(Email.getText().toString().trim())
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            SaveProfile.setText("save changes");
                                            if (task.isSuccessful()) {
                                                DatabaseReference emailRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentuser).child("Email");
                                                emailRef.setValue(Email.getText().toString());
                                                Toast.makeText(getActivity(),"Your details have been updated successfully", Toast.LENGTH_SHORT).show();;
                                            }
                                            else {
                                                Toast.makeText(getActivity(),"Something went wrong, please try again later", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                        }
                    }
                }
        );

    }


    private void getUserData() {
        DatabaseReference nameRef,emailRef,phRef,regRef,accRef;
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
