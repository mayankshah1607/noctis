package com.ieeevit.noctis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterPage extends AppCompatActivity {
    Button signUp;
    DatabaseReference db;
    EditText nameField,regField,passField,emailField,cnfPassField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);

    signUp = (Button) findViewById(R.id.signup);
    nameField = (EditText) findViewById(R.id.name);
    regField = (EditText) findViewById(R.id.reg_no);
    passField = (EditText) findViewById(R.id.pass);
    emailField = (EditText) findViewById(R.id.email);
    cnfPassField = (EditText) findViewById(R.id.email);



    signUp.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String Name = nameField.getText().toString().trim();
            String regNo = regField.getText().toString().trim();
            String Pass = passField.getText().toString().trim();
            String cnfPass = cnfPassField.getText().toString().trim();
            String Email = emailField.getText().toString().trim();

            db = FirebaseDatabase.getInstance().getReference().child(regNo);

            db.child("Name").setValue(Name);
            db.child("Email").setValue(Email);
        db.child("Password").setValue(Pass);}
    });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }
}
