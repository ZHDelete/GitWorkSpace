package com.zhyim.gaussian_blur_header.blur;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.renderscript.RenderScript;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.zhyim.gaussian_blur_header.App;
import com.zhyim.gaussian_blur_header.R;

/**
 * =====================================
 * 作    者:  ZHDelete
 * 创建日期:   2017/6/16
 * 描    述:
 * =====================================
 * http://www.jianshu.com/p/02da487a2f43
 * <p>
 * 在Adnroid 中，现在常用的图片高斯模糊技术有三种：
 * RenderScript 、fastBlur、对RenderScript和fastBlur的优化
 */

public class BlurActivity extends AppCompatActivity {

    private ImageView originIv, blurIv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blur);
        originIv = (ImageView) findViewById(R.id.origin_iv);
        blurIv = (ImageView) findViewById(R.id.blur_iv);

//        originIv.setImageBitmap(srcBmp);

        Bitmap srcBmp = BitmapFactory.decodeResource(getResources(), R.drawable.test_bunny_5);
        //注意float 要带f 不然判定成0
        Bitmap blurBmp = BitmapProcessor.rsBlur(App.geApp(), srcBmp, 4,1/2f);
        blurIv.setImageBitmap(blurBmp);

    }


}
