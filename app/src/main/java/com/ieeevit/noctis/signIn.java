package com.ieeevit.noctis;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class signIn extends AppCompatActivity {
    Button signup,login;
    TextView forgotPass;
    EditText email,pass;
    CheckBox checkBox;
    String loginType;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        login = (Button) findViewById(R.id.loginbtn);
        email = (EditText) findViewById(R.id.emailEditText);
        pass = (EditText) findViewById(R.id.passEditText);
        checkBox = (CheckBox) findViewById(R.id.cb);
        open();
        resetPass();
        login.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startApp();
                    }
                }
        );


    }

    private void startApp() {
        mAuth = FirebaseAuth.getInstance();
        String Email= email.getText().toString().trim();
        String Pass = pass.getText().toString().trim();

        if (checkBox.isChecked()) {
            loginType="Admin";
        }
        else {
            loginType="Normal";
        }


        login.setText("Logging in...");
        if (Email.isEmpty()) {
            login.setText("Log in");
            Toast.makeText(getApplicationContext(),"Please enter your Email ID", Toast.LENGTH_SHORT).show();
            return;
        }
        if (Pass.isEmpty()) {
            login.setText("Log in");
            Toast.makeText(getApplicationContext(),"Please enter your password", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(Email,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    //Sign In
                    String curUserID = mAuth.getCurrentUser().getUid();
                    DatabaseReference curUserDb = FirebaseDatabase.getInstance().getReference().child("Users").child(curUserID).child("Account Type");
                    curUserDb.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String checkType = dataSnapshot.getValue().toString();
                            login.setText("Log in");
                            if (checkType.equals("Admin")) {
                                if (loginType.equals("Admin")) {
                                    Intent toAdmin = new Intent(signIn.this,MainAdmin.class);
                                    startActivity(toAdmin);
                                    finish();
                                }

                                if (loginType.equals("Normal")) {
                                    Intent toUser = new Intent(signIn.this,MainUser.class);
                                    startActivity(toUser);
                                    finish();
                                }
                            }

                            if (checkType.equals("Normal")) {
                                if (loginType.equals("Admin")) {
                                    Toast.makeText(getApplicationContext(),"This account is not registered as an Admin", Toast.LENGTH_SHORT).show();

                                }

                                if (loginType.equals("Normal")) {
                                    Intent toUser = new Intent(signIn.this,MainUser.class);
                                    startActivity(toUser);
                                    finish();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(getApplicationContext(),"No Internet Connection", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                else {
                    Toast.makeText(getApplicationContext(),"Please check your credentials and try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    private void resetPass() {
        forgotPass = (TextView) findViewById(R.id.forgot);
        forgotPass.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent newIntent = new Intent(signIn.this,passReset.class);
                        startActivity(newIntent);
                        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                    }
                }
        );
    }



    public void open(){
        signup = (Button) findViewById(R.id.signupbtn);
        signup.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent myintent = new Intent(signIn.this,signUp.class);
                        startActivity(myintent);
                        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                    }
                }
        );
    }

}
