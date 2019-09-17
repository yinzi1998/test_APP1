package com.example.myapp1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_2_2_1);
        //TextView tx1 = findViewById(R.id.textView6);
        //tx1.setText("write with java");

        //EditText input=findViewById(R.id.editText4);
       // String str=input.getText().toString();

        //Button btn=findViewById(R.id.button3);
        //btn.setOnClickListener(this);
        /**btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });**/
    }


    public void printText(View view) {
        Button btn3=findViewById(R.id.button3);
        Button btn4=findViewById(R.id.button4);
        Log.i(TAG,"onClick: ");
        int id=view.getId();
        if(id==btn3.getId()) {
            EditText input = findViewById(R.id.editText4);
            String str = input.getText().toString();
            TextView tx1 = findViewById(R.id.textView6);
            tx1.setText(str+"button3");
        }
        else if(id==btn4.getId()) {
            EditText input = findViewById(R.id.editText4);
            String str = input.getText().toString();
            TextView tx1 = findViewById(R.id.textView6);
            tx1.setText(str+"button4");
        }

    }
}
