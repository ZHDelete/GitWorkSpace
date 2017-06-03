package com.zhdelete.CycleViewPager.yanzhenjie;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.zhdelete.CycleViewPager.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZHDelete on 2016/8/30.
 */
public class SecActivity extends AppCompatActivity {
    private AutoPlayViewPager autoPlayViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sec);

        autoPlayViewPager = (AutoPlayViewPager) findViewById(R.id.sec_vp);
        List<Fragment> fragmentList = new ArrayList<>();
        GifFragment fragment = new GifFragment();
        fragmentList.add(new GifFragment());
        fragmentList.add(new GifFragment());
        fragmentList.add(new GifFragment());
        fragmentList.add(new GifFragment());
        fragmentList.add(new GifFragment());

        AutoPalyAdapter mAdapter = new AutoPalyAdapter(getSupportFragmentManager(), this);
        mAdapter.initData(fragmentList);

        autoPlayViewPager.setAdapter(mAdapter);


        autoPlayViewPager.setDirection(AutoPlayViewPager.Direction.LEFT);
        autoPlayViewPager.setShowTime(3000);
        autoPlayViewPager.setCurrentItem(1);
        autoPlayViewPager.start();
        autoPlayViewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);


    }
}
