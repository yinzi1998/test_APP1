package com.example.myapp1;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.myapp1.R.id.editText9;

public class ConfigActivity extends AppCompatActivity {
    private static final String TAG = "ConfigActivity";
    EditText text_dollar = null;
    EditText text_euro = null;
    EditText text_won = null;
    Button btn12 = null;
    float get_dollar = 0;
    float get_euro = 0;
    float get_won = 0;

    //加载页面时调用的方法
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        text_dollar = findViewById(editText9);
        text_euro = findViewById(R.id.editText12);
        text_won = findViewById(R.id.editText13);
        btn12 = findViewById(R.id.button12);


        SharedPreferences sp3 = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        get_dollar = sp3.getFloat("dollar_rate",0.1406f);
        get_euro = sp3.getFloat("euro_rate",0.1276f);
        get_won = sp3.getFloat("won_rate",167.8471f);

        text_dollar.setText("Dollar rate: " + String.format("%.4f",get_dollar));
        text_euro.setText("Euro rate: " + String.format("%.4f",get_euro));
        text_won.setText("Won rate: " + String.format("%.4f",get_won));

        Log.i(TAG,"openOne: dollar_rate=" + get_dollar);
        Log.i(TAG,"openOne: euro_rate=" + get_euro);
        Log.i(TAG,"openOne: won_rate=" + get_won);

    }

    //汇率配置config界面的方法
    public void config(View view){
        int id = view.getId();
        if(id == btn12.getId()){
            String dollar_str_new = text_dollar.getText().toString();
            String euro_str_new = text_euro.getText().toString();
            String won_str_new = text_won.getText().toString();

            dollar_str_new = dollar_str_new.replace("Dollar rate: ","");
            euro_str_new = euro_str_new.replace("Euro rate: ","");
            won_str_new = won_str_new.replace("Won rate: ","");

            float dollar_rate_new = Float.parseFloat(dollar_str_new);
            float euro_rate_new = Float.parseFloat(euro_str_new);
            float won_rate_new = Float.parseFloat(won_str_new);

            //通过XML文件传数据
            SharedPreferences sp1 = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
            SharedPreferences.Editor ed1 = sp1.edit();
            ed1.putFloat("dollar_rate",dollar_rate_new);
            ed1.putFloat("euro_rate",euro_rate_new);
            ed1.putFloat("won_rate",won_rate_new);
            ed1.apply();

            Intent main = new Intent(this,MainActivity.class);
            setResult(1,main);
            finish();
        }//保存修改的汇率

    }
}
