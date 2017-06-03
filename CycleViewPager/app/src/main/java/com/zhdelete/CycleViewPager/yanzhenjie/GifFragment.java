package com.zhdelete.CycleViewPager.yanzhenjie;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhdelete.CycleViewPager.R;

/**
 * Created by ZHDelete on 2016/8/26.
 */
public class GifFragment extends Fragment {

    private TextView tv;

    private GifFragment instance;
    private int position;


    public static GifFragment setArgumetGetInstance(String string) {
        GifFragment instance = new GifFragment();
        Bundle bundle = new Bundle();
        bundle.putString("tips", string);
        instance.setArguments(bundle);
        return instance;
    }

    public static GifFragment newPage(GifFragment gifFragment, int position) {
         gifFragment.setPosition(position);
        return gifFragment;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getActivity()).inflate(R.layout.frag_gif, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv = (TextView) view.findViewById(R.id.frag_tv);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Bundle bundle = getArguments();
//        String str = bundle.getString("tips");
//        tv.setText(str);



    }
}
