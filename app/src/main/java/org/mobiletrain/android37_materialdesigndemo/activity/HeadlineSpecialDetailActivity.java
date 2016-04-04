package org.mobiletrain.android37_materialdesigndemo.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mobiletrain.android37_materialdesigndemo.R;
import org.mobiletrain.android37_materialdesigndemo.adapter.HeadlineSpecialDetailAdapter;
import org.mobiletrain.android37_materialdesigndemo.bean.SpecialDetail;
import org.mobiletrain.android37_materialdesigndemo.utils.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

public class HeadlineSpecialDetailActivity extends AppCompatActivity {

    private static final String TAG = "HeadlineSpecialDetailActivity";
    private ListView lv;
    private List<SpecialDetail> specialDetails;
    private Handler handler = new Handler();
    private String headImg;
    private String headDigest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_headline_special_detail);

        lv = (ListView)findViewById(R.id.headline_special_detail_lv);

        new Thread(new Runnable() {
            @Override
            public void run() {
                String json = OkHttpUtils.loadStringFromURL(getIntent().getStringExtra("url"));
                String specialid = getIntent().getStringExtra("specialid");

                final List<SpecialDetail> data = getData(json, specialid);
                Log.d(TAG, "run: 获取的专题 数" + data.size());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        lv.setAdapter(new HeadlineSpecialDetailAdapter(data, getApplicationContext()));
                      View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.headline_special_detail_head,null);
                        TextView digest = (TextView) v.findViewById(R.id.headline_special_detail_head_digest);
                        SimpleDraweeView sdv = (SimpleDraweeView) v.findViewById(R.id.headline_special_detail_head_sdv);
                          digest.setText(headDigest);
                        sdv.setImageURI(Uri.parse(headImg));
                        ViewGroup.LayoutParams l = sdv.getLayoutParams();
                        l.height = getResources().getDisplayMetrics().widthPixels / 5;
                        sdv.setLayoutParams(l);
                        lv.addHeaderView(v);
                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                if(position == 0 ){
                                    return;
                                }
                                String detailWebUrl = specialDetails.get(position -1).getUrl();
                                Intent intent = new Intent(HeadlineSpecialDetailActivity.this, HeadlineNormalDetailActivity.class);
                                intent.putExtra("url",detailWebUrl);
                                startActivity(intent);
                            }
                        });
                    }
                });
            }
        }).start();



    }

    private List<SpecialDetail> getData(String json, String specialid) {
        specialDetails = new ArrayList<>();
        JSONObject object = null;

        try {
            object = new JSONObject(json);

        JSONObject jsonObject = object.getJSONObject(specialid);

             headImg = jsonObject.getString("banner");  //头部的图片url
             headDigest = jsonObject.getString("digest");  //头部的导语

            JSONArray topics = jsonObject.getJSONArray("topics");

            for (int i = 0; i<topics.length(); i++){
                JSONObject subObj = topics.getJSONObject(i);
                String tname = subObj.getString("tname");//最新消息、新理念、新思想
                JSONArray docs = subObj.getJSONArray("docs");
                for(int j = 0; j < docs.length() ; j++){
                    JSONObject subsubObj = docs.getJSONObject(j);
                    String title = subsubObj.getString("title");
                    String digest = subsubObj.getString("digest");
                    String imgurl = subsubObj.getString("imgsrc");
                    int replyCount = subsubObj.getInt("replyCount");
                    String url = subsubObj.getString("url");
                    Log.d(TAG, "getData: 详情网址 ：" + url);
                    SpecialDetail specialDetail = new SpecialDetail(title, digest, url, replyCount,imgurl);
                    specialDetails.add(specialDetail);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return specialDetails;
    }

    public void finsh(View view) {
       finish();
    }

    /**
     *专题实体类
     */



}
