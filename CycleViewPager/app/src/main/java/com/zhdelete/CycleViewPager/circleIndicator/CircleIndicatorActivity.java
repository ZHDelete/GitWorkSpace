package com.zhdelete.CycleViewPager.circleIndicator;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.zhdelete.CycleViewPager.R;
import com.zhdelete.CycleViewPager.indicator.DisplayUtils;

/**
 * =====================================
 * 作    者:  ZHDelete
 * 创建日期:   2017/6/17
 * 描    述:
 * =====================================
 */

public class CircleIndicatorActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private IndicatorVPAdatpter mAdapter;

    private CircleIndicatorView indicator0,indicator1,indicator2, indicator3;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circleindicator);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        indicator0 = (CircleIndicatorView) findViewById(R.id.indicator_0);
        indicator1 = (CircleIndicatorView) findViewById(R.id.indicator_1);
        indicator2 = (CircleIndicatorView) findViewById(R.id.indicator_2);
        //代码设置属性
        indicator3 = (CircleIndicatorView) findViewById(R.id.indicator_3);

        mAdapter = new IndicatorVPAdatpter();
        mViewPager.setAdapter(mAdapter);

        indicator0.setupWithViewPager(mViewPager);
        indicator1.setupWithViewPager(mViewPager);
        indicator2.setupWithViewPager(mViewPager);
        indicator3.setupWithViewPager(mViewPager);

        indicator3.setRadius(DisplayUtils.dpToPx(15));
        indicator3.setBorderWidth(DisplayUtils.dpToPx(5));

        indicator3.setTextColor(Color.WHITE);
        indicator3.setDotSelectColor(Color.parseColor("#FFBB50"));
        indicator3.setDotNormalColor(Color.parseColor("#e38a7c"));
        indicator3.setSpace(DisplayUtils.dpToPx(10));
        indicator3.setFillMode(CircleIndicatorView.FillMode.LETTER);
        indicator3.setEnableClickSwitch(true);
        indicator3.setupWithViewPager(mViewPager);
        indicator3.setOnIndicatorClickListener(new CircleIndicatorView.OnIndicatorClickListener() {
            @Override
            public void onSelected(int position) {
                Log.d("CircleIndicatorActivity", "indicator3->position:" + position);
            }
        });
    }
}
