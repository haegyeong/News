package org.mobiletrain.android37_materialdesigndemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.mobiletrain.android37_materialdesigndemo.R;
import org.mobiletrain.android37_materialdesigndemo.activity.DetailQiuShiActivity;
import org.mobiletrain.android37_materialdesigndemo.bean.Qiushi;

import java.util.List;

/**
 * Created by Administrator on 2016-03-22.
 */
public class QiuShiAdapter extends BaseAdapter implements View.OnClickListener {
    private Context context;
    private List<Qiushi.ItemsEntity> items;

    public QiuShiAdapter(Context context, List<Qiushi.ItemsEntity> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        QiushiViewHolder vHolder = null;
        if (convertView == null) {
            vHolder = new QiushiViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_qiushi, parent, false);
            vHolder.iv_heard = (ImageView) convertView.findViewById(R.id.iv_heard);
            vHolder.iv_up = (ImageView) convertView.findViewById(R.id.iv_up);
            vHolder.iv_down = (ImageView) convertView.findViewById(R.id.iv_down);
            vHolder.iv_comment = (ImageView) convertView.findViewById(R.id.iv_comment);
            vHolder.iv_share = (ImageView) convertView.findViewById(R.id.iv_share);
            vHolder.tv_username = (TextView) convertView.findViewById(R.id.tv_username);
            vHolder.tv_comtent = (TextView) convertView.findViewById(R.id.tv_qcomtent);
            vHolder.tv_up = (TextView) convertView.findViewById(R.id.tv_qup);
            vHolder.tv_comment = (TextView) convertView.findViewById(R.id.tv_qcomment);
            vHolder.tv_share = (TextView) convertView.findViewById(R.id.tv_qshare);
            convertView.setTag(vHolder);
        } else {
            vHolder = (QiushiViewHolder) convertView.getTag();
        }
        Qiushi.ItemsEntity itemsEntity = items.get(position);
//        Picasso.with(context).load("").into(vHolder.iv_heard);
        vHolder.iv_up.setOnClickListener(this);
        vHolder.iv_down.setOnClickListener(this);
        vHolder.iv_comment.setOnClickListener(this);
        vHolder.iv_comment.setTag(position);
        vHolder.iv_share.setOnClickListener(this);
        if (itemsEntity.getUser() == null) {
            vHolder.tv_username.setText("匿名用户");
        } else {
            vHolder.tv_username.setText(itemsEntity.getUser().getLogin());
        }
        vHolder.tv_comtent.setText(itemsEntity.getContent());
        vHolder.tv_up.setText("好笑 " + itemsEntity.getVotes().getUp());
        vHolder.tv_comment.setText("评论 " + itemsEntity.getComments_count());
        if (itemsEntity.getShare_count() == 0) {
            vHolder.tv_share.setVisibility(View.GONE);
        } else {
            vHolder.tv_share.setVisibility(View.VISIBLE);
            vHolder.tv_share.setText("分享 " + itemsEntity.getShare_count());
        }

        return convertView;
    }

    public void addData(List<Qiushi.ItemsEntity> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_up:
                Toast.makeText(context, "好笑", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_down:
                Toast.makeText(context, "不好笑", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_comment:
                int postion = (int) v.getTag();
                Log.e("main", "onClick: postion=" + postion);
                Qiushi.ItemsEntity item = (Qiushi.ItemsEntity) this.getItem(postion);
                Bundle bundle = new Bundle();
                bundle.putInt("id", item.getId());
                Intent intent = new Intent(context, DetailQiuShiActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
                Toast.makeText(context, "评论", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_share:
                Toast.makeText(context, "分享", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    class QiushiViewHolder {
        private ImageView iv_heard;
        private ImageView iv_up;
        private ImageView iv_down;
        private ImageView iv_comment;
        private ImageView iv_share;
        private TextView tv_username;
        private TextView tv_comtent;
        private TextView tv_up;
        private TextView tv_comment;
        private TextView tv_share;
    }
}
