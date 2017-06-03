package com.zhdelete.CycleViewPager.indicator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zhdelete.CycleViewPager.R;

/**
 * =====================================
 * 作    者:  ZHDelete
 * 创建日期:   2017/5/27
 * 描    述:
 * =====================================
 */

public class TestAddViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout linearLayout = new LinearLayout(this);
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.pic1);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        linearLayout.addView(imageView,params);
        setContentView(linearLayout);

    }
}
