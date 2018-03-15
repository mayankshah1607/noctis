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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class signUp extends AppCompatActivity {
    Button button;
    TextView Email,regNo,Pass,CnfPass;
    ProgressBar prog;
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
        prog = (ProgressBar) findViewById(R.id.progressBar);
        String email = Email.getText().toString().trim();
        String regno = regNo.getText().toString().trim();
        String pass = Pass.getText().toString().trim();
        String cnfpass = CnfPass.getText().toString().trim();
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Email.setError("Please enter a valid email address");
            check=true;
        }

        if (pass.length()<6) {
            Pass.setError("Password must be at least 6 characters long.");
        }

        if (email.isEmpty()) {
            Email.setError("This field cannot be empty.");
            check = true;
        }
        if (regno.isEmpty()) {
            regNo.setError("This field cannot be empty.");
            check=true;
        }
        if (pass.isEmpty()) {
            Pass.setError("This field cannot be empty.");
            check=true;
        }
        if (cnfpass.isEmpty()) {
            CnfPass.setError("This field cannot be empty.");
            check=true;
        }

        if (!pass.equals(cnfpass)) {
            CnfPass.setError("Passwords do no match.");
            check=true;
        }

        if (check==false) {
            prog.setVisibility(View.VISIBLE);
            button.setText("SIGNING UP...");
            mAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            prog.setVisibility(View.GONE);
                            button.setText("SIGN UP");
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(),"Successfully registered!", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(getApplicationContext(),"An unexpected error occured", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }



    private void initializeWidgets() {
        button = (Button) findViewById(R.id.button);
        Email = (TextView) findViewById(R.id.email);
        regNo = (TextView) findViewById(R.id.regno);
        Pass = (TextView) findViewById(R.id.pass);
        CnfPass = (TextView) findViewById(R.id.cnfpass);

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
