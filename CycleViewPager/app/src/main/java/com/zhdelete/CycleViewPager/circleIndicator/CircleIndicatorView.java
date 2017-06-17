package com.zhdelete.CycleViewPager.circleIndicator;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.zhdelete.CycleViewPager.R;
import com.zhdelete.CycleViewPager.indicator.DisplayUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * =====================================
 * 作    者:  ZHDelete
 * 创建日期:   2017/6/17
 * 描    述:
 * =====================================
 * http://www.jianshu.com/p/7833d8450405
 * 自定义View 实现 indicator的功能
 */


public class CircleIndicatorView extends View implements ViewPager.OnPageChangeListener {

    private Paint mCirclePaint, mTextPaint;

    private int mCount;

    private int mRadius;
    //圆形边框宽度
    private int mStrokeWidth;

    private int mTextColor;

    //小圆点默认颜色
    private int mDotNormalColor;
    //小圆点 选中颜色
    private int mDotSelectColor = Color.parseColor("#FFFFFF");

    //两圆间距
    private int mSpace;


    private boolean mIsEnableClickSwitch;

    private FillMode mFillMode;
    //indicator 集合
    private List<Indicator> mIndicators;

    //选中的位置
    private int mSelectPosition;

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setSelectionPosition(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    public static class Indicator {
        public float cx;//圆心x
        public float cy;//圆形y

        public boolean isInner(float touchX, float touchY, int mRadius, int mBorderWidth) {
            boolean result =
                    touchX < cx + mRadius + mBorderWidth &&
                            touchX > cx - mRadius - mBorderWidth &&
                            touchY > cy - mRadius - mBorderWidth &&
                            touchY < cy + mRadius + mBorderWidth;
            return result;
        }
    }

    public static enum FillMode {
        LETTER,
        NUMBER,
        NONE;

        private FillMode() {
        }
    }

    public CircleIndicatorView(Context context) {
        this(context, null);
    }

    public CircleIndicatorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public CircleIndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CircleIndicatorView(Context context, @Nullable AttributeSet attrs,
                               int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttr(context, attrs);
        init();

    }

    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CircleIndicatorView);

        mRadius = ta.getDimensionPixelSize(R.styleable.CircleIndicatorView_indicatorRadius,
                DisplayUtils.dpToPx(6));
        mStrokeWidth = ta.getDimensionPixelSize(R.styleable.CircleIndicatorView_indicatorBorderWidth,
                DisplayUtils.dpToPx(2));
        mSpace = ta.getDimensionPixelSize(R.styleable.CircleIndicatorView_indicatorSpace,
                DisplayUtils.dpToPx(5));
        //color
        mTextColor = ta.getColor(R.styleable.CircleIndicatorView_indicatorTextColor,
                Color.BLACK);
        mDotSelectColor = ta.getColor(R.styleable.CircleIndicatorView_indicatorSelectColor,
                Color.WHITE);

        mDotNormalColor = ta.getColor(R.styleable.CircleIndicatorView_indicatorColor,
                Color.GRAY);

        mIsEnableClickSwitch = ta.getBoolean(R.styleable.CircleIndicatorView_enableIndicatorSwitch,
                false);
        int fillMode = ta.getInt(R.styleable.CircleIndicatorView_fill_mode, 2);
        if (fillMode == 0) {
            mFillMode = FillMode.LETTER;
        } else if (fillMode == 1) {
            mFillMode = FillMode.NUMBER;
        } else {
            mFillMode = FillMode.NONE;
        }
        ta.recycle();
    }


    private void init() {
        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //防抖
        mCirclePaint.setDither(true);
        //填充 并 描边
        mCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mCirclePaint.setColor(mDotNormalColor);
        mCirclePaint.setStrokeWidth(mStrokeWidth);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setDither(true);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mRadius);

        mIndicators = new ArrayList<>();


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desireWidht = (mRadius + mStrokeWidth) * 2 * mCount + mSpace * (mCount - 1);
        int desireHeight = mRadius * 2 + mStrokeWidth * 2;

        setMeasuredDimension(desireWidht, desireHeight);
        initIndicatiorPosition();
    }

    private void initIndicatiorPosition() {
        mIndicators.clear();
        float cx = 0;
        for (int i = 0; i < mCount; i++) {
            Indicator indicator = new Indicator();
            if (i == 0) {
                cx = mStrokeWidth + mRadius;
            } else {
                cx += (mRadius + mStrokeWidth) * 2 + mSpace;
            }
            indicator.cx = cx;
            indicator.cy = getMeasuredHeight() / 2;
            mIndicators.add(indicator);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < mIndicators.size(); i++) {
            Indicator indicator = mIndicators.get(i);
            float x = indicator.cx;
            float y = indicator.cy;

            if (mSelectPosition == i) {
                //当前小圆点 要填充
                mCirclePaint.setStyle(Paint.Style.FILL);
                mCirclePaint.setColor(mDotSelectColor);
            } else {
                mCirclePaint.setColor(mDotNormalColor);
                if (mFillMode != FillMode.NONE) {
                    //不填充
                    mCirclePaint.setStyle(Paint.Style.STROKE);
                } else {
                    mCirclePaint.setStyle(Paint.Style.FILL);
                }
            }
            canvas.drawCircle(x, y, mRadius, mCirclePaint);

            //绘制Dot 里的内容
            if (mFillMode != FillMode.NONE) {
                String text = null;
                if (mFillMode == FillMode.LETTER) {
                    if (i >= 0 && i < LETTER.length) {
                        text = LETTER[i];
                    }
                } else if (mFillMode == FillMode.NUMBER) {
                    text = String.valueOf(i + 1);
                }

                Rect textBound = new Rect();
                mTextPaint.getTextBounds(text, 0, text.length(), textBound);
                int textWidht = textBound.width();
                int textHeight = textBound.height();

                float txtStartX = x - textWidht / 2;
                float txtStartY = y + textHeight / 2;//文字基线位置
                canvas.drawText(text, txtStartX, txtStartY, mTextPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float xPoint, yPoint;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //getX,Y 是触点 相对于本View边界的坐标
                xPoint = event.getX();
                yPoint = event.getY();
                if (mIsEnableClickSwitch) {
                    handleTouch(xPoint, yPoint);
                    return true;
                }
        }
        return super.onTouchEvent(event);
    }

    private ViewPager mViewPager;

    private void handleTouch(float xPoint, float yPoint) {
        for (int i = 0; i < mIndicators.size(); i++) {
            Indicator indicator = mIndicators.get(i);
            if (indicator.isInner(xPoint, yPoint, mRadius, mStrokeWidth)) {
                if (mViewPager != null)
                    mViewPager.setCurrentItem(i, false);
                if (indicatorClickListener != null)
                    indicatorClickListener.onSelected(i);

                break;
            }
        }

    }


    public void setCount(int count) {
        this.mCount = count;
        invalidate();
    }

    public void setRadius(int radius) {
        this.mRadius = radius;
        invalidate();
    }

    public void setBorderWidth(int borderWidth) {
        this.mStrokeWidth = borderWidth;
    }

    public void setTextColor(int textColor) {
        this.mTextColor = textColor;
    }
//未选中时颜色
    public void setDotNormalColor(int normalColor) {
        this.mDotNormalColor = normalColor;
    }
//选中时颜色
    public void setDotSelectColor(int selectColor) {
        this.mDotSelectColor = selectColor;
    }

    public void setSelectionPosition(int position) {
        this.mSelectPosition = position;
        invalidate();
    }
    public void setFillMode(FillMode fillMode) {
        this.mFillMode = fillMode;
    }



    public void setSpace(int space) {
        this.mSpace = space;
    }

    public void setEnableClickSwitch(boolean enableClickSwitch) {
        this.mIsEnableClickSwitch = enableClickSwitch;
    }



    public void setupWithViewPager(ViewPager viewPager) {
        releaseViewPager();
        if (mViewPager != null) return;

        this.mViewPager = viewPager;
        mViewPager.addOnPageChangeListener(this);
        int vpCount = mViewPager.getAdapter().getCount();
        setCount(vpCount);
    }



    private void releaseViewPager() {
        if (mViewPager != null) {
            mViewPager.removeOnPageChangeListener(this);
            mViewPager = null;
        }
    }

    private OnIndicatorClickListener indicatorClickListener;

    public void setOnIndicatorClickListener(OnIndicatorClickListener listener) {
        this.indicatorClickListener = listener;
    }

    public interface OnIndicatorClickListener {
        public void onSelected(int position);
    }

    public static final String[] LETTER = new String[]{
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "G", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    };
}
