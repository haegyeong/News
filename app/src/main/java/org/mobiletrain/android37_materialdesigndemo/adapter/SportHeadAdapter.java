package org.mobiletrain.android37_materialdesigndemo.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.mobiletrain.android37_materialdesigndemo.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016-03-24.
 */
public class SportHeadAdapter extends PagerAdapter {
    private List<HashMap<String, String>> items;
    private Context context;

    public SportHeadAdapter(Context context, List<HashMap<String, String>> items) {
        this.context = context;
        this.items = items;
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
        Picasso.with(context).load(items.get(position%items.size()).get("path")).placeholder(R.drawable.ic_loading).error(R.drawable.ic_loading_error).into(iv);
        container.addView(iv);
        return iv;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}