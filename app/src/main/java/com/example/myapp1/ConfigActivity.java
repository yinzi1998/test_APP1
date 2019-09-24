package com.example.myapp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static com.example.myapp1.R.id.editText9;

public class ConfigActivity extends AppCompatActivity {
    private static final String TAG = "ConfigActivity";
    EditText text_dollar = null;
    EditText text_euro = null;
    EditText text_won = null;
    Button btn12 = null;
    double get_dollar = 0;
    double get_euro = 0;
    double get_won = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        text_dollar = findViewById(editText9);
        text_euro = findViewById(R.id.editText12);
        text_won = findViewById(R.id.editText13);
        btn12 = findViewById(R.id.button12);

        Intent intent = getIntent();
        Bundle bdl = intent.getExtras();
        get_dollar = bdl.getDouble("dollar_rate_key",0);
        get_euro = bdl.getDouble("euro_rate_key",0);
        get_won = bdl.getDouble("won_rate_key",0);

        text_dollar.setText("Dollar rate: " + String.format("%f",get_dollar));
        text_euro.setText("Euro rate: " + String.format("%f",get_euro));
        text_won.setText("Won rate: " + String.format("%f",get_won));

        Log.i(TAG,"openOne: dollar_rate=" + get_dollar);
        Log.i(TAG,"openOne: euro_rate=" + get_euro);
        Log.i(TAG,"openOne: won_rate=" + get_won);

    }

    public void config(View view){
        int id = view.getId();
        if(id == btn12.getId()){
            String dollar_str_new = text_dollar.getText().toString();
            String euro_str_new = text_euro.getText().toString();
            String won_str_new = text_won.getText().toString();

            dollar_str_new = dollar_str_new.replace("Dollar rate: ","");
            euro_str_new = euro_str_new.replace("Euro rate: ","");
            won_str_new = won_str_new.replace("Won rate: ","");

            double dollar_rate_new = Float.parseFloat(dollar_str_new);
            double euro_rate_new = Float.parseFloat(euro_str_new);
            double won_rate_new = Float.parseFloat(won_str_new);

            Intent main = new Intent(this,MainActivity.class);
            Bundle bdl = new Bundle();
            bdl.putDouble("dollar_rate_new_key",dollar_rate_new);
            bdl.putDouble("euro_rate_new_key",euro_rate_new);
            bdl.putDouble("won_rate_new_key",won_rate_new);
            main.putExtras(bdl);
            setResult(1,main);


            finish();

        }

    }
}
