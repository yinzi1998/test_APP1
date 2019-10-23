package com.example.myapp1;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements Runnable{
    private static final String TAG = "MainActivity";
    float dollar_rate;
    float euro_rate;
    float won_rate;
    Handler handler;
    private String updateDate = "";

    //创造页面，加载页面布局等时调用的方法，包括子进程
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_3_1_3);
        Log.i(TAG, "onCreate: ");

        //判断是否要更新
        SharedPreferences sp = getSharedPreferences("myrate",Activity.MODE_PRIVATE);
        updateDate = sp.getString("update_date","");
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        final String todayStr = sdf.format(today);
        if(!todayStr.equals(updateDate)){
            //子进程响应
            Thread t = new Thread(this); //this表示当前接口Runable的run()方法，定义了一个线程t
            t.start();

            Log.i(TAG, "onCreate: myrate中日期 " + updateDate);
            Log.i(TAG, "onCreate: 今天日期 " + todayStr);
            Log.i(TAG, "onCreate: 需要更新");
        }else{
            Log.i(TAG, "onCreate: myrate中日期 " + updateDate);
            Log.i(TAG, "onCreate: 今天日期 " + todayStr);
            Log.i(TAG, "onCreate: 不需要更新");
        }

        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if(msg.what == 5){
                    Bundle bdl = (Bundle) msg.obj;
                    dollar_rate = bdl.getFloat("web_dollar_rate");
                    euro_rate = bdl.getFloat("web_euro_rate");
                    won_rate = bdl.getFloat("web_won_rate");

                    Log.i(TAG,"handleMessage: 网页获取dollar_rate " + dollar_rate);
                    Log.i(TAG,"handleMessage: 网页获取euro_rate " + euro_rate);
                    Log.i(TAG,"handleMessage: 网页获取won_rate " + won_rate);

                    //汇率和日期写到myrate.xml中
                    SharedPreferences sp1 = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor ed1 = sp1.edit();
                    ed1.putFloat("dollar_rate",dollar_rate);
                    ed1.putFloat("euro_rate",euro_rate);
                    ed1.putFloat("won_rate",won_rate);
                    ed1.putString("update_date",todayStr);
                    ed1.apply();

                    Toast.makeText(MainActivity.this,"今日汇率已更新",Toast.LENGTH_SHORT).show();
                }//子进程号为5时，动态读取网页汇率信息并且传到myrate.xml中
            }//改写父类Hander的方法
        };


    }

    //重新激活，如电话结束后
    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");
    }//

    //用户唤醒，onStart之后，需要用户唤醒才可以继续运行
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart: ");
    }

    //暂停，如有一个电话来了
    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
    }

    //onPause之后就是onStop
    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: ");
    }

    //销毁，释放资源，应用完全死亡
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }

    //处理暂停时，如旋转时，数据丢失的问题
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
//        Log.i(TAG, "onSaveInstanceState: ");
//        //ab两队计分的数据存入bundle中
//        String scorea = ((EditText)findViewById(R.id.editText10)).getText().toString();
//        String scoreb = ((EditText)findViewById(R.id.editText11)).getText().toString();
//        outState.putString("teama_score",scorea);
//        outState.putString("teamb_score",scoreb);
    }

    //还原暂停后的数据，和onSaveInstanceState方法相对应
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        Log.i(TAG, "onRestoreInstanceState: ");
//        String scorea = savedInstanceState.getString("teama_score");
//        String scoreb = savedInstanceState.getString("teamb_score");
//        ((EditText)findViewById(R.id.editText10)).setText(scorea);
//        ((EditText)findViewById(R.id.editText11)).setText(scoreb);
    }

    //接受跳转界面的finish()数据的方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && requestCode == 1){
            SharedPreferences sp2 = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
            dollar_rate = sp2.getFloat("dollar_rate",0.1406f);
            euro_rate = sp2.getFloat("euro_rate",0.1276f);
            won_rate = sp2.getFloat("won_rate",167.8471f);
        }
    }

    //加载菜单项的方法
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_1,menu);
        return true;
    }

    //菜单项的触发处理事件
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //菜单项1，打开RateListActivity
        if(item.getItemId() == R.id.item1){
            //打开从网页动态读取的列表窗口
            Intent list = new Intent(this,RateListActivity.class);
            startActivity(list);
            Log.i(TAG, "onOptionsItemSelected: 菜单打开RateListActivity");
        }
        //菜单项2，打开ConfigActivity
        if(item.getItemId() == R.id.item2){
            Intent config = new Intent(this,ConfigActivity.class);
            startActivity(config);
            Log.i(TAG, "onOptionsItemSelected: 菜单打开ConfigActivity");
        }
        //菜单项3，打开RateListViewActivity
        if(item.getItemId() == R.id.item3){
            //打开通过ListView组件从网页动态读取的列表窗口
            Intent listview = new Intent(this,RateListViewActivity.class);
            startActivity(listview);
            Log.i(TAG, "onOptionsItemSelected: 菜单打开RateListViewActivity");
        }
        if(item.getItemId() == R.id.item4){
            //打开通过ListView组件从网页动态读取的列表窗口
            Intent gridview = new Intent(this,RateGridViewActivity.class);
            startActivity(gridview);
            Log.i(TAG, "onOptionsItemSelected: 菜单打开RateGridViewActivity");
        }
        if(item.getItemId() == R.id.item5){
            //打开通过数据库读取的列表窗口
            Intent databaseview = new Intent(this,RateDataBaseActivity.class);
            startActivity(databaseview);
            Log.i(TAG, "onOptionsItemSelected: 菜单打开RateDataBaseActivity");

        }
        return super.onOptionsItemSelected(item);
    }

    //汇率的按钮触发时间转换
    public void money(View view) {
        EditText input = findViewById(R.id.editText8);
        String str_rmb = input.getText().toString();
        TextView output = findViewById(R.id.textView8);
        Button btn8 = findViewById(R.id.button8);
        Button btn9 = findViewById(R.id.button9);
        Button btn10 = findViewById(R.id.button10);
        Button btn11 = findViewById(R.id.button11);

        SharedPreferences sp2 = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        dollar_rate = sp2.getFloat("dollar_rate",0.1406f);
        euro_rate = sp2.getFloat("euro_rate",0.1276f);
        won_rate = sp2.getFloat("won_rate",167.8471f);

        int id1 = view.getId();
        //美元兑换
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

                    float rmb = Float.parseFloat(str_rmb);
                    float dollar = rmb * dollar_rate;
                    String str=String.valueOf(dollar);
                    output.setText(str);
                } else {
                    output.setText("请输入正确格式的金额！");
                }
            }
        }
        //欧元兑换
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
                    float rmb = Float.parseFloat(str_rmb);
                    float euro = rmb * euro_rate;
                    String str=String.valueOf(euro);
                    output.setText(str);
                } else {
                    Toast.makeText(this,"请输入正确格式的金额",Toast.LENGTH_SHORT).show();
                    output.setText("请输入正确格式的金额！");
                }
            }
        }
        //韩元兑换
        if (id1 == btn10.getId()) {
            if (str_rmb.length() == 0 || str_rmb == null) {
                Toast.makeText(this,"请输入人民币金额",Toast.LENGTH_SHORT).show();
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
                    Float rmb = Float.parseFloat(str_rmb);
                    float won = rmb * won_rate;
                    String str=String.valueOf(won);
                    output.setText(str);
                } else {
                    output.setText("请输入正确格式的金额！");
                }
            }
        }
        //转到汇率配置界面
        if (id1 == btn11.getId()) {
            Intent config = new Intent(this,ConfigActivity.class);

//用bundle转发数据，但是已经写道myrate.xml中了，不需要了
//            Bundle bdl = new Bundle();
//            bdl.putDouble("dollar_rate_key",dollar_rate);
//            bdl.putDouble("euro_rate_key",euro_rate);
//            bdl.putDouble("won_rate_key",won_rate);
//            config.putExtras(bdl);
            Log.i(TAG,"open ConfigAvtivity");
            startActivityForResult(config,1);
        }
    }

    //course3_1_2两支队伍计分
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

    }

    //course_3_1_1温度转为华氏度
    public void printText(View view) {
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
    }

    //子线程具体实现代码，读取网页的汇率信息并添加到bundle中
    @Override
    public void run() {
        Log.i(TAG,"run: run().....");

        //用于保存从网页中获取的汇率
        Bundle bundle = new Bundle();

//用URL，不用jsoup的网页读取方法
//        msg.what = 5;
//        URL url = null;
//        try {
//            url = new URL("http://www.usd-cny.com/bankofchina.htm");
//            HttpURLConnection http = (HttpURLConnection) url.openConnection();
//            Log.i(TAG, "run: http.getResponseCode()=" + http.getResponseCode());
//            Log.i(TAG, "run: getResponseMessage=" + http.getResponseMessage());
//
//            InputStream in = http.getInputStream();
//            String html = inputStream2String(in);
//            //Log.i(TAG, "run: html=" + html);
//            //Document doc = Jsoup.parse(html);

//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

       //用jsoup方法读取网页内容
        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.usd-cny.com/bankofchina.htm").get();
            Log.i(TAG, "run: " + doc.title());
            //获取table中的数据
            Elements tables = doc.getElementsByTag("table");
            Element table1 = tables.get(0);
            //获取td中的数据
            Elements tds = table1.getElementsByTag("td");
            for(int i=0;i<tds.size();i+=6){
                Element td1 = tds.get(i);
                Element td2 = tds.get(i+5);
                Log.i(TAG, "run: text=" + td1.text() + "-->" + td2.text());

                if("美元".equals(td1.text())){
                    bundle.putFloat("web_dollar_rate",100f/Float.parseFloat(td2.text()));
                }else if("韩元".equals(td1.text())){
                    bundle.putFloat("web_won_rate",100f/Float.parseFloat(td2.text()));
                }else if("欧元".equals(td1.text())){
                    bundle.putFloat("web_euro_rate",100f/Float.parseFloat(td2.text()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Message msg = handler.obtainMessage(5);//从handler的消息队列中取出一个message
        msg.obj = bundle;
        handler.sendMessage(msg);
    }

    //把InputStream转换成String的方法
    private String inputStream2String(InputStream inputStream) throws IOException {
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream, "gb2312");
        while(true){
            int rsz = in.read(buffer,0,buffer.length);
            if(rsz<0) break;
            out.append(buffer,0,rsz);
        }
        return out.toString();

    }
}
