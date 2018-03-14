package com.ieeevit.noctis;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class signUp extends AppCompatActivity {
    Button button;
    TextView Email,regNo,Pass,CnfPass;
    TextInputLayout EmailLayout,regNoLayout,PassLayout,CnfPassLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initializeWidgets();
        createUser();
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        createUser();
                    }
                }
        );
    }

    private void createUser() {
                        FirebaseAuth mAuth;
                        mAuth = FirebaseAuth.getInstance();
                        boolean isValid = true;
                        if (Email.getText().toString().isEmpty()) {
                            EmailLayout.setError("This field is required");
                            isValid=false;
                        }
                        else {EmailLayout.setErrorEnabled(false);}

                        if (regNo.getText().toString().isEmpty()) {
                            regNoLayout.setError("This field is required");
                            isValid=false;
                        }
                        else {regNoLayout.setErrorEnabled(false);}

                        if (Pass.getText().toString().isEmpty()) {
                            PassLayout.setError("This field is required");
                            isValid=false;
                        }
                        else {PassLayout.setErrorEnabled(false);}

                        if (CnfPass.getText().toString().isEmpty()) {
                            CnfPassLayout.setError("This field is required");
                            isValid=false;
                        }
                        else {CnfPassLayout.setErrorEnabled(false);}

                        if (isValid) {
                            //signup the user

                            //Pending
                        }

                    }

    private void initializeWidgets() {
        button = (Button) findViewById(R.id.button);

        Email = (TextView) findViewById(R.id.email);
        regNo = (TextView) findViewById(R.id.regno);
        Pass = (TextView) findViewById(R.id.pass);
        CnfPass = (TextView) findViewById(R.id.cnfpass);

        EmailLayout = (TextInputLayout) findViewById(R.id.emailLayout);
        regNoLayout = (TextInputLayout) findViewById(R.id.regnoLayout);
        PassLayout = (TextInputLayout) findViewById(R.id.passLayout);
        CnfPassLayout = (TextInputLayout) findViewById(R.id.cnfpassLayout);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
