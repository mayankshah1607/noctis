package com.ieeevit.noctis;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Mayank on 3/20/2018.
 */

public class MainUserHistory extends Fragment {
    RecyclerView mRecyclerView;
    String curName,curReg,curEmail,curPh;
    String currentuser;
    DatabaseReference nameRef,emailRef,phRef,regRef,mainDb;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_user_history,container,false);
        currentuser = FirebaseAuth.getInstance().getUid();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        mainDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentuser).child("History");
        getUserData();
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
}
