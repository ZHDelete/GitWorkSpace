package com.zhdelete.CycleViewPager.circleIndicator;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zhdelete.CycleViewPager.R;

/**
 * =====================================
 * 作    者:  ZHDelete
 * 创建日期:   2017/6/17
 * 描    述:
 * =====================================
 */

public class IndicatorVPAdatpter extends PagerAdapter {
    private static final int RES[] =
            new int[]{
                    R.mipmap.test_bunny_1,
                    R.mipmap.test_bunny_2,
                    R.mipmap.test_bunny_3,
                    R.mipmap.test_bunny_4,
    };


    @Override
    public int getCount() {
        return RES.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = LayoutInflater.from(container.getContext())
                .inflate(R.layout.indicator_vp_page, container, false);
        ImageView iv = (ImageView) itemView.findViewById(R.id.indicator_vp_page_iv);
        iv.setImageResource(RES[position]);
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
