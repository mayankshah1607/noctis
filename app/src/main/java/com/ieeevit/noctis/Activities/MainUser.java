package com.ieeevit.noctis.Activities;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;

import android.widget.Button;

import com.ieeevit.noctis.Fragments.MainUserAccount;
import com.ieeevit.noctis.Fragments.MainUserApply;
import com.ieeevit.noctis.Fragments.MainUserHistory;
import com.ieeevit.noctis.R;
import com.ieeevit.noctis.SecitionsPageAdapter;

public class MainUser extends AppCompatActivity {
    private SecitionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);
        mSectionsPageAdapter = new SecitionsPageAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager){
        SecitionsPageAdapter adapter = new SecitionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new MainUserApply(),"Apply");
        adapter.addFragment(new MainUserHistory(),"History");
        adapter.addFragment(new MainUserAccount(),"Account");
        viewPager.setAdapter(adapter);
    }


}
