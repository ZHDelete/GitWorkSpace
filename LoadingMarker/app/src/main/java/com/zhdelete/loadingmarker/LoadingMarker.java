package com.zhdelete.loadingmarker;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * =====================================
 * author      :  ZHDelete
 * date        :  2017/10/18
 * description :
 * =====================================
 */

public class LoadingMarker extends View {

    private Paint darkPaint;
    private Paint lightPaint;
    private Paint ovalPaint;

    private int darkColor =Color.BLACK;
    private int lightColor=Color.BLUE;

    private boolean isLoading;
    private ValueAnimator loadingAnim;
    private Float startAngle;

    public LoadingMarker(Context context) {
        this(context, null);
    }

    public LoadingMarker(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingMarker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {

        //拿到 color
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.LoadingMarker, defStyleAttr, 0);
        darkColor = ta.getColor(R.styleable.LoadingMarker_darkClor, Color.BLACK);
        lightColor = ta.getColor(R.styleable.LoadingMarker_lightColor, Color.BLUE);
        isLoading = ta.getBoolean(R.styleable.LoadingMarker_isLoading, false);
        ta.recycle();

        log(String.format("darkColor -> %d lightColor -> %d black -> %d darkColor -> %d", darkColor, lightColor, Color.BLACK, Color.BLUE));

        darkPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        darkPaint.setColor(darkColor);
        darkPaint.setStrokeWidth(4);

        lightPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        lightPaint.setColor(lightColor);
        lightPaint.setStrokeWidth(4);

        ovalPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        ovalPaint.setColor(lightColor);
        ovalPaint.setStrokeWidth(6);

        loadingAnim = ValueAnimator.ofFloat();
        loadingAnim.setFloatValues(0f, 360f);
        loadingAnim.setDuration(1000 * 1);
        loadingAnim.setRepeatMode(ValueAnimator.RESTART);
        loadingAnim.setRepeatCount(ValueAnimator.INFINITE);


        loadingAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                startAngle = (Float) animation.getAnimatedValue();
                log(String.format("startAngle -> %s", startAngle));
                postInvalidate();
            }
        });

        setLoading(isLoading);

    }

    public void setDarkColor(@ColorInt int darkColor) {
        this.darkColor = darkColor;
    }

    public void setLightColor(@ColorInt int lightColor) {
        this.lightColor = lightColor;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
        if (loading) {
            if (!loadingAnim.isStarted()) {
                loadingAnim.start();
            }
        } else {
            loadingAnim.cancel();
            ViewCompat.offsetTopAndBottom(this,-30);
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    ViewCompat.offsetTopAndBottom(LoadingMarker.this, 30);
                }
            }, 200);
        }
        postInvalidate();
    }

    int mWidth;
    int mHeight;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int defWidth = (int) (DisplayUtil.getScreenWidth(getContext()) * 0.9f / 13.0f);
        int defHeith = (int) (defWidth * 1.5f);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);


        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        } else {
            mWidth = defWidth;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        } else {
            mHeight = defHeith;
        }

        log(String.format("mWidth -> %d mHeight -> %s", mWidth, mHeight));
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(final Canvas canvas) {
//        super.onDraw(canvas);
        //父类onDraw 空实现 注不注掉 都可

        int measureWidth = getMeasuredWidth();
        int measureHeight = getMeasuredHeight();

        final int centerX = measureWidth / 2;
        final int centerY = centerX;
        //背景 大圆 半径
        int radius = measureWidth / 2;
        //前景 小圆 半径
        int smallRadius = radius / 3;
        //loading 弧形 半径
        final int ovalRadius = radius * 2 / 3;
        //底座 椭圆 半径
        int ovalBotXRadius = smallRadius * 2 / 3;
        int ovalBotYRadius = smallRadius / 3;

//        log(String.format(
//                "measureWidth -> %d measureHeight -> %d\n" +
//                        "centerX -> %d centerY -> %d\n" +
//                        "radius -> %d smallRadius -> %d", measureWidth, measureHeight, centerX, centerY, radius, smallRadius));

        //底层实心 圆
        darkPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(centerX, centerY, radius, darkPaint);
        //上层 小圆
        lightPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(centerX, centerY, smallRadius, lightPaint);
        //画 底座
        RectF ovalBot = new RectF(centerX - ovalBotXRadius, measureHeight - ovalBotYRadius * 2, centerX + ovalBotXRadius, measureHeight);
        canvas.drawArc(ovalBot,0,360,true,darkPaint);
        //画 竖线
        canvas.drawLine(centerX, centerY * 2, centerX, measureHeight - ovalBotYRadius, darkPaint);

        ovalPaint.setStyle(Paint.Style.STROKE);
//        RectF oval = new RectF(centerX - ovalRadius, centerY - ovalRadius, centerX + ovalRadius, centerY + ovalRadius);
//        canvas.drawArc(oval, 90, 180, false, ovalPaint);

        if (isLoading) {
            RectF oval = new RectF(centerX - ovalRadius, centerY - ovalRadius, centerX + ovalRadius, centerY + ovalRadius);
            canvas.drawArc(oval, startAngle, 220, false, ovalPaint);

        }

    }


    private static final String TAG = "LoadingMarker";
    private static final boolean DEBUG = true;

    private void log(String logMsg) {
        if (DEBUG)
            Log.d(TAG, logMsg);
    }
}
