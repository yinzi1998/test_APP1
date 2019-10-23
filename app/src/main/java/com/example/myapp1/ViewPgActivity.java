package com.example.myapp1;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class ViewPgActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pg);

        //滑动窗口
        ViewPager viewPager = findViewById(R.id.viewpager);
        //Adapter管理数据
        MyViewPgAdapter myViewPgAdapter = new MyViewPgAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myViewPgAdapter);
        //滑动窗口上方标题
        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        //将上方标题与滑动窗口绑定
        tabLayout.setupWithViewPager(viewPager);

    }
}
