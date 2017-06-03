package com.practive.zh.day15_service1_100count;

import android.app.IntentService;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    MyBindService.MyBinder myBinder;
    ServiceConnection connection;

    private Button bindBtn,passValueBtn,unBindBtn;
    private TextView showTv;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindBtn = (Button) findViewById(R.id.bind_button);
       // passValueBtn = (Button) findViewById(R.id.get_data_button);
        unBindBtn = (Button) findViewById(R.id.unbind_button);
        showTv = (TextView) findViewById(R.id.show_count_tv);

        bindBtn.setOnClickListener(this);
        //passValueBtn.setOnClickListener(this);
        unBindBtn.setOnClickListener(this);


        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                myBinder = (MyBindService.MyBinder) service;

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

                myBinder = null;
            }
        };

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                //刷新UI
                showTv.setText(String.valueOf(msg.arg1));//setText 传入String
                return false;
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bind_button:
                //绑定之后,Service的oncreat方法开始执行,那边开始数数了,
                //所以,如果不开始要值,得不到前边的数.,因此,应该吧传值按钮监听里的东西,移到这里
                Intent bindServiceIntent = new Intent(this,MyBindService.class);
                bindService(bindServiceIntent,connection,BIND_AUTO_CREATE);
                Log.d("MainActivity", "bind 成功");


                //写一个子线程,向服务要值.
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //循环,向服务要值;
                        while(true) {
                            //判断activity是否与Service连接上,没连接上时,不走下面代码
                            if (myBinder !=null){
                                //向服务要值
                                int i = myBinder.publishData();
                                Message msg = new Message();
                                msg.arg1 = i;
                                //将消息发送到主线程,开始发送UI,每发送一次,handler 的callback 被回调一次,setText一次
                                handler.sendMessage(msg);

                                if (i == 100) {
                                    //如果i = 100 就结束这个线程
                                    break;
                                }
                                try {
                                    Thread.sleep(200);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    }
                }).start();


                break;


            case R.id.unbind_button:
                myBinder = null;//手动将myBind对象置空,否则,已经解绑 但由于对象仍然存在,依旧可能得到值
                //置空对象,以便java 回收机制将堆内存对象回收
                unbindService(connection);
                break;
        }
    }

}
