package com.zhdelete.CycleViewPager;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by ZHDelete on 2016/8/29.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);

    }
}
