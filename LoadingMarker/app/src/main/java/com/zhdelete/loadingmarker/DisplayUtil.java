package com.zhdelete.loadingmarker;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * =====================================
 * 作    者:  ZHDelete
 * 创建日期:   2017/6/28
 * 描    述:
 * =====================================
 */

public class DisplayUtil {

    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.heightPixels;
    }
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.widthPixels;
    }
}
