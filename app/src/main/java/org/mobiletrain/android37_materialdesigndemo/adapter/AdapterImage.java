package org.mobiletrain.android37_materialdesigndemo.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.mobiletrain.android37_materialdesigndemo.R;
import org.mobiletrain.android37_materialdesigndemo.activity.ImageInfoActivity;
import org.mobiletrain.android37_materialdesigndemo.bean.Images;

import java.util.ArrayList;
import java.util.List;

public class AdapterImage extends RecyclerView.Adapter<AdapterImage.ViewHolder> {

    private Context mContext;
    private Images images;
    private List<Images.DataEntity.ArticlesEntity> list;

    public AdapterImage(Context mContext) {
        this.mContext = mContext;
        list = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(mContext).inflate(R.layout.item_lv_image, parent, false);
        return new ViewHolder(view);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.item_tv_title_image.setText(list.get(position).getTitle());
        // Log.e("list.get(position).getThumbnail_pic()" , list.get(position).getThumbnail_pic());
        Picasso.with(mContext).load(list.get(position).getMedia().get(0).getUrl()).placeholder(R.drawable.ic_loading).error(R.drawable.ic_loading_error).into(holder.item_iv_image);

        final View view = holder.mView;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationZ", 20, 0);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {

                        Intent intent = new Intent(mContext, ImageInfoActivity.class);
                        Log.e("----------", list.get(position).getFull_url());
                        intent.putExtra("url", list.get(position).getFull_url());

                        mContext.startActivity(intent);
                    }
                });
                animator.start();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        ImageView item_iv_image;
        TextView item_tv_title_image;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            item_iv_image = ((ImageView) view.findViewById(R.id.item_iv_image));
            item_tv_title_image = ((TextView) view.findViewById(R.id.item_tv_title_image));
        }

    }

    public void adapterclean(){
        this.list.clear();

    }

    public void addData(Images images) {
        list.addAll(images.getData().getArticles());

        notifyDataSetChanged();  //刷新数据
    }
}
