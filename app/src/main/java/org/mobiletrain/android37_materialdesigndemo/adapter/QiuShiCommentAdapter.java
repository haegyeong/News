package org.mobiletrain.android37_materialdesigndemo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.mobiletrain.android37_materialdesigndemo.R;
import org.mobiletrain.android37_materialdesigndemo.bean.QiuShiDetail;
import org.mobiletrain.android37_materialdesigndemo.bean.QiushiComment;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Administrator on 2016-03-23.
 */
public class QiuShiCommentAdapter extends BaseAdapter implements View.OnClickListener {


    private Context context;
    private List<QiushiComment.ItemsEntity> items;

    public QiuShiCommentAdapter(Context context, List<QiushiComment.ItemsEntity> items) {
        this.context = context;
        this.items = items;
    }

    public void addData(List<QiushiComment.ItemsEntity> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
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

        ViewHolder vholder = null;
        if (convertView == null) {
            vholder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_comment_qiushi, parent, false);
            vholder.iv_comment_heard = (ImageView) convertView.findViewById(R.id.iv_commment_heard);
            vholder.iv_agree = (ImageView) convertView.findViewById(R.id.iv_agree);
            vholder.tv_com_comment = (TextView) convertView.findViewById(R.id.tv_com_comment);
            vholder.tv_com_username = (TextView) convertView.findViewById(R.id.tv_com_username);
            vholder.tv_com_time = (TextView) convertView.findViewById(R.id.tv_com_time);
            vholder.tv_com_floor = (TextView) convertView.findViewById(R.id.tv_com_floor);
            convertView.setTag(vholder);
        } else {
            vholder = (ViewHolder) convertView.getTag();
        }
        QiushiComment.ItemsEntity itemsEntity = items.get(position);
//        Picasso.with(context).load("").into(vholder.iv_comment_heard);
        vholder.iv_agree.setOnClickListener(this);
        vholder.tv_com_comment.setText(itemsEntity.getContent());
        vholder.tv_com_username.setText(itemsEntity.getUser().getLogin());
        vholder.tv_com_time.setText(itemsEntity.getCreated_at() / (1000 * 60 * 60 * 24) + "分钟前");
        vholder.tv_com_floor.setText(itemsEntity.getFloor() + "楼");
        return convertView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_agree:
                Toast.makeText(context, "赞一个", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    class ViewHolder {
        private ImageView iv_comment_heard;
        private TextView tv_com_username;
        private TextView tv_com_comment;
        private TextView tv_com_time;
        private TextView tv_com_floor;
        private ImageView iv_agree;

    }
}
