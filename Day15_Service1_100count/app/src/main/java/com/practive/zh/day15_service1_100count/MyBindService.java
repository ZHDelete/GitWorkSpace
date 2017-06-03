package com.practive.zh.day15_service1_100count;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

/**
 * Created by dllo on 16/1/27.
 */
public class MyBindService extends Service {



    private int test = 0;
    MyBinder myBinder = new MyBinder();
    private boolean isFinished;//判断线程是否需要停止

    public class MyBinder extends Binder {
        public int publishData() {
            return test;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        isFinished = false;//此时线程不需要停止

        new Thread(new Runnable() {
            @Override
            public void run() {//循环放到子线程里,为了不卡主线程,主UI不能卡过5s 否则:ANR
                while (test < 100 && !isFinished) {//思考一下,开始写if为啥不行,因为if 没循环
                    test++;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isFinished = true;//线程需要停止
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }
}
