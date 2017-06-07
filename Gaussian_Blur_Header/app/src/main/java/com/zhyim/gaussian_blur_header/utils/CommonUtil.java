package com.zhyim.gaussian_blur_header.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * =====================================
 * 作    者:  ZHDelete
 * 创建日期:   2017/6/6
 * 描    述:
 * =====================================
 */

public class CommonUtil {

    private CommonUtil() {}

    public static int[] getScreenSize(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width= outMetrics.widthPixels;
        int height = outMetrics.heightPixels;
        return new int[]{width, height};
    }

}
