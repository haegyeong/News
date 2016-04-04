package org.mobiletrain.android37_materialdesigndemo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import com.squareup.picasso.Picasso;

import org.mobiletrain.android37_materialdesigndemo.R;
import org.mobiletrain.android37_materialdesigndemo.bean.HeadLineNews;

import java.util.ArrayList;
import java.util.List;

/**
 * 广告轮播
 * Created by joson on 2016/2/25.
 */
public class AutoViewPager extends RelativeLayout{
    private Context context;
    private ViewPager viewPager;
    //指示点
    private LinearLayout dots;

    private List<ImageView> imageViews = new ArrayList<>();


    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1); //每次自动加一
        };
    };
    private AutoViewpagerAdapter adapter;

    public AutoViewPager(Context context) {
        this(context,null);
        this.context= context;
    }
    public AutoViewPager(Context context, AttributeSet attrs) {
        this(context, attrs,0);
        this.context= context;
    }

    public AutoViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context= context;
        init();
    }
    private void init(){
        View view = LayoutInflater.from(context).inflate(R.layout.auto_view_pager,this,true);
        viewPager = (ViewPager) view.findViewById(R.id.auto_viewpager_viewpager);
        dots = (LinearLayout) view.findViewById(R.id.auto_viewpager_dot);
    }

    public void setUrlList( List<HeadLineNews.HeadlineEntity.AdsEntity>  ads ){
        initImages(ads);
        initDot();
        adapter = new AutoViewpagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new AutoViewPagerChangeListener());
        // 定位。先默认定义position为无限大的中间。这样既可以往左划，又可以往右划。
        int position = (adapter.getCount() / 2)
                 - (adapter.getCount() / 2 % adapter.getRealCount());
        viewPager.setCurrentItem(position);
        autoRepeat();
    }


    private void autoRepeat() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    handler.sendEmptyMessage(0); //随便发什么
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }

    @SuppressLint("NewApi")
    private void initImages(List<HeadLineNews.HeadlineEntity.AdsEntity> ads) {
        for (int i = 0; i <ads.size(); i++) {
            ImageView img = new ImageView(context);
            int height = context.getResources().getDisplayMetrics().heightPixels / 5;
            LayoutParams params = new LayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                            LayoutParams.MATCH_PARENT)); //宽高设定
            img.setLayoutParams(params);
            img.setScaleType(ImageView.ScaleType.CENTER);
            //通过fresco加载图片到img
            Picasso.with(context).load(Uri.parse(ads.get(i).getImgsrc())).into(img);
            //创建好的图片放到图片数组中
            imageViews.add(img);
        }
    }

    @SuppressLint("NewApi")
    private void initDot() {
        for (int i = 0 ; i<imageViews.size(); i++){
            ImageView img = new ImageView(context);
             LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT));
            params.leftMargin = 5;
            params.rightMargin = 5;
            img.setLayoutParams(params);
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            img.setBackgroundResource(R.drawable.dot);
            img.setEnabled(false);
            //将创建好的点添加到布局
            dots.addView(img);
        }
        dots.getChildAt(0).setEnabled(true);
    }

    class AutoViewpagerAdapter extends PagerAdapter{
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }
        public int getRealCount(){
            return imageViews.size();
        }


        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //添加
            container.addView(imageViews.get(position % imageViews.size()));
            return imageViews.get(position % imageViews.size());
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(imageViews.get(position % imageViews.size()));
        }
    }

        class AutoViewPagerChangeListener implements ViewPager.OnPageChangeListener{

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
               //根据viewPager状态改变点
                int count = dots.getChildCount();
                position = position % imageViews.size();
                for (int i = 0; i < count; i++) {
                    dots.getChildAt(i).setEnabled(false);
                }
                dots.getChildAt(position).setEnabled(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        }
}
