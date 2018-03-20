package com.ieeevit.noctis;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class signIn extends AppCompatActivity {
    Button signup,login;
    TextView forgotPass;
    EditText email,pass;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        login = (Button) findViewById(R.id.loginbtn);
        email = (EditText) findViewById(R.id.emailEditText);
        pass = (EditText) findViewById(R.id.passEditText);
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
                login.setText("Log in");
                if (task.isSuccessful()){
                    //Sign In
                    Intent mainIntent = new Intent(signIn.this,MainUser.class);
                    startActivity(mainIntent);
                    finish();
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
