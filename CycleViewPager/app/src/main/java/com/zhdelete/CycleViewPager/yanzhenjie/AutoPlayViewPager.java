package com.zhdelete.CycleViewPager.yanzhenjie;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by ZHDelete on 2016/8/27.
 */
public class AutoPlayViewPager extends ViewPager {





    public enum Direction {

        LEFT,
        RIGHT;
    }

    private int showTime = 3 * 1000;

    private Direction direction = Direction.LEFT;






    public AutoPlayViewPager(Context context) {
        super(context);
        init();
    }

    public AutoPlayViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        //设置页面滑动时候的动画
        //true为有序
        setPageTransformer(true, new VerticalPageTransformer());
        //设置此观点的过度滚动模式。有效过滚动模式反弹时始终（默认），
        // OVER_SCROLL_IF_CONTENT滚动（仅允许过滚动如果视图含量比容器大），
        // 或OVER_SCROLL_NEVER。设置一个视图的过滚动模式将只有如果视图是能够滚动的效果。

//        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    public void setShowTime(int showTimeMillis) {

        this.showTime = showTimeMillis;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void start() {
        //但是为了防止开发者不停的调用start方法造成卡死现象，
        // 我们在start中做个优化，每次star前先把之前private Runable player移除
        stop();
        postDelayed(player, showTime);
    }

    public void stop() {
        removeCallbacks(player);
    }

    public void previous() {
        if (direction == Direction.RIGHT) {
            play(Direction.LEFT);
        } else if (direction == Direction.LEFT) {

            play(Direction.RIGHT);
        }
    }

    public void next() {
            play(direction);
    }

    private Runnable player = new Runnable() {
        @Override
        public void run() {
            play(direction);
        }
    };

    private synchronized void play(Direction direction) {
        PagerAdapter pagerAdapter = getAdapter();
        if (pagerAdapter != null) {//判空

            //item 数量
            int count = pagerAdapter.getCount();

            Log.d("AutoPlayViewPager", "count:" + count);
            //viewpager 现在是第几个
            int currentItem = getCurrentItem();
            Log.d("AutoPlayViewPager", "currentItem:" + currentItem);


            switch (direction) {
                case LEFT:
                    currentItem++;
                    if (currentItem >= count) {
                        currentItem = 0;
                    }
                    break;
                case RIGHT:
                    currentItem--;
                    if (currentItem < 0) {
                        currentItem = count - 1;
                    }
                    break;
            }
            //根据播放方向,计算出下一个position
            setCurrentItem(currentItem);
        }

        //这就是:可以循环播放的中的,每次播放完成之后,
        // 在再调start方法,start方法里,又会post一个runnable
        start();
    }

    /**
     * 有些同学可能不知道为什么不在构造方法中而要在onFinishInflate中addOnPageChangeListener，
     * 是因为这个方法会在view被加载完成后调用，
     * 所以我们在这里做一些初始化的工作比较合理。
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == SCROLL_STATE_IDLE) {
                    start();
                } else if (state == SCROLL_STATE_DRAGGING) {
                    stop();
                }
            }
        });
    }

    //PageTransformer接口，用来自定义ViewPager页面切换动画，
// 用setPageTransformer(boolean, PageTransformer)方法来进行设置；
    private class VerticalPageTransformer implements ViewPager.PageTransformer {

        @Override
        public void transformPage(View page, float position) {
            if (position < -1) {
                page.setAlpha(0);
            } else if (position <= 1) {
                page.setAlpha(1);

                //这句话是为了:抵消x方向的滑动,否则会对角线滑动
//                page.setTranslationX(page.getWidth() * -position);

//                float yPosition = position * page.getHeight();
//                page.setTranslationY(yPosition);
                Log.d("VerticalPageTransformer", "swap 4");

            } else {
                page.setAlpha(0);
            }
        }
    }
}
