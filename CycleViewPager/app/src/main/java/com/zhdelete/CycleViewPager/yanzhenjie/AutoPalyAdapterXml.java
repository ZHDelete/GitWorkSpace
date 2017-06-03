package com.zhdelete.CycleViewPager.yanzhenjie;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhdelete.CycleViewPager.R;

import java.util.List;

/**
 * Created by ZHDelete on 2016/8/27.
 */
public class AutoPalyAdapterXml extends PagerAdapter {

    private List<String> gifUrls;
    private Context mContext;
    private OnMyItemClickListener mListener;

    public void setMyItemListener(OnMyItemClickListener mListener) {
        this.mListener = mListener;
    }


    private static final String TAG = "AutoPalyAdapterXml";


    public AutoPalyAdapterXml(Context mContext) {
        this.mContext = mContext;
    }

    public void initDatas(List<String> gifUrls) {
        if (this.gifUrls != null) {
            gifUrls.clear();
        }
        if (gifUrls != null) {
            this.gifUrls = gifUrls;

        }
    }

    /**
     * 为了能让轮播无限循环，
     * 所以我们在getCount中返回int的最大值：
     *
     * @return
     */
    @Override
    public int getCount() {
        return gifUrls == null ? 0 : Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.cyclet_item, container, false);
        TextView tv = (TextView) itemView.findViewById(R.id.cycle_item_tv);
        tv.setText("2013年8月16日 - 搞了半天textview 不能滚动,用一下代码100%可行xml如下2013年8月16日 - 搞了半天textview 不能滚动,用一下代码100%可行xml如下");

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onMyItemClick(position);

            }
        });
        container.addView(itemView);
        return  itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    interface OnMyItemClickListener{
        void onMyItemClick(int position);
    }
}
