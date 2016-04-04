package org.mobiletrain.android37_materialdesigndemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.mobiletrain.android37_materialdesigndemo.R;
import org.mobiletrain.android37_materialdesigndemo.bean.WarComment;

import java.util.List;

/**
 * Created by Administrator on 2016-03-22.
 */
public class WarCommentAdatapter extends BaseAdapter {

    private Context context;
    private List<WarComment.CommentListEntity> commentList;

    public WarCommentAdatapter(Context context, List<WarComment.CommentListEntity> commentList) {
        this.context = context;
        this.commentList = commentList;
    }
    public void addData(List<WarComment.CommentListEntity> commentList){
        this.commentList.addAll(commentList);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public Object getItem(int position) {
        return commentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder vHolder = null;
        if (convertView == null) {
            vHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_comment_war, parent, false);
            vHolder.iv_heard = (ImageView) convertView.findViewById(R.id.iv_heard);
            vHolder.tv_username = (TextView) convertView.findViewById(R.id.tv_userName);
            vHolder.tv_loushu = (TextView) convertView.findViewById(R.id.tv_loushu);
            vHolder.tv_militaryRank = (TextView) convertView.findViewById(R.id.tv_militaryRank);
            vHolder.tv_comtent = (TextView) convertView.findViewById(R.id.tv_comtent);
            vHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(vHolder);
        } else {
            vHolder = (ViewHolder) convertView.getTag();
        }
        WarComment.CommentListEntity commentListEntity = commentList.get(position);
        Picasso.with(context).load(commentListEntity.getUserImg()).placeholder(R.drawable.ic_loading).error(R.drawable.ic_loading_error).into(vHolder.iv_heard);
        vHolder.tv_username.setText(commentListEntity.getUserName());
        vHolder.tv_loushu.setText(commentListEntity.getLoushu()+"楼");
        vHolder.tv_militaryRank.setText(commentListEntity.getMilitaryRank());
        vHolder.tv_comtent.setText(commentListEntity.getContent());
        vHolder.tv_time.setText(commentListEntity.getPublishTime());

        return convertView;
    }

    class ViewHolder {
        private ImageView iv_heard;
        private TextView tv_username;
        private TextView tv_loushu;
        private TextView tv_militaryRank;
        private TextView tv_comtent;
        private TextView tv_time;
    }
}
