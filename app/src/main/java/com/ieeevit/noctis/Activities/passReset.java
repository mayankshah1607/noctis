package com.ieeevit.noctis.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ieeevit.noctis.R;

public class passReset extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_reset);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

}
