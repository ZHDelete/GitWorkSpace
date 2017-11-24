package com.zhdelete.loadingmarker;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * author      :  ZHDelete
 * date        :  2017/11/3
 * description :
 */

public class RangeCircle extends View {

    public RangeCircle(Context context) {
        this(context, null);
    }

    public RangeCircle(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RangeCircle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private Paint circlePaint;
    private Paint innerPaint;
    private int level;

    private int circleColor;
    private int innerColor;

    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RangeCircle, defStyleAttr, 0);
        circleColor = ta.getColor(R.styleable.RangeCircle_circleColor, Color.BLUE);
        innerColor = ta.getColor(R.styleable.RangeCircle_innerColor, Color.WHITE);
        level = ta.getInt(R.styleable.RangeCircle_level, 0);
        if (level < 0 || level > 10) {
            level = 0;
            log(String.format("当前level -> %d 不正确,请设置level 在 [0,10]", level));
        }

        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setColor(circleColor);
        circlePaint.setStrokeWidth(DisplayUtil.dpToPx(2));

        innerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        innerPaint.setColor(innerColor);
    }

    public RangeCircle setLevel(int level) {
        if (this.level != level) {
            this.level = level;
            invalidate();
        }
        return this;
    }

    public RangeCircle setCircleColor(int circleColor) {
        this.circleColor = circleColor;
        return this;
    }

    public RangeCircle setInnerColor(int innerColor) {
        this.innerColor = innerColor;
        return this;
    }

    private int mWidth;
    private int mHeight;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int defWidth = DisplayUtil.getScreenWidth(getContext());
        int defHeight = (int) (DisplayUtil.getScreenHeight(getContext()) * 3.0 / 5);

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
            mHeight = defHeight;
        }
        log(String.format("final mWidth -> %d mHeight -> %s", mWidth, mHeight));
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int measureWidth = getMeasuredWidth();
        int measureHeight = getMeasuredHeight();
        int centerX = measureWidth / 2;
        int centerY = measureHeight / 2;
        int step = measureWidth / 10 / 2;
        int radius = level * step;
        if (radius != 0) {
            circlePaint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(centerX, centerY, radius, circlePaint);

            innerPaint.setStyle(Paint.Style.FILL);
            innerPaint.setAlpha(44);
            canvas.drawCircle(centerX, centerY, radius, innerPaint);
        }
    }

    private static final String TAG = "RangeCircle";
    private static final boolean DEBUG = true;

    private void log(String logMsg) {
        if (DEBUG)
            Log.d(TAG, logMsg);
    }
}
