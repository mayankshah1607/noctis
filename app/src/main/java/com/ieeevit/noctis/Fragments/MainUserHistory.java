package com.ieeevit.noctis.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ieeevit.noctis.CardClass;
import com.ieeevit.noctis.R;

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
        mainDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentuser).child("History");
        mainDb.keepSynced(true);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getUserData();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<CardClass,holdView>firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<CardClass, holdView>
                (CardClass.class,R.layout.card,holdView.class,mainDb) {
            @Override
            protected void populateViewHolder(holdView viewHolder, CardClass model, int position) {
                viewHolder.setAdmin(model.getAdmin());
                viewHolder.setFromDate(model.getFromDate());
                viewHolder.setFromTime(model.getFromTime());
                viewHolder.setToDate(model.getToDate());
                viewHolder.setToTime(model.getToTime());
            }
        };
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class holdView extends RecyclerView.ViewHolder{
        View mView;
        public holdView(View itemview) {
            super(itemview);
            mView=itemview;
        }
        public void setFromDate(String FromDate) {
            TextView Fromdatetext = (TextView) mView.findViewById(R.id.fromdatetext);
            Fromdatetext.setText(FromDate);
        }
        public void setToDate(String ToDate) {
            TextView Todatetext = (TextView) mView.findViewById(R.id.todatetext);
            Todatetext.setText(ToDate);
        }
        public void setFromTime(String FromTime) {
            TextView Fromtimetext = (TextView) mView.findViewById(R.id.fromtimetext);
            Fromtimetext.setText(FromTime);
        }
        public void setToTime(String ToTime) {
            TextView Totimetext = (TextView) mView.findViewById(R.id.totimetext);
            Totimetext.setText(ToTime);
        }
        public void setAdmin(String Admin) {
            TextView admin = (TextView) mView.findViewById(R.id.adminemailtext);
            admin.setText(Admin);
        }
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

        phRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentuser).child("PhoneNumber");
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

        regRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentuser).child("RegistrationNumber");
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
