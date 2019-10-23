package com.example.myapp1;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RateGridViewActivity extends AppCompatActivity implements Runnable{
    private static final String TAG = "RateGridViewActivity";
    Handler handler;
    private ArrayList<HashMap<String,String>> listItems;
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_grid_view);

        Thread t = new Thread(this); //this表示当前接口Runable的run()方法，定义了一个线程t
        t.start();
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (msg.what == 7) {
                    ArrayList<HashMap<String,String>> listItems1 = (ArrayList<HashMap<String,String>>)msg.obj;
                    GridView listView = (GridView)findViewById(R.id.rategrid);
                    myAdapter = new MyAdapter(RateGridViewActivity.this , R.layout.list_item , listItems1);
                    listView.setAdapter(myAdapter);
                }

            }
        };
    }

    @Override
    public void run() {
        List<String> list1 = new ArrayList<String>();
        Document doc = null;
        listItems = new ArrayList<HashMap<String, String>>();

        try {
            doc = Jsoup.connect("http://www.usd-cny.com/bankofchina.htm").get();
            Log.i(TAG, "run: " + doc.title());
            //获取table中的数据
            Elements tables = doc.getElementsByTag("table");
            Element table1 = tables.get(0);
            Elements tds = table1.getElementsByTag("td");
            for(int i=0;i<tds.size();i+=6){
                Element td1 = tds.get(i);
                Element td2 = tds.get(i+5);
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("itemTitle" , td1.text());
                map.put("itemDetail" , td2.text());
                listItems.add(map);
                Log.i(TAG, "run: listItem" + td1.text() + "-->" + td2.text());
//                Log.i(TAG, "run: text=" + td1.text() + "-->" + td2.text());
//                list1.add(td1.text() + "-->" + td2.text());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Message msg = handler.obtainMessage(7);//从handler的消息队列中取出一个message
        msg.obj = listItems;
        handler.sendMessage(msg);
    }
}
