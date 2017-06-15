package com.zhdelete.CycleViewPager.mzbanner;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zhdelete.CycleViewPager.R;
import com.zhdelete.CycleViewPager.yanzhenjie.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * =====================================
 * 作    者:  ZHDelete
 * 创建日期:   2017/6/14
 * 描    述:
 * =====================================
 */

public class MzActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private int[] mImgs = new int[]{
            R.mipmap.test_bunny_1,
            R.mipmap.test_bunny_2,
            R.mipmap.test_bunny_3,
            R.mipmap.test_bunny_4,
            R.mipmap.test_bunny_5,
            R.mipmap.test_bunny_6,
            R.mipmap.test_bunny_7,
            R.mipmap.test_bunny_8
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mz);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(new ClipAdapter(this));
        mViewPager.setPageTransformer(true,new CustomTransformer());
        mViewPager.setOffscreenPageLimit(4);

    }

   public class ClipAdapter extends PagerAdapter {

       private final Context mContext;

       public ClipAdapter(Context context) {
           mContext = context;
       }

       @Override
        public int getCount() {
            return mImgs.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }



        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(mContext);
            imageView.setImageResource(mImgs[position]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            container.addView(imageView);
            return imageView;
        }

       @Override
       public void destroyItem(ViewGroup container, int position, Object object) {
           container.removeView((View) object);
       }
   }
}
