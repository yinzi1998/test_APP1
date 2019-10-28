package com.example.myapp1;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class CSVoteActivity extends AppCompatActivity {

    private static final String TAG = "CSVoteActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cs_vote);
    }

    //投票按钮的事件
    public void onClick(View btn){
        if(btn.getId() == R.id.btn_approve){
            new VoteTask().execute("赞成");
        }else if(btn.getId() == R.id.btn_oppose){
            new VoteTask().execute("反对");
        }else{
            new VoteTask().execute("弃权");
        }
    }

    private String doVote(String voteStr){
        String retStr = "";
        Log.i(TAG, "doVote: voteStr= " + voteStr);


        try {
            //存储封装好的请求体信息
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("r=").append(URLEncoder.encode(voteStr, "utf-8"));

            byte[] data = stringBuffer.toString().getBytes();
            String urlPath = "http://localhost:8080/";
            URL url = new URL(urlPath);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            //设置连接超时时间
            httpURLConnection.setConnectTimeout(3000);
            //打开输入流，以便从服务器获取数据
            httpURLConnection.setDoInput(true);
            //打开输出流，以便向服务器提交数据
            httpURLConnection.setDoOutput(true);
            //设置以post方法提交数据
            httpURLConnection.setRequestMethod("POST");
            //使用post方式不能使用缓存
            httpURLConnection.setUseCaches(false);
            //设置请求主题的类型是文本类型
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //设置请求体的长度
            httpURLConnection.setRequestProperty("Content-Length", String.valueOf(data.length));
            //获得输出流，向服务器写入数据
            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(data);
            //服务器处理写数据之后，获得服务器的响应码
            int response = httpURLConnection.getResponseCode();
            if(response == HttpURLConnection.HTTP_OK){
                InputStream inputStream = httpURLConnection.getInputStream();
                //处理服务器响应的结果
                retStr = inputStream2String(inputStream);
                Log.i(TAG, "doVote: 服务器响应结果retStr= " + retStr);
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return retStr;

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

    //内部类
    private class VoteTask extends AsyncTask<String, Void, String> {

        //后台功能实现的函数，返回的值直接自动传给onPostExecute
        @Override
        protected String doInBackground(String... strings) {
            for(String s : strings){
                Log.i(TAG, "doInBackground: 传入的参数是= " + s);
            }
            String ret = doVote(strings[0]);
            return ret;
        }

        //执行完后台之后，在前端布局的显示
        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(CSVoteActivity.this, s, Toast.LENGTH_SHORT).show();
        }
    }
}
