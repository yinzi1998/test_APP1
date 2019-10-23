package com.example.myapp1;

import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FrameActivity extends FragmentActivity {

    private static final String TAG = "FrameActivity";

    private Fragment mFragments[];
    private RadioGroup radioGroup;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private RadioButton rbHome, rbFunc, rbSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        mFragments = new Fragment[3];
        fragmentManager = getSupportFragmentManager();
        mFragments[0] = fragmentManager.findFragmentById(R.id.fragment_home);
        mFragments[1] = fragmentManager.findFragmentById(R.id.fragment_func);
        mFragments[2] = fragmentManager.findFragmentById(R.id.fragment_setting);
        fragmentTransaction = fragmentManager.beginTransaction().hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]);
        fragmentTransaction.show(mFragments[0]).commit();

        rbHome = findViewById(R.id.radioHome);
        rbFunc = findViewById(R.id.radioFunc);
        rbSetting = findViewById(R.id.radioSetting);
        //home按钮处于点击状态
        rbHome.setBackgroundResource(R.drawable.shape3);

        radioGroup = findViewById(R.id.bottomGroup);

        //对三个按钮组添加监听，选择哪个显示哪个界面，实现fragment的切换
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkdId) {
                Log.i(TAG, "onCheckedChanged: 我们选择RadioGroup中的= " + checkdId);
                fragmentTransaction = fragmentManager.beginTransaction().hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]);
                rbHome.setBackgroundResource(R.drawable.shape2);
                rbFunc.setBackgroundResource(R.drawable.shape2);
                rbSetting.setBackgroundResource(R.drawable.shape2);
                switch (checkdId){
                    case R.id.radioHome:
                        fragmentTransaction.show(mFragments[0]).commit();
                        //home按钮处于点击状态
                        rbHome.setBackgroundResource(R.drawable.shape3);
                        break;
                    case R.id.radioFunc:
                        fragmentTransaction.show(mFragments[1]).commit();
                        //function按钮处于点击状态
                        rbFunc.setBackgroundResource(R.drawable.shape3);
                        break;
                    case R.id.radioSetting:
                        fragmentTransaction.show(mFragments[2]).commit();
                        //setting按钮处于点击状态
                        rbSetting.setBackgroundResource(R.drawable.shape3);
                        break;
                    default:
                        break;
                }
            }
        });


    }
}
