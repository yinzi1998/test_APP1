package com.example.myapp1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class RateDataBaseActivity extends ListActivity implements Runnable, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    private static final String TAG = "RateDataBaseActivity";
    Handler handler;
    private ArrayList<HashMap<String,String>> listItems;
    private SimpleAdapter listItemAdapter;
    MyAdapter myAdapter;
    //用于获取日期来更新
    private  String updateDate = "";
    private String todayStr = "";

    //创造页面，加载页面布局等时调用的方法，包括子进程、列表事件监听
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //因为是自带的布局，所以不需要下面的一行语句
//        setContentView(R.layout.activity_rate_list);


        //判断是否要更新
        SharedPreferences sp = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        updateDate = sp.getString("update_date","");
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        todayStr = sdf.format(today);
        //需要通过子进程更新并且写入数据库
        if(!todayStr.equals(updateDate)){
            //子进程响应
            Thread t = new Thread(this); //this表示当前接口Runable的run()方法，定义了一个线程t
            t.start();

            RateManager manager = new RateManager(RateDataBaseActivity.this);
            listItems = new ArrayList<HashMap<String, String>>();

            for(RateItem i : manager.showListAll()){
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("itemTitle" , i.getCurName());
                map.put("itemDetail" , i.getCurRate());
                listItems.add(map);
            }
            myAdapter = new MyAdapter(RateDataBaseActivity.this , R.layout.list_item , listItems);
            setListAdapter(myAdapter);

            Log.i(TAG, "onCreate: myrate中日期 " + updateDate);
            Log.i(TAG, "onCreate: 今天日期 " + todayStr);
            Log.i(TAG, "onCreate: 需要更新，从网络中获取数据");
         //不需要更新，读取数据库
        }else{
            RateManager manager = new RateManager(RateDataBaseActivity.this);
            listItems = new ArrayList<HashMap<String, String>>();

            for(RateItem i : manager.showListAll()){
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("itemTitle" , i.getCurName());
                map.put("itemDetail" , i.getCurRate());
                listItems.add(map);
            }
            myAdapter = new MyAdapter(RateDataBaseActivity.this , R.layout.list_item , listItems);
            setListAdapter(myAdapter);

            Log.i(TAG, "onCreate: myrate中日期 " + updateDate);
            Log.i(TAG, "onCreate: 今天日期 " + todayStr);
            Log.i(TAG, "onCreate: 不需要更新，从数据库中获取数据");
        }

        //添加列表点击事件的监听
        getListView().setOnItemClickListener(this);
        //添加列表长按事件的监听
        getListView().setOnItemLongClickListener(this);
    }

    //子进程具体实现代码，动态读取网页的汇率信息，需要implements Runnable
    @Override
    public void run() {
        //listItems = new ArrayList<HashMap<String, String>>();
        List<RateItem> rateItemDB = new ArrayList<RateItem>();
        Document doc = null;
        RateManager manager = new RateManager(this);
        try {
            doc = Jsoup.connect("http://www.usd-cny.com/bankofchina.htm").get();
            Log.i(TAG, "run: " + doc.title());
            //获取table中的数据
            Elements tables = doc.getElementsByTag("table");
            Element table1 = tables.get(0);
            Elements tds = table1.getElementsByTag("td");

            //数据添加到数据库中
            for(int i=0;i<tds.size();i+=6){
                Element td1 = tds.get(i);
                Element td2 = tds.get(i+5);
                rateItemDB.add(new RateItem(td1.text(), td2.text()));
            }
            //删除所有数据并且添加到数据库中去
            manager.deleteAll();
            Log.i(TAG, "run: 已经删除数据库中所有数据");
            manager.addAll(rateItemDB);
            Log.i(TAG, "run: 已经将网络中数据添加到数据库中");
            //记录更新的日期写入myrate.xml中去
            SharedPreferences sp1 = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
            SharedPreferences.Editor ed1 = sp1.edit();
            ed1.putString("update_date",todayStr);
            ed1.apply();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //列表点击的触发事件，需要implements AdapterView.OnItemClickListener，跳转到RateClacActivity界面计算具体汇率
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Log.i(TAG, "onItemClick: 点击的id= " + id);

        //通过position获得数据
        HashMap<String, String> map = (HashMap<String, String>) getListView().getItemAtPosition(position);
        String titleStr = map.get("itemTitle");
        String detailStr = map.get("itemDetail");
        Log.i(TAG, "onItemClick: 通过position点击获得的title= " + titleStr);
        Log.i(TAG, "onItemClick: 通过position点击获得的Detail= " + detailStr);

        //通过view获得数据
        TextView title = view.findViewById(R.id.itemTitle);
        TextView detail = view.findViewById(R.id.itemDetail);
        String titleStr2 = title.getText().toString();
        String detailStr2 = detail.getText().toString();
        Log.i(TAG, "onItemClick: 通过view点击获得的title= " + titleStr2);
        Log.i(TAG, "onItemClick: 通过view点击获得的Detail= " + detailStr2);

        //打开新的界面 activity_rate_calc.xml，传入参数
        Intent rateCalc = new Intent(this , RateCalcActivity.class);
        rateCalc.putExtra("title", titleStr2);
        rateCalc.putExtra("rate", Float.parseFloat(detailStr2));
        startActivity(rateCalc);
    }

    //列表长按的触发事件，删除对应的item行
    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long id) {
        Log.i(TAG, "onItemLongClick: 长按删除的id= " + id);

        //构造对话框来删除item
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示").setMessage("请确认是否删除当前数据").setPositiveButton("是", new DialogInterface.OnClickListener() {
            //“是”按钮的点击事件
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.i(TAG, "onClick: 对话框确定按钮事件处理");
                Log.i(TAG, "onClick: 长按删除后的list长度= " + listItems.size());
                listItems.remove(position);
                myAdapter.notifyDataSetChanged();
            }
        })
                .setNegativeButton("否", null);
        builder.create().show();

        //false的时候，依旧触发点击事件；true的时候，不会触发点击事件
        return true;
    }
}
