package org.mobiletrain.android37_materialdesigndemo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.mobiletrain.android37_materialdesigndemo.R;
import org.mobiletrain.android37_materialdesigndemo.bean.HeadLineNews;
import org.mobiletrain.android37_materialdesigndemo.utils.OkHttpUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Administrator on 2016/3/22.
 */
public class HeadLineAdapter extends BaseAdapter {
    public static final int NORMAL = 0x00000001;
    public static final int SPECIAL = 0x00000002;
    public static final int PHOTOSET = 0x00000003;
    public static final int LIVE = 0x00000004;
    private static final String TAG = "HeadlineAdapter";
    private List<HeadLineNews.HeadlineEntity> list;
    private Context context;
    private int currentPosition;

    public HeadLineAdapter(List<HeadLineNews.HeadlineEntity> list,Context context) {
        this.list = list;
        this.context = context;
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
        ViewHolder viewHolder;
        PhotoSetViewHolder photoSetHolder;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            photoSetHolder = new PhotoSetViewHolder();
            switch (getItemViewType(position)){
                case NORMAL:
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_headline,parent,false);
                    SimpleDraweeView img = (SimpleDraweeView) convertView.findViewById(R.id.item_headline_img);
                    TextView replyCount = (TextView) convertView.findViewById(R.id.item_headline_replayCount);
                    TextView title = (TextView) convertView.findViewById(R.id.item_headline_title);
                    TextView digest = (TextView) convertView.findViewById(R.id.item_headline_digest);
                    resetWidth(img);
                    viewHolder.imageView = img;
                    viewHolder.tv_digest = digest;
                    viewHolder.tv_replyCount = replyCount;
                    viewHolder.tv_title = title;
                    convertView.setTag(viewHolder);
                    break;

                case PHOTOSET:
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_headline_photoset,parent,false);
                    SimpleDraweeView img1 = (SimpleDraweeView) convertView.findViewById(R.id.item_headline_pic1);
                    SimpleDraweeView img2 = (SimpleDraweeView) convertView.findViewById(R.id.item_headline_pic2);
                    SimpleDraweeView img3 = (SimpleDraweeView) convertView.findViewById(R.id.item_headline_pic3);
                    TextView replyCount2 = (TextView) convertView.findViewById(R.id.item_headline_replayCount);
                    TextView title2 = (TextView) convertView.findViewById(R.id.item_headline_title);

                    resetWidth(img1);
                    resetWidth(img2);
                    resetWidth(img3);

                    photoSetHolder.imgi = img1;
                    photoSetHolder.imgii = img2;
                    photoSetHolder.imgiii = img3;
                    photoSetHolder.tv_replyCount = replyCount2;
                    photoSetHolder.tv_title = title2;
                    convertView.setTag(photoSetHolder);
                    break;

                case SPECIAL:
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_headline,parent,false);
                    SimpleDraweeView image = (SimpleDraweeView) convertView.findViewById(R.id.item_headline_img);
                    TextView replyCount3 = (TextView) convertView.findViewById(R.id.item_headline_replayCount);
                    TextView title3 = (TextView) convertView.findViewById(R.id.item_headline_title);
                    TextView digest3 = (TextView) convertView.findViewById(R.id.item_headline_digest);
                    resetWidth(image);

                    replyCount3.setText("专题");
                    replyCount3.setTextColor(Color.BLUE);
                    viewHolder.imageView = image;
                    viewHolder.tv_digest = digest3;
                    viewHolder.tv_replyCount = replyCount3;
                    viewHolder.tv_title = title3;
                    convertView.setTag(viewHolder);
                    break;
                case LIVE:
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_headline,parent,false);
                    SimpleDraweeView image4 = (SimpleDraweeView) convertView.findViewById(R.id.item_headline_img);
                    TextView replyCount4 = (TextView) convertView.findViewById(R.id.item_headline_replayCount);
                    TextView title4 = (TextView) convertView.findViewById(R.id.item_headline_title);
                    TextView digest4 = (TextView) convertView.findViewById(R.id.item_headline_digest);
                    resetWidth(image4);

                    viewHolder.imageView = image4;
                    viewHolder.tv_digest = digest4;
                    viewHolder.tv_replyCount = replyCount4;
                    viewHolder.tv_title = title4;
                    convertView.setTag(viewHolder);

                    break;
            }

        }

        HeadLineNews.HeadlineEntity entity = list.get(position);
        switch (getItemViewType(position)){
            case PHOTOSET:
                photoSetHolder = (PhotoSetViewHolder) convertView.getTag();
                photoSetHolder.imgi.setImageURI(Uri.parse(entity.getImgsrc()));

                if(photoSetHolder.imgii != null && entity.getImgextra() != null) {
                    photoSetHolder.imgii.setImageURI(Uri.parse(entity.getImgextra().get(0).getImgsrc()));
                }
                if(photoSetHolder.imgiii != null && entity.getImgextra() != null) {
                    photoSetHolder.imgiii.setImageURI(Uri.parse(entity.getImgextra().get(1).getImgsrc()));
                }
                photoSetHolder.tv_title.setText(entity.getTitle());

                photoSetHolder.tv_replyCount.setText(entity.getReplyCount()+"跟帖");
                break;

            default:
                viewHolder = (ViewHolder) convertView.getTag();
                if(viewHolder.tv_digest != null) {
                    viewHolder.tv_digest.setText(entity.getDigest());
                }
                viewHolder.tv_title.setText(entity.getTitle());

                if(getItemViewType(position) == LIVE){
                    viewHolder.tv_replyCount.setText("直播");
                    viewHolder.tv_replyCount.setTextColor(Color.RED);
                }
                else if(getItemViewType(position) == SPECIAL){
                    viewHolder.tv_replyCount.setText("专题");
                }else {
                    viewHolder.tv_replyCount.setText(entity.getReplyCount()+"跟帖");
                }

         /*       byte[] imgByteNormal = OkHttpUtils.loadByteFromURL(entity.getImgsrc());

                Bitmap imageNormal = null;
                if(imgByteNormal != null){
                 imageNormal = BitmapFactory.decodeByteArray(imgByteNormal, 0, imgByteNormal.length);
                }*/


                viewHolder.imageView.setImageURI(Uri.parse(entity.getImgsrc()));
                break;

        }





        return convertView;
    }

    private void resetWidth(SimpleDraweeView img1) {
        ViewGroup.LayoutParams layoutParams = img1.getLayoutParams();
        layoutParams.width = context.getResources().getDisplayMetrics().widthPixels / 3;
        img1.setLayoutParams(layoutParams);
    }

    private Bitmap loadBitmap(String url) {
        final Bitmap[] bm = {null};
        OkHttpUtils.loadDataFromUrl(url, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                if(response.isSuccessful()) {
                    InputStream is = response.body().byteStream();

                    bm[0] =   BitmapFactory.decodeStream(is);

                }
            }
        });

        return bm[0];
    }


    class ViewHolder {
        private SimpleDraweeView imageView;
        private TextView tv_title;
        private TextView tv_replyCount;
        private TextView tv_digest;

    }
    class PhotoSetViewHolder {
        private TextView tv_title;
        private SimpleDraweeView imgi;
        private SimpleDraweeView imgii;
        private SimpleDraweeView imgiii;
        private TextView tv_replyCount;
    }


    @Override
    public int getViewTypeCount() {
        return 5;
    }

    @Override
    public int getItemViewType(int position) {


        String  skipType = list.get(position).getSkipType();
        if(skipType == null){
            return NORMAL;
        }else if(skipType.equals("photoset")){
            return PHOTOSET; //照片集
        }else if(skipType.equals("special")){
            return SPECIAL;  //专题
        }else if(skipType.equals("live")){
            return LIVE;
        }
        return NORMAL;

    }


}
