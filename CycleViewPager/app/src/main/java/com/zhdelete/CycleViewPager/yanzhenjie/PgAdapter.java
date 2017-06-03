package com.zhdelete.CycleViewPager.yanzhenjie;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.List;

/**
 * Created by ZHDelete on 2016/8/27.
 */
public class PgAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;
    public PgAdapter(FragmentManager fm,List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        Log.d("PgAdapter", "position:" + position);
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
