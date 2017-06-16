package com.zhyim.gaussian_blur_header.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.zhyim.gaussian_blur_header.R;
import com.zhyim.gaussian_blur_header.widget.BlurBackground;
import com.zhyim.gaussian_blur_header.widget.BlurHeaderView;

public class MainActivity extends AppCompatActivity {
    BlurBackground blurBackground;
    ImageView showIv;

    private BlurHeaderView blurHeaderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);


//        blurBackground = (BlurBackground) findViewById(R.id.gausinan_view);
//        showIv = (ImageView) findViewById(R.id.iv);
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.miao);
//        blurBackground.setBgImg(bitmap);
        blurHeaderView = new BlurHeaderView(this);
        blurHeaderView.setSourceBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.miao));

        setContentView(blurHeaderView);
    }
}
