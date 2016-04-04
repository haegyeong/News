package org.mobiletrain.android37_materialdesigndemo.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.mobiletrain.android37_materialdesigndemo.R;
import org.mobiletrain.android37_materialdesigndemo.activity.ZhihuInfoActivity;
import org.mobiletrain.android37_materialdesigndemo.bean.Zhihu;
import org.mobiletrain.android37_materialdesigndemo.bean.ZhihuImageurl;
import org.mobiletrain.android37_materialdesigndemo.utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class Adapterzhihu extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    public List<Zhihu.StoriesEntity> list;
    private List<ZhihuImageurl> urllist;
    private Zhihu zhihu;
    private Constant<Zhihu> constant;


    public Adapterzhihu(Context mContext, List<Zhihu.StoriesEntity> stories) {
        this.mContext = mContext;
        this.list = stories;
        list = new ArrayList<Zhihu.StoriesEntity>();
        urllist=new ArrayList<>();
    }

    //重写父类方法
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 1;
        }
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == 1) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_zhihu_head, parent, false);

            return new HeadHolder(view);

        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_cardview_zhihu, parent, false);

            return new MyViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (position == 0) {
            HeadHolder headHolder = ((HeadHolder) holder);
            Picasso.with(mContext).load(zhihu.getImage()).placeholder(R.drawable.ic_loading).error(R.drawable.ic_loading_error).into(headHolder.iv_zhihu_head);
            headHolder.tv_head.setText(zhihu.getDescription());

        } else {
            final MyViewHolder Myviewholder = ((MyViewHolder) holder);
            Myviewholder.tv_title_item.setText(list.get(position).getTitle());
            if (urllist.get(position).getUrl()!=null){

                Picasso.with(mContext).load(Uri.parse(urllist.get(position).getUrl())).placeholder(R.drawable.ic_loading).error(R.drawable.ic_loading_error).into(Myviewholder.iv_zhihu_item);
            }

            Log.e("onBindViewHolder", list.get(position).getTitle());
            Myviewholder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ObjectAnimator animator = ObjectAnimator.ofFloat(Myviewholder.view, "translationZ", 20, 0);
                    animator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            Intent intent = new Intent(mContext, ZhihuInfoActivity.class);
                            intent.putExtra("id", list.get(position).getId());
                            mContext.startActivity(intent);  //跳转知乎详情页
                        }
                    });
                    animator.start();
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        Log.e("info", "------" + list.size());
        return list.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title_item;
        ImageView iv_zhihu_item;
        View view;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_title_item = ((TextView) itemView.findViewById(R.id.tv_title_zhihu_item));
            iv_zhihu_item = ((ImageView) itemView.findViewById(R.id.iv_image_zhihu_item));
            view = itemView;
        }
    }
    public class HeadHolder extends RecyclerView.ViewHolder {
        ImageView iv_zhihu_head;
        TextView tv_head;

        public HeadHolder(View itemView) {
            super(itemView);
            iv_zhihu_head = ((ImageView) itemView.findViewById(R.id.iv_zhihu_head));
            tv_head = ((TextView) itemView.findViewById(R.id.tv_zhihu_headtitle));

        }
    }

    public void addData(Zhihu zhihu) {
        Log.e("info", "=======" + zhihu.getStories().toString());
        list.addAll(zhihu.getStories());
        this.zhihu = zhihu;
        notifyDataSetChanged();  //刷新数据
    }
    public void addImageurl(List<ZhihuImageurl> listurl) {
        urllist.addAll(listurl);
       // notifyDataSetChanged();  //刷新数据
    }
}


