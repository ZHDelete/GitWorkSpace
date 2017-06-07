package com.zhyim.gaussian_blur_header;

import android.app.Application;
import android.content.Context;

import com.bumptech.glide.Glide;

/**
 * =====================================
 * 作    者:  ZHDelete
 * 创建日期:   2017/6/6
 * 描    述:
 * =====================================
 */

public class App extends Application {
    private static Context _instance;


    @Override
    public void onCreate() {
        super.onCreate();
        _instance = this;
    }

    public static Context geApp() {
        return _instance;
    }
}
