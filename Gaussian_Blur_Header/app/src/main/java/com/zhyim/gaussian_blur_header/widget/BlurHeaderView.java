package com.zhyim.gaussian_blur_header.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.zhyim.gaussian_blur_header.R;

/**
 * =====================================
 * 作    者:  ZHDelete
 * 创建日期:   2017/6/16
 * 描    述:
 * =====================================
 */

public class BlurHeaderView extends RelativeLayout {

    private Bitmap sourceBmp;
    private BlurBackground blurView;
    private CardView avatorCard;
    private ImageView avatarIv;


    public BlurHeaderView(Context context) {
        super(context);
        init(context);

    }

    public BlurHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BlurHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setSourceBitmap(Bitmap sourceBmp) {
        this.sourceBmp = sourceBmp;
        blurView.setBgImg(sourceBmp);

        Bitmap clipBmp;
//将长方形图片裁剪成正方形图片
        if (sourceBmp.getWidth() >= sourceBmp.getHeight()){
            clipBmp = Bitmap.createBitmap(sourceBmp, sourceBmp.getWidth()/2 - sourceBmp.getHeight()/2, 0, sourceBmp.getHeight(), sourceBmp.getHeight()
            );
        }else{
            clipBmp = Bitmap.createBitmap(sourceBmp, 0, sourceBmp.getHeight()/2 - sourceBmp.getWidth()/2, sourceBmp.getWidth(), sourceBmp.getWidth()
            );
        }
        RoundedBitmapDrawable roundDrawable = RoundedBitmapDrawableFactory.create(getResources(), clipBmp);
        roundDrawable.setCornerRadius(clipBmp.getWidth() / 2); //设置圆角半径为正方形边长的一半
        roundDrawable.setAntiAlias(true);

        avatarIv.setImageDrawable(roundDrawable);

    }

    private void init(Context context) {
        View content = LayoutInflater.from(context).inflate(R.layout.view_blur_header, this, true);
        blurView = (BlurBackground) content.findViewById(R.id.blur_background);
        avatorCard = (CardView) content.findViewById(R.id.avatar_card);
        avatarIv = (ImageView) content.findViewById(R.id.avatar_iv);

    }
}
