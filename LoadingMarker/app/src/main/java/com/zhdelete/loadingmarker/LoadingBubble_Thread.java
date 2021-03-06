package com.zhdelete.loadingmarker;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.BounceInterpolator;

/**
 * =====================================
 * author      :  ZHDelete
 * date        :  2017/10/18
 * description :  地图 加载的 Bubble
 *                改用Thread 实现动画
 *                因为: 发现,使用ObjectAnimator 存在动画取消是 isRunnign  isStart 失效的情况
 * =====================================
 */

public class LoadingBubble_Thread extends View {

    private Paint bgPaint;
    private Paint forePaint;
    private Paint targetPaint;
    private Paint basePaint;

    private int backgroundColor = Color.parseColor("#5D4A4A");
    private int foregroundColor = Color.parseColor("#FFE248");
    private int baseColor = Color.parseColor("#44660808");

    private Path bubblePath = new Path();

    private float rotateDegree = 0;

    private boolean isLoading;

    private ObjectAnimator rotateAnim = ObjectAnimator.ofFloat(this, "rotateDegree", 0f, 360f);

    private ObjectAnimator shakeYAnim;

    public LoadingBubble_Thread(Context context) {
        this(context, null);
    }

    public LoadingBubble_Thread(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingBubble_Thread(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {

        //拿到 color
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.LoadingBubble, defStyleAttr, 0);
//        Color.parseColor("#f95656");
        backgroundColor = ta.getColor(R.styleable.LoadingBubble_background_color, Color.parseColor("#5D4A4A"));
        foregroundColor = ta.getColor(R.styleable.LoadingBubble_foreground_color, Color.parseColor("#FFE248"));
        baseColor = ta.getColor(R.styleable.LoadingBubble_base_color, Color.parseColor("#44660808"));
        isLoading = ta.getBoolean(R.styleable.LoadingBubble_loading, false);
        ta.recycle();

        log(String.format("backgroundColor -> %d foregroundColor -> %d baseColor -> %d ", backgroundColor, foregroundColor, baseColor));

        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bgPaint.setColor(backgroundColor);
        bgPaint.setStrokeWidth(4);
        bgPaint.setStyle(Paint.Style.FILL);

        forePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        forePaint.setColor(foregroundColor);
        forePaint.setStrokeWidth(4);
        forePaint.setStyle(Paint.Style.FILL);

        targetPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        targetPaint.setColor(foregroundColor);
        targetPaint.setStrokeWidth(4);
        targetPaint.setStyle(Paint.Style.STROKE);

        basePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        basePaint.setColor(baseColor);
        basePaint.setStrokeWidth(6);

        rotateAnim.setDuration(1000 * 1);
        rotateAnim.setRepeatCount(ValueAnimator.INFINITE);
        rotateAnim.setRepeatMode(ValueAnimator.REVERSE);


        setLoading(isLoading);
    }

    public float getRotateDegree() {
        return rotateDegree;
    }

    public void setRotateDegree(float rotateDegree) {
        this.rotateDegree = rotateDegree;
        postInvalidate();
    }

    public @ColorInt
    int getBackgroundColor() {
        return backgroundColor;
    }

    @Override
    public void setBackgroundColor(@ColorInt int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public @ColorInt
    int getForegroundColor() {
        return foregroundColor;
    }

    public void setForegroundColor(@ColorInt int foregroundColor) {
        this.foregroundColor = foregroundColor;
    }

    public @ColorInt
    int getBaseColor() {
        return baseColor;
    }

    public void setBaseColor(@ColorInt int baseColor) {
        this.baseColor = baseColor;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;

//        if (loading) {
//            if (!rotateAnim.isStarted()) {
//                rotateAnim.start();
//            }
//        } else {
//            rotateAnim.cancel();
//            setRotateDegree(0f);
//        }
        log(String.format("setLoading - loading -> %s rotateAnim.isRunning -> %s rotatieAnim.isStart -> %s",
                loading, rotateAnim.isRunning(), rotateAnim.isStarted()));
        ObjectAnimator shakeY = null;
        if (loading) {
            if (!rotateAnim.isRunning()) {
                float startTransY = getTranslationY();
                float endTransY = startTransY - 50;
                log(String.format("setLoading - startTransY -> %s endTransY -> %s", startTransY, endTransY));
                shakeY = ObjectAnimator.ofFloat(this, "translationY", startTransY, endTransY, startTransY);
                shakeY.setDuration(500);
                shakeY.setInterpolator(new BounceInterpolator());
                shakeY.start();
                shakeY.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        log(String.format("shakeY - onAnimationEnd - rotateAnim.start()"));
//                        rotateAnim.start();

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                while (isLoading) {
                                    float degree = getRotateDegree();
                                    degree += 5f;
                                    setRotateDegree(degree);
                                    try {
                                        Thread.currentThread().sleep(10L);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }).start();
                    }
                });
            }
        } else {
            if (shakeY != null && shakeY.isRunning()) {
                log("setLoading - shakeY.cancel");
                shakeY.cancel();
            }
            log("setLoading - rotateAnim.cancel");
//            if (rotateAnim.isRunning()) {
//            rotateAnim.cancel();
//            }
            setRotateDegree(0f);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        if (rotateAnim.isRunning()) {
            rotateAnim.cancel();
        }
    }

    int mWidth;
    int mHeight;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float desity = metrics.density;

        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displaymetrics);
        float desity2 = displaymetrics.density;

        log(String.format("desity -> %s desity2 -> %s", desity, desity2));//RedMI 3s log 结果 : desity -> 2.0 desity2 -> 2.0
        //46 x 75 31 x 50 取 4/5
        int defWidth = (int) (24.8 * desity);
        int defHeith = (int) (40 * desity);

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
        setMeasuredDimension(mWidth, mHeight + 100);//100 用来 修正 loadignBubble 与 定位 监听 重叠位置关系
    }

    @Override
    protected void onDraw(final Canvas canvas) {
//        super.onDraw(canvas);
        //父类onDraw 空实现 注不注掉 都可

        int measureWidth = getMeasuredWidth();
        int measureHeight = getMeasuredHeight() - 100;//100 用来 修正 loadignBubble 与 定位 监听 重叠位置关系

        final int centerX = measureWidth / 2;
        final int centerY = centerX;

        //背景 大圆 半径
        int radius = measureWidth / 2;
        // 标靶半径
        int radiusTarget = radius * 12 / 23;
        //黄色小圆半径
        int smallRadius = radius * 7 / 23;

        //底部椭圆 宽高
        int ovalHeight = measureHeight / 11;
        int ovalWidth = ovalHeight * 2;
        //底部椭圆 圆心
        int ovalX = centerX;
        int ovalY = measureHeight * 10 / 11 + ovalHeight / 2;

        //背景 弧形
        RectF arcBound = new RectF(0, 0, measureWidth, measureWidth);
        bubblePath.addArc(arcBound, -212, 244);
        //
        int pathBotX = ovalX;
        int pathBotY = ovalY - ovalHeight / 6;
        bubblePath.lineTo(pathBotX, pathBotY);
        bubblePath.close();
        //背景 bubble
        canvas.drawPath(bubblePath, bgPaint);
        //黄色 中心 小圆
        canvas.drawCircle(centerX, centerY, smallRadius, forePaint);

        int ovalLeft = ovalX - ovalWidth / 2;
        int ovalTop = ovalY - ovalHeight / 2;
        int ovalRight = ovalX + ovalWidth / 2;
        int ovalBot = ovalY + ovalHeight / 2;
        RectF ovalBound = new RectF(ovalLeft, ovalTop, ovalRight, ovalBot);
        canvas.drawOval(ovalBound, basePaint);

        //四边 的 标靶
        canvas.save();

        canvas.drawCircle(centerX, centerY, radiusTarget, targetPaint);
        canvas.rotate(rotateDegree, centerX, centerY);
        int targetLength = radius / 6;
        canvas.translate(centerX, centerY);
        float[] targets = new float[]
                {-radiusTarget, 0, -radiusTarget - targetLength, 0,
                        0, -radiusTarget, 0, -radiusTarget - targetLength,
                        radiusTarget, 0, radiusTarget + targetLength, 0,
                        0, radiusTarget, 0, radiusTarget + targetLength};
        canvas.drawLines(targets, targetPaint);
        canvas.restore();


    }


    private static final String TAG = "HM_LoadingBubble";
    private static final boolean DEBUG = true;

    private void log(String logMsg) {
        if (DEBUG)
            Log.d(TAG, logMsg);
    }
}
