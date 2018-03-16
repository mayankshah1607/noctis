package com.ieeevit.noctis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class signIn extends AppCompatActivity {
    Button signup,login;
    TextView forgotPass;
    EditText email,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        open();
        loginApp();
        resetPass();
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

    private void loginApp() {
        login = (Button) findViewById(R.id.loginbtn);
        login.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Sign in
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
