package com.zhdelete.CycleViewPager.indicator;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.zhdelete.CycleViewPager.R;

import java.util.ArrayList;
import java.util.List;

/**
 * =====================================
 * 作    者:  ZHDelete
 * 创建日期:   2017/5/27
 * 描    述:
 * =====================================
 */

public class IndicatorActivity extends AppCompatActivity {

    private static final String TAG = "IndicatorActivity";

    private ViewPager mVpager;
    private GuidePagerAdapter mVPagerAdapter;

    private LinearLayout indicatorGroup;

    private RelativeLayout rootView;

    List<ImageView> copyIndicators;
    private RelativeLayout.LayoutParams rlpamas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_indicator);

        mVpager = (ViewPager) findViewById(R.id.view_pager);
        rootView = (RelativeLayout) findViewById(R.id.root_content);


        List<Integer> imgs = new ArrayList<>();
        imgs.add(R.drawable.pic1);
        imgs.add(R.drawable.pic2);
        imgs.add(R.drawable.pic3);
        imgs.add(R.drawable.pic4);

        mVPagerAdapter = new GuidePagerAdapter(imgs, this);
        mVpager.setAdapter(mVPagerAdapter);


        mVpager.addOnPageChangeListener(pageChangeListener);


        indicatorGroup = new LinearLayout(this);

//        indicatorGroup.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white));
//        rlpamas = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 168);

        rlpamas = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rlpamas.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        rlpamas.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        rlpamas.bottomMargin = 56;

        Button button = new Button(this);
        button.setText("button");




        LinearLayout.LayoutParams llparams =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        llparams.gravity = Gravity.CENTER;

        copyIndicators = new ArrayList<>();
        for (int i = 0; i < imgs.size(); i++) {
            ImageView tmp = createIndictImageView();
            indicatorGroup.addView(tmp,llparams);
            copyIndicators.add(tmp);
        }

        rootView.addView(indicatorGroup, rlpamas);
        resetIndicator();

    }
    private ImageView createIndictImageView() {
//        ImageView indict = (ImageView) getLayoutInflater()
//                .inflate(R.layout.indicator_iv, null,false);
        ImageView indict = new ImageView(this);
        indict.setPadding(15, 15, 15, 15);
        indict.setClickable(true);
        indict.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.guide_dot_seletor_rect));
        return indict;
    }



    ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            handleIndicatorSelected(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void handleIndicatorSelected(int position) {
        int size = copyIndicators.size();
        for (int i = 0; i < size; i++) {
            ImageView indict = copyIndicators.get(i);
            if (position == i)
                indict.setEnabled(true);
            else
                indict.setEnabled(false);
        }
    }

    private void resetIndicator() {
        int size = copyIndicators.size();
        for (int i = 0; i < size; i++) {
            ImageView indict = copyIndicators.get(i);
            if (i == 0)
                indict.setEnabled(true);
            else
                indict.setEnabled(false);
        }
    }


    private class GuidePagerAdapter extends PagerAdapter {


        private List<Integer> images = new ArrayList<>();
        private Context mContext;

        private SparseArray<View> mViewsSaved;

        public GuidePagerAdapter(List<Integer> images, Context mContext) {
            this.images = images;
            this.mContext = mContext;
            mViewsSaved = new SparseArray<>();
        }

        @Override
        public int getCount() {
            return !images.isEmpty() ? images.size() : 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView page = new ImageView(mContext);
            ViewGroup.LayoutParams params =
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT);
            page.setLayoutParams(params);
            mViewsSaved.put(position, page);
            page.setImageResource(images.get(position));

            container.addView(page);

            return page;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);// 调 super 会报错
            try {
                container.removeView(mViewsSaved.get(position));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

