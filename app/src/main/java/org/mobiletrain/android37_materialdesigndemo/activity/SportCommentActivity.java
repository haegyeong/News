package org.mobiletrain.android37_materialdesigndemo.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.mobiletrain.android37_materialdesigndemo.R;
import org.mobiletrain.android37_materialdesigndemo.adapter.SportCommentAdapter;
import org.mobiletrain.android37_materialdesigndemo.bean.SportCommentTotal;
import org.mobiletrain.android37_materialdesigndemo.utils.SportThreadUtil;
import org.mobiletrain.android37_materialdesigndemo.utils.URLSet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Administrator on 2016/3/23.
 */
public class SportCommentActivity extends AppCompatActivity {
    private static final String TAG = SportCommentActivity.class.getSimpleName();
    @Bind(R.id.listView)ListView listView;
    @Bind(R.id.layout_comment)PtrFrameLayout layout_comment;
    private OkHttpClient client;
    private Context context = this;
    private Gson gson;

    private SportCommentTotal total;
    private SportCommentAdapter adapter;
    private List<SportCommentTotal.DataEntity.CmntlistEntity> list = new ArrayList<>();

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sportactivity_comment);
        ButterKnife.bind(this);

        PtrClassicDefaultHeader defaultHeader = new PtrClassicDefaultHeader(context);
        layout_comment.setHeaderView(defaultHeader);
        layout_comment.addPtrUIHandler(defaultHeader);
        layout_comment.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                layout_comment.refreshComplete();
            }
        });

        final String comments = getIntent().getStringExtra("comments");

        adapter = new SportCommentAdapter(this,list);
        listView.setAdapter(adapter);
        //网络初始化
        initOkhttpClient();
        //解析数据
        initNetData(comments);


    }
    public void finsh(View view){
        finish();

    }

    private void initOkhttpClient() {
        gson = new Gson();
        client = new OkHttpClient();
        client.setConnectTimeout(10, TimeUnit.SECONDS);
    }

    private void initNetData(String comments) {
        Request request = new Request.Builder().url(URLSet.URL_SPORTCOMMENT.replace("@",comments)).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                SportThreadUtil.showToast(context, handler, "获取评论失败！");
            }

            @Override
            public void onResponse(Response response) throws IOException {
                 SportThreadUtil.showToast(context, handler, "success");

                if (response.isSuccessful()){
                    String json = response.body().string();
                    Log.e(TAG, "onResponse: " + json);

                    total = gson.fromJson(json, SportCommentTotal.class);
                    List<SportCommentTotal.DataEntity.CmntlistEntity> entity = total.getData().getCmntlist();
                    list.addAll(entity);
                    SportThreadUtil.adapterNotifyDataSetChanged(handler,adapter);
                }
            }
        });
    }
}
