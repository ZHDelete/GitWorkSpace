package com.zhdelete.CycleViewPager.mzbanner;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

/**
 * =====================================
 * 作    者:  ZHDelete
 * 创建日期:   2017/6/14
 * 描    述:
 * =====================================
 */

public class CustomTransformer implements ViewPager.PageTransformer {
    private static final String TAG = "CustomTransformer";

    private static final float DEF_SCALE = 0.5F;

    @Override
    public void transformPage(View page, float position) {
        Log.d(TAG, "position: " + position);
        if (position < -1f) { //-无穷 -> -1
            Log.d(TAG, "transformPage: position < -1 -> " + position);
//            page.setScaleY(DEF_SCALE);
        } else if (position < 0) {//左滑 0 -> -1 右滑 -1 -> 0
            Log.d(TAG, "transformPage: position < 0 -> " + position);
            float scale = 1f + 0.5f * position;
            page.setScaleY(scale);

        } else if (position <= 1) {//左滑 1 -> 0 右滑 0 -> 1
            Log.d(TAG, "transformPage: position < 1 -> " + position);
            float scale = 1f - 0.5f * position;
            page.setScaleY(scale);

        } else {
            Log.d(TAG, "transformPage: position > 1 -> " + position);
//                page.setScaleY(DEF_SCALE);
        }


//        if(position < -1.0F) {
//            page.setScaleY(0.9F);
//        } else if(position <= 1.0F) {
//            float scale = Math.max(0.9F, 1.0F - Math.abs(position));
//            page.setScaleY(scale);
//        } else {
//            page.setScaleY(0.9F);
//        }

    }
}
