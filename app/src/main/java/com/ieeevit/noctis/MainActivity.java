package com.ieeevit.noctis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button login,register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        open();

    }
    public void open(){
        login = (Button) findViewById(R.id.button_1);
        register = (Button) findViewById(R.id.button_2);

        login.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent lgn = new Intent("com.ieeevit.noctis.LoginPage");
                        startActivity(lgn);
                        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                    }
                }
        );

        register.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent reg = new Intent("com.ieeevit.noctis.RegisterPage");
                        startActivity(reg);
                        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                    }
                }
        );

    }




}
