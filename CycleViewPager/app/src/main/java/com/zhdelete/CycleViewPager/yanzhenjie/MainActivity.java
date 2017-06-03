package com.zhdelete.CycleViewPager.yanzhenjie;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;


import com.zhdelete.CycleViewPager.R;
import com.zhdelete.CycleViewPager.yanzhenjie.AutoPalyAdapter2;
import com.zhdelete.CycleViewPager.yanzhenjie.AutoPlayViewPager2;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {



    private ViewPager mVp;
    private AutoPlayViewPager2 autoPlayViewPager2;
//    private SimpleDraweeView simpleDraweeView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        mVp = (ViewPager) findViewById(R.id.main_vp_2);
//        mVp.setAdapter(new PgAdapter(getSupportFragmentManager(), fragmentList));

        autoPlayViewPager2 = (AutoPlayViewPager2) findViewById(R.id.main_autovp2);
        AutoPalyAdapter2 autoPalyAdapter2 = new AutoPalyAdapter2(this);
        List<String> gifUrls = new ArrayList<>();
        List<Integer> drawableGifs = new ArrayList<>();
//        gifUrls.add("asset:///dog.gif");
//        gifUrls.add("asset:///nits.gif");
//        gifUrls.add("asset:///dog.gif");
//        gifUrls.add("asset:///nits.gif");
//        gifUrls.add("asset:///dog.gif");


        //glide
        drawableGifs.add(R.drawable.banner1);
        drawableGifs.add(R.drawable.banner2);
        drawableGifs.add(R.drawable.banner3);
        drawableGifs.add(R.drawable.banner4);
        drawableGifs.add(R.drawable.banner5);


        autoPalyAdapter2.initDatas(drawableGifs);

        autoPlayViewPager2.setAdapter(autoPalyAdapter2);
        autoPlayViewPager2.setDirection(AutoPlayViewPager2.Direction.LEFT);
        autoPlayViewPager2.setCurrentItem(1);
        autoPlayViewPager2.start();

//        simpleDraweeView = (SimpleDraweeView) findViewById(R.id.main_gifview);
//        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
//                .setAutoPlayAnimations(true)
//                .setUri("asset:///1.gif")
//                .build();
//        simpleDraweeView.setController(draweeController);


    }
}
