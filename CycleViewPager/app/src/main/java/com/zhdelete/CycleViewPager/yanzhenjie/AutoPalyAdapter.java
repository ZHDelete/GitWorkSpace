package com.zhdelete.CycleViewPager.yanzhenjie;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import java.util.List;

/**
 * Created by ZHDelete on 2016/8/27.
 */
public class AutoPalyAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;

    private Context mContext;

    private FragmentManager mFragmentManager;
    private FragmentTransaction mCurTransaction;
    private static final boolean DEBUG = false;
    private static final String TAG = "AutoPalyAdapter";
    private Fragment mCurrentPrimaryItem = null;

    public AutoPalyAdapter(FragmentManager fm, Context context) {
        super(fm);
        mFragmentManager = fm;
        this.mContext = context;
        mCurTransaction = fm.beginTransaction();
    }


    public void initData(List<Fragment> fragmentList) {
        this.fragmentList = fragmentList;
    }


    @Override
    public Fragment getItem(int position) {
        Log.d(TAG, "getItem : position:" + position);
        position = position % fragmentList.size();
        Log.d(TAG, "getItem : position:  取余" + position);

        return fragmentList == null ? null : fragmentList.get(position);

    }

    /**
     * 为了能让轮播无限循环，
     * 所以我们在getCount中返回int的最大值：
     * @return
     */
    @Override
    public int getCount() {

        return fragmentList == null ? 0 : fragmentList.size();

    }

}
