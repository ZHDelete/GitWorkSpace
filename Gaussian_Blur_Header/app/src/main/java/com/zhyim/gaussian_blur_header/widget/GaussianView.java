package com.zhyim.gaussian_blur_header.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.zhyim.gaussian_blur_header.R;
import com.zhyim.gaussian_blur_header.utils.CommonUtil;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * =====================================
 * 作    者:  ZHDelete
 * 创建日期:   2017/6/6
 * 描    述:
 * =====================================
 */

public class GaussianView extends View {

    private static final String TAG = "GaussianView";

    //背景图片
    private Bitmap bgImg;
    //组件宽高
    private int mWidth,mHeight;
    //bg 的 Rect
    private Rect mRect,mCenterRect;
    //画 图片的画笔
    private Paint mPaint;
    //画底边线的 画笔
    private Paint mLinePaint,mLinePaint2;

    //底边线 path
    private Path bottomPath, bottomPath2;
    private PointF startP ,endP,controlP;

    private int controlHeight;
    private int startHeight;


    public GaussianView(Context context) {
        super(context);
        init();
    }

    public GaussianView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GaussianView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
//        setBackgroundColor(Color.GREEN);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.GREEN);


        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setStrokeWidth(4);
        mLinePaint.setStyle(Paint.Style.FILL);
        mLinePaint.setAlpha(125);
        mLinePaint.setColor(Color.WHITE);

        mLinePaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint2.setStrokeWidth(4);
        mLinePaint2.setStyle(Paint.Style.FILL);
        mLinePaint2.setAlpha(110);
        mLinePaint2.setColor(Color.GRAY);


        startP = new PointF(0, 0);
        endP = new PointF(0, 0);
        controlP= new PointF(0, 0);
        bottomPath = new Path();
        bottomPath2 = new Path();

        controlHeight = 40;
        startHeight = 20;


        mRect = new Rect();
        mCenterRect = new Rect();
    }


    public void setBgImg(Bitmap bgImg) {
        this.bgImg = bgImg;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            Log.d(TAG, "width Exactly");
            mWidth = widthSize;
        } else {
            int screenWidht = CommonUtil.getScreenSize(getContext())[1];
            int desireByImg = getPaddingLeft() + bgImg.getWidth() + getPaddingRight();
            if (widthMode == MeasureSpec.AT_MOST) {
                //wrap_content时,组件宽度
                int desire = Math.min(widthSize, desireByImg);
                mWidth = desire;
            }
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            Log.d(TAG, "height Exactly");
            mHeight = heightSize;
        } else {
            int desireByImg = getPaddingTop() + bgImg.getHeight() + getPaddingBottom();
            int screenHeight = CommonUtil.getScreenSize(getContext())[1];
            mHeight = Math.min(desireByImg, heightSize);
        }
        setMeasuredDimension(mWidth, mHeight);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }

    @Override
    protected void onDraw(final Canvas canvas) {
//        super.onDraw(canvas);

        blurBitmap(bgImg, new BlurCallback() {
            @Override
            public void getBlurBitmap(Bitmap bitmap) {
                Log.d(TAG, "回调拿到模糊后的Bitmap:" + bitmap);
                drawBgBitmap(bitmap, canvas);
            }
        });

        //画底部 波浪线
        drawBottomLine(canvas);

        //画 中心 头像
        drawCenterBitmap(canvas);
    }


    private void  drawBottomLine(Canvas canvas) {
        startP.x = 0;
        startP.y = mHeight - startHeight;
        controlP.x = mWidth /4;
        controlP.y = mHeight - startHeight - controlHeight;
        endP.x = mWidth / 2;
        endP.y = mHeight - startHeight;

        bottomPath.moveTo(startP.x, startP.y);
        bottomPath.quadTo(controlP.x, controlP.y, endP.x, endP.y);

        bottomPath2.moveTo(startP.x, startP.y);
        bottomPath2.quadTo(controlP.x, controlP.y, endP.x, endP.y);
        bottomPath2.quadTo(controlP.x,mHeight - startHeight + controlHeight,
                startP.x,startP.y);

        startP.x = mWidth/2;
        startP.y = mHeight - startHeight;
        controlP.x = mWidth - mWidth / 4;
        controlP.y = mHeight - startHeight + controlHeight;
        endP.x = mWidth;
        endP.y = mHeight - startHeight;

        bottomPath.quadTo(controlP.x, controlP.y, endP.x, endP.y);

        bottomPath.lineTo(mWidth, mHeight);
        bottomPath.lineTo(0,mHeight);
        bottomPath.lineTo(0, mWidth - startHeight);
        bottomPath.close();

        canvas.drawPath(bottomPath, mLinePaint);
        canvas.drawPath(bottomPath2,mLinePaint2);
    }


    private void drawBgBitmap(Bitmap bitmap, Canvas canvas) {
        Log.d(TAG, "drawBgBitmap");
        mRect.left = getPaddingLeft();
        mRect.top = getPaddingTop();
        mRect.right = mWidth - getPaddingRight();
        mRect.bottom = mHeight - getPaddingBottom();

        canvas.drawBitmap(bitmap, null, mRect, mPaint);
    }

    public interface BlurCallback {
        public void getBlurBitmap(Bitmap bitmap);
    }

    public void blurBitmap(Bitmap bitmap, final BlurCallback blurCallback) {
        Glide.with(getContext())
                .load(R.drawable.miao)
                .bitmapTransform(new BlurTransformation(getContext(), 11))
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        int w = resource.getIntrinsicWidth();
                        int h = resource.getIntrinsicHeight();
                        String log = String.format("模糊后图片 宽度 : %s  高度 : %s ", w, h);
                        Log.d(TAG, log);
                        Bitmap.Config config = resource.getOpacity() != PixelFormat.OPAQUE
                                ? Bitmap.Config.ARGB_8888
                                : Bitmap.Config.RGB_565;
                        Bitmap blurBitmap = Bitmap.createBitmap(w, h, config);
                        Canvas canvas = new Canvas(blurBitmap);
                        resource.setBounds(0, 0, w, h);
                        resource.draw(canvas);
                        blurCallback.getBlurBitmap(blurBitmap);
                    }
                });

    }

    public void roundBitmap(Bitmap bitmap, final BlurCallback blurCallback) {
        Glide.with(getContext())
                .load(R.drawable.miao)
                .bitmapTransform(new CropCircleTransformation(getContext()))
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        int w = resource.getIntrinsicWidth();
                        int h = resource.getIntrinsicHeight();
                        String log = String.format("圆形后图片 宽度 : %s  高度 : %s ", w, h);
                        Log.d(TAG, log);
                        Bitmap.Config config = resource.getOpacity() != PixelFormat.OPAQUE
                                ? Bitmap.Config.ARGB_8888
                                : Bitmap.Config.RGB_565;
                        Bitmap blurBitmap = Bitmap.createBitmap(w, h, config);
                        Canvas canvas = new Canvas(blurBitmap);
                        resource.setBounds(0, 0, w, h);
                        resource.draw(canvas);
                        blurCallback.getBlurBitmap(blurBitmap);
                    }
                });
    }

    private void drawCenterBitmap(final Canvas canvas) {
        Log.d(TAG, "drawCenterBitmap");
        roundBitmap(bgImg, new BlurCallback() {
            @Override
            public void getBlurBitmap(Bitmap bitmap) {
//                int w = bitmap.getWidth();
//                int h = bitmap.getHeight();

                int w = 160;
                int h = 160;

                String log = String.format("拿到圆形后的Bitmap  宽度 : %s 高度 : %s ", w, h);
                Log.d(TAG, log);
                mCenterRect.left = mWidth / 2 - w / 2;
                mCenterRect.right = mWidth / 2 + w / 2;
                mCenterRect.top = mHeight / 2 - h / 2;
                mCenterRect.bottom = mHeight / 2 + h / 2;

                canvas.drawBitmap(bitmap, null, mCenterRect, mPaint);

            }
        });

    }
}
