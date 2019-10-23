package com.example.myapp1;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class ShakeActivity extends AppCompatActivity {

    private static final String TAG = "ShakeActivity";
    private TextView textView;
    private ImageView imageView;
    private static String strs[] = {"石头", "剪刀", "布"};
    private static int pics[] = {R.drawable.shitou, R.drawable.jiandao, R.drawable.bu};
    //定义传感器，用于传感器的管理
    private SensorManager sensorManager;
    //定义手机震动
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake);

        textView = findViewById(R.id.shake_text);
        imageView = findViewById(R.id.shake_image);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
    }

    //重力感应监听
    private SensorEventListener sensorEventListener = new SensorEventListener() {
        //传感器数据信息改变时执行的方法
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float[] values = sensorEvent.values;
            //x轴方向的重力加速度，向右为正
            float x = values[0];
            //y轴方向的重力加速度，向前为正
            float y = values[1];
            //z轴方向的重力加速度，向上为正
            float z = values[2];
            Log.i(TAG, "onSensorChanged: x= " + x + "y= " + y + "z= " + z);
            //不同手机厂商的medumValue不同，临界值
            int medumValue = 10;
            //一般这三个方向的重力加速度达到40就达到了手机摇晃的状态
            if(Math.abs(x)>medumValue || Math.abs(y)>medumValue || Math.abs(z)>medumValue){
                vibrator.vibrate(200);
                Message msg = new Message();
                msg.what = 10;
                handler.sendMessage(msg);
            }
        }

        //传感器精度发生变化时执行的方法
        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    //切换回前端，重新注册传感器使之生效
    @Override
    protected void onResume() {
        super.onResume();
        //注册监听器，让监听器再次生效
        //第一个参数是Listener，第二个参数是传感器类型，第三个参数是获得传感器数据信息的频率
        sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), sensorManager.SENSOR_DELAY_NORMAL);

    }

    //切换到后台的方法，取消传感器
    @Override
    protected void onStop() {
        super.onStop();
        //取消监听器
        if(sensorManager != null){
            sensorManager.unregisterListener(sensorEventListener);
        }
    }

    //处理SensorEventListener中发出的消息，设置相应的石头剪刀布
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.what == 10){
                Log.i(TAG, "handleMessage: 检测到摇晃，执行操作！");
                Random r = new Random();
                int num = Math.abs(r.nextInt()%3);
                textView.setText(strs[num]);
                imageView.setImageResource(pics[num]);
            }
        }
    };
}
