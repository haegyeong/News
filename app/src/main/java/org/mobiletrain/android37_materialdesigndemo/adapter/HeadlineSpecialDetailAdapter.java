package org.mobiletrain.android37_materialdesigndemo.adapter;

/**
 * Created by Administrator on 2016/3/24.
 */

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.mobiletrain.android37_materialdesigndemo.R;
import org.mobiletrain.android37_materialdesigndemo.bean.SpecialDetail;

import java.util.List;

/**
 * 适配器
 */
public class HeadlineSpecialDetailAdapter extends BaseAdapter {

    private final List<SpecialDetail> data;
    private Context context;

    public HeadlineSpecialDetailAdapter(List<SpecialDetail> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SpecialDiatialViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new SpecialDiatialViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_headline,parent,false);
            SimpleDraweeView img = (SimpleDraweeView) convertView.findViewById(R.id.item_headline_img);
            TextView digest = (TextView) convertView.findViewById(R.id.item_headline_digest);
            TextView replyCount = (TextView) convertView.findViewById(R.id.item_headline_replayCount);
            TextView title = (TextView) convertView.findViewById(R.id.item_headline_title);

            resetWidth(img);
            viewHolder.imageView = img;
            viewHolder.tv_digest = digest;
            viewHolder.tv_replyCount = replyCount;
            viewHolder.tv_title = title;
            convertView.setTag(viewHolder);
        }
        viewHolder = (SpecialDiatialViewHolder) convertView.getTag();
        viewHolder.tv_title.setText(data.get(position).getTitle());
        viewHolder.tv_digest.setText(data.get(position).getDigest());
        viewHolder.tv_replyCount.setText(String.valueOf(data.get(position).getReplyCount()) + "跟贴");
        viewHolder.imageView.setImageURI(Uri.parse(data.get(position).getImgsrc()));

        return convertView;
    }
    private void resetWidth(SimpleDraweeView img1) {
        ViewGroup.LayoutParams layoutParams = img1.getLayoutParams();
        layoutParams.width = context.getResources().getDisplayMetrics().widthPixels / 3;
        img1.setLayoutParams(layoutParams);
    }
    class SpecialDiatialViewHolder {
        private SimpleDraweeView imageView;
        private TextView tv_title;
        private TextView tv_replyCount;
        private TextView tv_digest;
    }
}
