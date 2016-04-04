package org.mobiletrain.android37_materialdesigndemo.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.mobiletrain.android37_materialdesigndemo.R;
import org.mobiletrain.android37_materialdesigndemo.bean.SportCommentTotal;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/3/23.
 */
public class SportCommentAdapter extends BaseAdapter {
    private Context context;
    private List<SportCommentTotal.DataEntity.CmntlistEntity> list = new ArrayList<>();

    public SportCommentAdapter(Context context, List<SportCommentTotal.DataEntity.CmntlistEntity> list) {
        this.context = context;
        this.list = list;
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
        SportCommentTotal.DataEntity.CmntlistEntity entity = list.get(position);

        HolderSportNormal vHolder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.sport_comment, parent, false);
            vHolder = new HolderSportNormal(convertView);
            convertView.setTag(vHolder);
        } else {
            vHolder = (HolderSportNormal) convertView.getTag();
        }

        Picasso.with(context).load(Uri.parse(entity.getWb_profile_img())).into(vHolder.imgHead);
        Log.e("m", "getView: " + entity.getNick());
        vHolder.text_name.setText(entity.getNick());
        vHolder.text_content.setText(entity.getContent());
        vHolder.text_time.setText(entity.getTime()+"");
        vHolder.text_location.setText(entity.getArea());
        vHolder.text_agree.setText(entity.getAgree());
//        vHolder.text_reply.setText(entity.getReplylist().get(0).getContent());

        return convertView;
    }

    class HolderSportNormal {
        @Bind(R.id.imgHead)
        ImageView imgHead;
        @Bind(R.id.text_name)
        TextView text_name;
        @Bind(R.id.text_time)
        TextView text_time;
        @Bind(R.id.text_content)
        TextView text_content;
        @Bind(R.id.text_location)
        TextView text_location;
        @Bind(R.id.text_agree)
        TextView text_agree;
        @Bind(R.id.text_reply)
        TextView text_reply;


        public HolderSportNormal(View convertView) {
            ButterKnife.bind(this,convertView);
        }
    }
}
