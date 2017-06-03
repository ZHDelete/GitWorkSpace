package com.zhdelete.CycleViewPager.yanzhenjie;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import com.zhdelete.CycleViewPager.R;
import com.zhdelete.CycleViewPager.yanzhenjie.AutoPalyAdapterXml;
import com.zhdelete.CycleViewPager.yanzhenjie.AutoPlayViewPagerXml;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZHDelete on 2016/8/30.
 */
public class ThirdActivity extends AppCompatActivity  implements AutoPalyAdapterXml.OnMyItemClickListener{
    private AutoPlayViewPagerXml autoPlayViewPagerXml;
    private AutoPalyAdapterXml adapterXml;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        autoPlayViewPagerXml = (AutoPlayViewPagerXml) findViewById(R.id.third_xml_vp);
        adapterXml = new AutoPalyAdapterXml(this);
        List<String> gifUrls = new ArrayList<>();
        gifUrls.add("asset:///dog.gif");
        gifUrls.add("asset:///nits.gif");
        gifUrls.add("asset:///dog.gif");
        gifUrls.add("asset:///nits.gif");
        gifUrls.add("asset:///dog.gif");

        adapterXml.initDatas(gifUrls);

        autoPlayViewPagerXml.setAdapter(adapterXml);
        autoPlayViewPagerXml.setDirection(AutoPlayViewPagerXml.Direction.LEFT);
        autoPlayViewPagerXml.setCurrentItem(0);
        autoPlayViewPagerXml.start();

        adapterXml.setMyItemListener(this);

    }

    @Override
    public void onMyItemClick(int position) {
        Log.d("ThirdActivity", "position:" + position);
    }
}
