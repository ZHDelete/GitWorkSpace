package com.zhdelete.CycleViewPager.yanzhenjie;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by ZHDelete on 2016/8/27.
 */
public class AutoPalyAdapter2 extends PagerAdapter {

    private List<String> gifUrls;
    private Context mContext;


    private static final String TAG = "AutoPalyAdapter2";
    List<Integer> drawableGifs;


    public AutoPalyAdapter2(Context mContext) {
        this.mContext = mContext;
    }

//    public void initDatas(List<String> gifUrls) {
//        if (this.gifUrls != null) {
//            gifUrls.clear();
//        }
//        if (gifUrls != null) {
//            this.gifUrls = gifUrls;
//
//        }
//    }
    public void initDatas( List<Integer> drawableGifs) {
        if (this.drawableGifs != null) {
            drawableGifs.clear();
        }
        if (drawableGifs != null) {
            this.drawableGifs = drawableGifs;

        }
    }

    /**
     * 为了能让轮播无限循环，
     * 所以我们在getCount中返回int的最大值：
     *
     * @return
     */
    @Override
    public int getCount() {
//        return gifUrls == null ? 0 : Integer.MAX_VALUE;
        return drawableGifs == null ? 0 : Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

//           SimpleDraweeView simpleDraweeView = new SimpleDraweeView(mContext);
//            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(300, 150);
//            simpleDraweeView.setLayoutParams(layoutParams);
//            String url = gifUrls.get(position % gifUrls.size());
//            Log.d(TAG, url);
////        simpleDraweeView.setImageURI("http://f.hiphotos.baidu.com/image/h%3D200/sign=3630dd6c4336acaf46e091fc4cd88d03/bd3eb13533fa828b5bc103b6f51f4134960a5a81.jpg");
////        simpleDraweeView.setImageURI("http://ftp.ytbbs.com/attachments/forum/201404/14/165935vfzw45q2574ggvii.gif");
////        Uri uri = Uri.parse("asset:///1.gif");
////        simpleDraweeView.setImageURI(uri);
//
//
//            DraweeController draweeController = Fresco.newDraweeControllerBuilder()
//                    .setAutoPlayAnimations(true)
////                    .setUri(Uri.parse("asset:///dog.gif"))
//                    .setUri(Uri.parse(url))
//                    .build();
//            simpleDraweeView.setController(draweeController);
//
//            container.addView(simpleDraweeView);
//        return simpleDraweeView;
        ImageView imageView = new ImageView(mContext);
        Glide.with(mContext).load(drawableGifs.get(position%drawableGifs.size())).into(imageView);
        container.addView(imageView);
        return imageView;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
