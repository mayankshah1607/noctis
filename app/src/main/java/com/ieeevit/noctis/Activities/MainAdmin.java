package com.ieeevit.noctis.Activities;

import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;

import com.ieeevit.noctis.MainUserAccount;
import com.ieeevit.noctis.R;
import com.ieeevit.noctis.SecitionsPageAdapter;
import com.ieeevit.noctis.ViewRequests;

public class MainAdmin extends AppCompatActivity {
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
        adapter.addFragment(new ViewRequests(),"view requests");
        adapter.addFragment(new MainUserAccount(),"Account");
        viewPager.setAdapter(adapter);
    }
}
