package com.darkpingouin.christmascountdown;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class Main2Activity extends AppCompatActivity {


    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);



        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

    }
    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new Tab1Fragment());
        adapter.addFragment(new Tab2Fragment());
        adapter.addFragment(new Tab3Fragment());
        adapter.addFragment(new Tab4Fragment());
        adapter.addFragment(new Tab5Fragment());
        adapter.addFragment(new Tab6Fragment());
        adapter.addFragment(new Tab7Fragment());
        adapter.addFragment(new Tab8Fragment());
        viewPager.setAdapter(adapter);
    }


}
