package com.example.myapp1;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RateListActivity extends ListActivity implements Runnable{
    private static final String TAG = "RateListActivity";
    Handler handler;
    private ArrayList<HashMap<String,String>> listItems;
    private SimpleAdapter listItemAdapter;

    //创造页面，加载页面布局等时调用的方法，包括子进程
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //因为是自带的布局，所以不需要下面的一行语句
//        setContentView(R.layout.activity_rate_list);

        Thread t = new Thread(this); //this表示当前接口Runable的run()方法，定义了一个线程t
        t.start();

        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if(msg.what==7){
                    ArrayList<HashMap<String,String>> listItems1 = (ArrayList<HashMap<String,String>>)msg.obj;
                    listItemAdapter = new SimpleAdapter(RateListActivity.this , listItems1 , R.layout.list_item , new String[]{"itemTitle" , "itemDetail"} , new int[]{R.id.itemTitle , R.id.itemDetail});
                    setListAdapter(listItemAdapter);
                }//子进程号为7时，动态读取网页汇率信息添加进HashMap中
            }
        };
    }

    //子进程具体实现代码，动态读取网页的汇率信息并且添加到listItems中
    @Override
    public void run() {
        List<String> list1 = new ArrayList<String>();
        listItems = new ArrayList<HashMap<String, String>>();
        Document doc = null;
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
                map.put("itemTitle" , td1.text() + "的Rate: ");
                map.put("itemDetail" , td2.text());
                listItems.add(map);
                Log.i(TAG, "run: listItem" + td1.text() + "-->" + td2.text());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Message msg = handler.obtainMessage(7);//从handler的消息队列中取出一个message
        msg.obj = listItems;
        handler.sendMessage(msg);
    }
}
