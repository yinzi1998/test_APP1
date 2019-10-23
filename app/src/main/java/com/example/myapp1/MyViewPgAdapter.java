package com.example.myapp1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyViewPgAdapter extends FragmentPagerAdapter {

    private String[] title = {"First", "Second", "Third"};

    //构造方法
    public MyViewPgAdapter(FragmentManager manager){
        super(manager);
    }

    //管理显示滑动窗口下显示哪一个页面
    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return new ViewPgFirstFragment();
        }else if(position == 1){
            return new ViewPgSecondFragment();
        }else{
            return new ViewPgThirdFragment();
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }

    @Override
    public int getCount() {
        return 3;
    }
}
