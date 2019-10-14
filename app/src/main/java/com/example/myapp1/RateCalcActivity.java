package com.example.myapp1;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RateCalcActivity extends AppCompatActivity {
    private static final String TAG = "rateCalcActivity";
    float rate = 0f;
    String title = "";
    EditText input2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_calc);
        rate = getIntent().getFloatExtra("rate", 0f);
        title = getIntent().getStringExtra("title");

        Log.i(TAG, "onCreate: 传入的title= " + title);
        Log.i(TAG, "onCreate: 传入的rate= " + rate);
        ((TextView)findViewById(R.id.title2)).setText(title);
        input2 = findViewById(R.id.input2);

        //对输入框的内容添加监听，只要输入改变了了就计算
        input2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                TextView show2 = findViewById(R.id.show2);
                if(editable.length()>0){
                    float rate_new = 100f/rate;
                    String rmbStr = editable.toString();
                    float rmb = Float.parseFloat(rmbStr);
                    show2.setText(rmbStr + "人民币转换为：" + rmb*rate_new + title);
                }
            }
        });

    }
}
