package com.ieeevit.noctis;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class signUp extends AppCompatActivity {
    Button button;
    EditText Email,regNo,Pass,CnfPass,Phno;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initializeWidgets();
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        createUser();
                    }
                }
        );
    }

    private void createUser(){
        boolean check = false;
        mAuth = FirebaseAuth.getInstance();
        String email = Email.getText().toString().trim();
        String regno = regNo.getText().toString().trim();
        String pass = Pass.getText().toString().trim();
        String cnfpass = CnfPass.getText().toString().trim();
        String phno = Phno.getText().toString().trim();



        if (email.isEmpty()) {
            Toast.makeText(getApplicationContext(),"Enter your email address", Toast.LENGTH_SHORT).show();
            check=true;
            return;
        }

        if (regno.isEmpty()) {
            Toast.makeText(getApplicationContext(),"Enter your registration number", Toast.LENGTH_SHORT).show();
            check=true;
            return;}

        if (phno.isEmpty()) {
            Toast.makeText(getApplicationContext(),"Enter your phone number", Toast.LENGTH_SHORT).show();
            check=true;
        }

        if (pass.isEmpty()) {
            Toast.makeText(getApplicationContext(),"Enter a desired password", Toast.LENGTH_SHORT).show();
            check=true;
            return;
        }

        if (cnfpass.isEmpty()) {
            Toast.makeText(getApplicationContext(),"Please confirm your password", Toast.LENGTH_SHORT).show();
            check=true;
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(getApplicationContext(),"Enter a valid email address", Toast.LENGTH_SHORT).show();
            check=true;
            return;
        }

        if (pass.length()<6) {
            Toast.makeText(getApplicationContext(),"Password must be at least 6 characters long", Toast.LENGTH_SHORT).show();
            check=true;
            return;
        }

        if (phno.length()!=10) {
            Toast.makeText(getApplicationContext(),"Enter a valid phone number", Toast.LENGTH_SHORT).show();
            check=true;
            return;}


        if (!pass.equals(cnfpass)) {
            Toast.makeText(getApplicationContext(),"Passwords do not match", Toast.LENGTH_SHORT).show();
            check=true;
            return;
        }

        if (check==false) {
            button.setText("Signing up...");
            mAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            button.setText("SIGN UP");
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(),"Successfully registered!", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                if (task.getException() instanceof FirebaseAuthUserCollisionException){
                                    Toast.makeText(getApplicationContext(),"An account already exists with this email ID", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
        }
    }



    private void initializeWidgets() {
        button = (Button) findViewById(R.id.button);
        Email = (EditText) findViewById(R.id.email);
        regNo = (EditText) findViewById(R.id.regno);
        Pass = (EditText) findViewById(R.id.pass);
        CnfPass = (EditText) findViewById(R.id.cnfpass);
        Phno = (EditText) findViewById(R.id.phno);

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
