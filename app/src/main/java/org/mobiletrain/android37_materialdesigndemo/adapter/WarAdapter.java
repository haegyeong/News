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
import org.mobiletrain.android37_materialdesigndemo.bean.War;

import java.util.List;

/**
 * Created by Administrator on 2016-03-22.
 */
public class WarAdapter extends BaseAdapter {

    private Context context;
    private List<War.DataEntity.NewsListEntity> newsList;
    private static final int ITEM_TYPE_NORMOL = 0;
    private static final int ITEM_TYPE_PIC = 1;
    private static final int ITEM_TYPE_TXT = 2;
    private View view;

    public WarAdapter(Context context, List<War.DataEntity.NewsListEntity> newsList) {
        this.context = context;
        this.newsList = newsList;
    }

    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public Object getItem(int position) {
        return newsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        War.DataEntity.NewsListEntity newsListEntity = newsList.get(position);

        if (!newsListEntity.getPicThr().equals("")) {
            return ITEM_TYPE_PIC;
        }
        if (!newsListEntity.getPicOne().equals("")) {
            return ITEM_TYPE_NORMOL;
        }
        if (newsListEntity.getPicOne().equals("")) {
            return ITEM_TYPE_TXT;
        } else {
            return 0;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        War.DataEntity.NewsListEntity data = newsList.get(position);
        NormalView hView = null;
        ThreView threView = null;
        TxtViewHolder txtViewHolder = null;
        int viewType = getItemViewType(position);

        switch (viewType) {
            case ITEM_TYPE_NORMOL:
                if (convertView == null) {
                    hView = new NormalView();
                } else {
                    hView = (NormalView) convertView.getTag();
                }
                hView.setData(data);
                return hView.getConvertView();
            case ITEM_TYPE_PIC:
                if (convertView == null) {
                    threView = new ThreView();
                } else {
                    threView = (ThreView) convertView.getTag();
                }
                threView.setData(data);
                return threView.getConvertView();
            case ITEM_TYPE_TXT:
                if (convertView == null) {
                    txtViewHolder = new TxtViewHolder();
                } else {
                    txtViewHolder = (TxtViewHolder) convertView.getTag();
                }
                txtViewHolder.setData(data);
                return txtViewHolder.getConvertView();
        }
        return null;
    }

    public void addData(List<War.DataEntity.NewsListEntity> newsList) {
        this.newsList.addAll(newsList);
        notifyDataSetChanged();
    }

    class NormalView {

        private TextView tv_title;
        private ImageView iv_pic;
        private TextView tv_time;
        private View convertView;
        private War.DataEntity.NewsListEntity data;

        public NormalView() {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_war_normal, null);
            this.iv_pic = (ImageView) convertView.findViewById(R.id.iv_pic);
            this.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            this.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(this);
        }

        private void setData(War.DataEntity.NewsListEntity data) {
            this.data = data;
            refresh();
        }

        public View getConvertView() {
            return convertView;
        }

        public void refresh() {
            tv_title.setText(data.getTitle());
            tv_time.setText(data.getTimeAgo());
            Picasso.with(context).load(data.getPicOne()).placeholder(R.drawable.ic_loading).error(R.drawable.ic_loading_error).into(iv_pic);
        }
    }

    class ThreView {
        private TextView tv_time;
        private TextView tv_title;
        private ImageView iv_picOne;
        private ImageView iv_picTwo;
        private ImageView iv_picThr;
        private View convertView;
        private War.DataEntity.NewsListEntity data;


        public ThreView() {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_war_three, null);
            this.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            this.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            this.iv_picOne = (ImageView) convertView.findViewById(R.id.iv_picOne);
            this.iv_picTwo = (ImageView) convertView.findViewById(R.id.iv_picTwo);
            this.iv_picThr = (ImageView) convertView.findViewById(R.id.iv_picThr);
            convertView.setTag(this);
        }

        public void setData(War.DataEntity.NewsListEntity data) {
            this.data = data;
            refreshView();
        }

        public View getConvertView() {
            return convertView;
        }

        public void refreshView() {
            tv_time.setText(data.getTimeAgo());
            tv_title.setText(data.getTitle());
            Picasso.with(context).load(data.getPicOne()).into(iv_picOne);
            Picasso.with(context).load(data.getPicTwo()).into(iv_picTwo);
            Picasso.with(context).load(data.getPicThr()).into(iv_picThr);
        }
    }

    class TxtViewHolder {
        private TextView tv_title;
        private TextView tv_time;
        private View convertView;
        private War.DataEntity.NewsListEntity data;

        public TxtViewHolder() {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_war_txt, null);
            this.tv_title = (TextView) convertView.findViewById(R.id.txt_tv_title);
            this.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(this);
        }

        public View getConvertView() {
            return convertView;
        }

        public void setData(War.DataEntity.NewsListEntity data) {
            this.data = data;
            refreshView();
        }

        public void refreshView() {
            tv_title.setText(data.getTitle());
            tv_time.setText(data.getTimeAgo());
        }
    }
}
