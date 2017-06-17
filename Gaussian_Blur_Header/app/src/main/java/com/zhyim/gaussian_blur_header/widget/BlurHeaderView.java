package com.zhyim.gaussian_blur_header.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
    private ImageView avatarIv;

    private String topText;
    private String topLeftText;
    private String topRightText;
    private String botLeftText;
    private String botRightText;
    private String botText;

    private Gender mSex;

    public static enum Gender {
        male, famel,;

        private Gender() {
        }
    }

    public BlurHeaderView(Context context) {
        super(context, null);
        init(context);

    }

    public BlurHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        init(context);
        initAttr(context,attrs);
    }

    public BlurHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        initAttr(context, attrs);
    }

    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BlurHeaderView);
        //默认使用iclauncher
        int resId = ta.getResourceId(R.styleable.BlurHeaderView_image, R.mipmap.ic_launcher);
        setSourceBitmap(BitmapFactory.decodeResource(getResources(), resId));

        topText = ta.getString(R.styleable.BlurHeaderView_topText);
        topLeftText = ta.getString(R.styleable.BlurHeaderView_topLeftText);
        topRightText = ta.getString(R.styleable.BlurHeaderView_topRightText);
        botLeftText = ta.getString(R.styleable.BlurHeaderView_botLeftText);
        botRightText = ta.getString(R.styleable.BlurHeaderView_botRightText);
        botText = ta.getString(R.styleable.BlurHeaderView_botText);

        int gender = ta.getInt(R.styleable.BlurHeaderView_gender, 1);
        if (gender == 0) {
            setGender(Gender.famel);
        } else if (gender == 1) {
            setGender(Gender.male);
        }
        ta.recycle();
    }

    public void setSourceBitmap(Bitmap sourceBmp) {
        this.sourceBmp = sourceBmp;
        blurView.setBgImg(sourceBmp);

        Bitmap clipBmp;
        //将长方形图片裁剪成正方形图片
        if (sourceBmp.getWidth() >= sourceBmp.getHeight()) {
            clipBmp = Bitmap.createBitmap(sourceBmp, sourceBmp.getWidth() / 2 - sourceBmp.getHeight() / 2, 0, sourceBmp.getHeight(), sourceBmp.getHeight()
            );
        } else {
            clipBmp = Bitmap.createBitmap(sourceBmp, 0, sourceBmp.getHeight() / 2 - sourceBmp.getWidth() / 2, sourceBmp.getWidth(), sourceBmp.getWidth()
            );
        }
        RoundedBitmapDrawable roundDrawable = RoundedBitmapDrawableFactory.create(getResources(), clipBmp);
        roundDrawable.setCornerRadius(clipBmp.getWidth() / 2); //设置圆角半径为正方形边长的一半
        roundDrawable.setAntiAlias(true);

        avatarIv.setImageDrawable(roundDrawable);

    }

    private ImageView genderIv;
    private TextView nameTv,topLeftTv,topRightTv,botLeftTv,botRightTv,botTv;
    private void init(Context context) {
        View content = LayoutInflater.from(context).inflate(R.layout.view_blur_header, this, true);
        genderIv = (ImageView) content.findViewById(R.id.gender_iv);

        blurView = (BlurBackground) content.findViewById(R.id.blur_background);
        avatarIv = (ImageView) content.findViewById(R.id.avatar_iv);

        nameTv = (TextView) content.findViewById(R.id.name_tv);
        topLeftTv = (TextView) content.findViewById(R.id.top_left_tv);
        topRightTv = (TextView) content.findViewById(R.id.top_right_tv);
        botLeftTv = (TextView) content.findViewById(R.id.bot_left_tv);
        botRightTv = (TextView) content.findViewById(R.id.bot_right_tv);
        botTv = (TextView) findViewById(R.id.bot_tv);


    }

    public void setTopText(String topText) {
        this.topText = topText;
        nameTv.setText(topText);
    }

    public void setTopLeftText(String topLeftText) {
        this.topLeftText = topLeftText;
        topLeftTv.setText(topLeftText);
    }

    public void setTopRightText(String topRightText) {
        this.topRightText = topRightText;
        topRightTv.setText(topRightText);
    }

    public void setBotLeftText(String botLeftText) {
        this.botLeftText = botLeftText;
        botLeftTv.setText(botLeftText);

    }

    public void setBotRightText(String botRightText) {
        this.botRightText = botRightText;
        botRightTv.setText(botRightText);
    }

    public void setBotText(String botText) {
        this.botText = botText;
        botTv.setText(botText);
    }

    public void setGender(Gender gender) {
        this.mSex = gender;
        if (gender == Gender.male) {
            genderIv.setImageResource(R.drawable.gender_male);
        } else if (gender == Gender.famel) {
            genderIv.setImageResource(R.drawable.gender_famel);
        }
    }
}
