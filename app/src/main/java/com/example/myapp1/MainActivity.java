package com.example.myapp1;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    double dollar_rate = 0.1406;
    double euro_rate = 0.1276;
    double won_rate = 167.8471;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_3_1_3);
        /**btn.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {

        }
        });**/
    }//加载页面时调用的方法

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && requestCode == 1){
            Bundle bdl = data.getExtras();
            dollar_rate = bdl.getDouble("dollar_rate_new_key",dollar_rate);
            euro_rate = bdl.getDouble("euro_rate_new_key",euro_rate);
            won_rate = bdl.getDouble("won_rate_new_key",won_rate);
        }
    } //接受数据的方法

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_1,menu);
        return true;
    }//加载菜单项的方法

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.item1){
            Intent config = new Intent(this,ConfigActivity.class);
            Bundle bdl = new Bundle();
            bdl.putDouble("dollar_rate_key",dollar_rate);
            bdl.putDouble("euro_rate_key",euro_rate);
            bdl.putDouble("won_rate_key",won_rate);
            config.putExtras(bdl);

            Log.i(TAG,"openOne: dollar_rate=" + dollar_rate);
            Log.i(TAG,"openOne: euro_rate=" + euro_rate);
            Log.i(TAG,"openOne: won_rate=" + won_rate);

            startActivityForResult(config,1);
        }//菜单项1
        if(item.getItemId() == R.id.item2){
            TextView output = findViewById(R.id.textView8);
            output.setText("你打开了Item2");
        }//菜单项2
        if(item.getItemId() == R.id.item3){
            TextView output = findViewById(R.id.textView8);
            output.setText("你打开了Item3");
        }//菜单项3
        return super.onOptionsItemSelected(item);
    }//菜单项的处理事件

    public void money(View view) {
        EditText input = findViewById(R.id.editText8);
        String str_rmb = input.getText().toString();
        TextView output = findViewById(R.id.textView8);
        Button btn8 = findViewById(R.id.button8);
        Button btn9 = findViewById(R.id.button9);
        Button btn10 = findViewById(R.id.button10);
        Button btn11 = findViewById(R.id.button11);

        int id1 = view.getId();
        if (id1 == btn8.getId()) {
            if (str_rmb.length() == 0 || str_rmb == null) {
                output.setText("请先输入人民币金额！");
            } else {
                char[] ch_rmb = str_rmb.toCharArray();
                int dian = 0;
                int symbol = 0;
                for (int i = 0; i < ch_rmb.length; i++) {
                    if (i == 0 && ch_rmb[i] == '.') {
                        symbol = 1;
                        break;
                    }
                    if ((ch_rmb[i] < 48 || ch_rmb[i] > 57) && ch_rmb[i] != '.') {
                        symbol = 1;
                        break;
                    }
                    if (i != 0 && ch_rmb[i] == '.') {
                        dian++;
                    }
                }
                if (symbol == 0 && dian <= 1) {

                    double rmb = Double.parseDouble(str_rmb);
                    double dollar = rmb * dollar_rate;
                    String str=String.valueOf(dollar);
                    output.setText(str);
                } else {
                    output.setText("请输入正确格式的金额！");
                }
            }
        }//美元兑换
        if (id1 == btn9.getId()) {
            if (str_rmb.length() == 0 || str_rmb == null) {
                output.setText("请先输入人民币金额！");
            } else {
                char[] ch_rmb = str_rmb.toCharArray();
                int dian = 0;
                int symbol = 0;
                for (int i = 0; i < ch_rmb.length; i++) {
                    if (i == 0 && ch_rmb[i] == '.') {
                        symbol = 1;
                        break;
                    }
                    if ((ch_rmb[i] < 48 || ch_rmb[i] > 57) && ch_rmb[i] != '.') {
                        symbol = 1;
                        break;
                    }
                    if (i != 0 && ch_rmb[i] == '.') {
                        dian++;
                    }
                }
                if (symbol == 0 && dian <= 1) {
                    double rmb = Double.parseDouble(str_rmb);
                    double euro = rmb * euro_rate;
                    String str=String.valueOf(euro);
                    output.setText(str);
                } else {
                    Toast.makeText(this,"请输入正确格式的金额",Toast.LENGTH_SHORT).show();
                    output.setText("请输入正确格式的金额！");
                }
            }
        }//欧元兑换
        if (id1 == btn10.getId()) {
            if (str_rmb.length() == 0 || str_rmb == null) {
                Toast.makeText(this,"清闲输入人民币金额",Toast.LENGTH_SHORT).show();
                //output.setText("请先输入人民币金额！");
            } else {
                char[] ch_rmb = str_rmb.toCharArray();
                int dian = 0;
                int symbol = 0;
                for (int i = 0; i < ch_rmb.length; i++) {
                    if (i == 0 && ch_rmb[i] == '.') {
                        symbol = 1;
                        break;
                    }
                    if ((ch_rmb[i] < 48 || ch_rmb[i] > 57) && ch_rmb[i] != '.') {
                        symbol = 1;
                        break;
                    }
                    if (i != 0 && ch_rmb[i] == '.') {
                        dian++;
                    }
                }
                if (symbol == 0 && dian <= 1) {
                    double rmb = Double.parseDouble(str_rmb);
                    double won = rmb * won_rate;
                    String str=String.valueOf(won);
                    output.setText(str);
                } else {
                    output.setText("请输入正确格式的金额！");
                }
            }
        }//韩元兑换
        if (id1 == btn11.getId()) {
            Intent config = new Intent(this,ConfigActivity.class);
            Bundle bdl = new Bundle();
            bdl.putDouble("dollar_rate_key",dollar_rate);
            bdl.putDouble("euro_rate_key",euro_rate);
            bdl.putDouble("won_rate_key",won_rate);
            config.putExtras(bdl);

            Log.i(TAG,"openOne: dollar_rate=" + dollar_rate);
            Log.i(TAG,"openOne: euro_rate=" + euro_rate);
            Log.i(TAG,"openOne: won_rate=" + won_rate);

            startActivityForResult(config,1);
        }//汇率配置界面


    }//汇率转换


    public void point(View view) {
        Button btn18 = findViewById(R.id.button18);
        Button btn19 = findViewById(R.id.button19);
        Button btn20 = findViewById(R.id.button20);
        Button btn21 = findViewById(R.id.button21);
        Button btn23 = findViewById(R.id.button23);
        Button btn24 = findViewById(R.id.button24);
        Button btn25 = findViewById(R.id.button25);
        //EditText text_num=findViewById(R.id.editText5);
        EditText textA_num = findViewById(R.id.editText10);
        EditText textB_num = findViewById(R.id.editText11);
        //String str_num=text_num.getText().toString();
        String strA_num = textA_num.getText().toString();
        String strB_num = textB_num.getText().toString();
        //int num=Integer.parseInt(str_num);
        int numA = Integer.parseInt(strA_num);
        int numB = Integer.parseInt(strB_num);
        int id = view.getId();
        if (id == btn19.getId()) {
            numA = numA + 1;
            String str = String.valueOf(numA);
            textA_num.setText(str);
        }
        if (id == btn23.getId()) {
            numB = numB + 1;
            String str = String.valueOf(numB);
            textB_num.setText(str);
        }
        if (id == btn20.getId()) {
            numA = numA + 2;
            String str = String.valueOf(numA);
            textA_num.setText(str);
        }
        if (id == btn24.getId()) {
            numB = numB + 2;
            String str = String.valueOf(numB);
            textB_num.setText(str);
        }
        if (id == btn21.getId()) {
            numA = numA + 3;
            String str = String.valueOf(numA);
            textA_num.setText(str);
        }
        if (id == btn25.getId()) {
            numB = numB + 3;
            String str = String.valueOf(numB);
            textB_num.setText(str);
        }
        if (id == btn18.getId()) {
            textA_num.setText("0");
            textB_num.setText("0");
        }

    } //course3_1_2两支队伍计分


    /*public void printText(View view) {
        Button btn3=findViewById(R.id.button3);
        Log.i(TAG,"onClick: ");
        int id=view.getId();
        if(id==btn3.getId()) {
            EditText input=findViewById(R.id.editText4);
            String str_tem=input.getText().toString();
            TextView tx1 = findViewById(R.id.textView6);
            int dian=0;
            if(str_tem.length()==0 || str_tem==null){tx1.setText("请先输入温度");}
            else{
                char[] ch_tem=str_tem.toCharArray();
                for(int i=0;i<ch_tem.length;i++){
                    if(i==0 && ch_tem[i]=='.'){tx1.setText("请输入正确格式的温度"); break;}
                    else if(ch_tem[i]=='.'){dian++;}
                    else if(ch_tem[i]<48 || ch_tem[i]>57){tx1.setText("请输入正确格式的温度"); break;}
                }
                if(dian>1){tx1.setText("请输入正确格式的温度");}
                else{
                    double tem=Double.parseDouble(str_tem);
                    double huashi=(tem/5)*9+32;
                    String str_huashi=String.valueOf(huashi);
                    str_huashi=str_huashi.substring(0,11);
                    tx1.setText("结果："+str_huashi);
                }
            }
        }
    }//course_3_1_1温度转为华氏度*/
}
