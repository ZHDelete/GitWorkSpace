package com.zhyim.gaussian_blur_header.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.zhyim.gaussian_blur_header.R;
import com.zhyim.gaussian_blur_header.widget.GaussianView;

public class MainActivity extends AppCompatActivity {
GaussianView gaussianView;
    ImageView showIv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gaussianView = (GaussianView) findViewById(R.id.gausinan_view);
        showIv = (ImageView) findViewById(R.id.iv);


        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.miao);
        gaussianView.setBgImg(bitmap);

        gaussianView.roundBitmap(bitmap, new GaussianView.BlurCallback() {
            @Override
            public void getBlurBitmap(Bitmap bitmap) {
                showIv.setImageBitmap(bitmap);
            }
        });

    }
}
