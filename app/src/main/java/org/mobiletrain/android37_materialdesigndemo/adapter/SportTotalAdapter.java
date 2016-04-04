package org.mobiletrain.android37_materialdesigndemo.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.mobiletrain.android37_materialdesigndemo.R;
import org.mobiletrain.android37_materialdesigndemo.bean.SportTotal;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/3/22.
 */
public class SportTotalAdapter extends BaseAdapter {
    private HolderNormal holdernormal;
    private HolderHead holderhead;
    private Context context;
    private List<SportTotal.DataEntity.ListEntity> list;

    public SportTotalAdapter(Context context, List<SportTotal.DataEntity.ListEntity> list) {
        this.context = context;
        this.list = list;
    }

    public void addData(List<SportTotal.DataEntity.ListEntity> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
        Log.e("TAG", "notifyDataSetChanged: " + this.list.size());

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SportTotal.DataEntity.ListEntity entity = list.get(position);
        if (position <= 5) {
            ImageView imageView = new ImageView(context);
            imageView.setVisibility(View.GONE);
            return imageView;
        }

        convertView = LayoutInflater.from(context).inflate(R.layout.sport_total, parent, false);
        holdernormal = new HolderNormal(convertView);
        convertView.setTag(holdernormal);

        HolderNormal holderNormal = holdernormal;
        Picasso.with(context).load(Uri.parse(entity.getPic())).placeholder(R.drawable.ic_loading).error(R.drawable.ic_loading_error).into(holderNormal.imageView);
        holderNormal.text_title.setText(entity.getTitle());
        holderNormal.text_summary.setText(entity.getIntro());
        holderNormal.text_comment.setText(entity.getComment() + "评论");
        return convertView;

    }

    class HolderHead {
        @Bind(R.id.viewPager)
        ViewPager viewPager;
        @Bind(R.id.text_title)
        TextView text_title;
        @Bind(R.id.layout_dots)
        LinearLayout layout_dots;

        public HolderHead(View view) {
            ButterKnife.bind(this, view);
        }
    }

    class HolderNormal {
        @Bind(R.id.imageView)
        ImageView imageView;
        @Bind(R.id.text_title)
        TextView text_title;
        @Bind(R.id.text_summary)
        TextView text_summary;
        @Bind(R.id.text_comment)
        TextView text_comment;

        public HolderNormal(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
