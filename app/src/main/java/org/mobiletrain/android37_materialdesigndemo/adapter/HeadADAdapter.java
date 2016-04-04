package org.mobiletrain.android37_materialdesigndemo.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2016-03-24.
 */
public class HeadADAdapter extends PagerAdapter {
    private Context context;
    private List<String> imgPic;

    public HeadADAdapter(Context context, List<String> imgPic) {
        this.context = context;
        this.imgPic = imgPic;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView iv = new ImageView(context);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Picasso.with(context).load(imgPic.get(position % imgPic.size())).into(iv);
        container.addView(iv);
        return iv;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
