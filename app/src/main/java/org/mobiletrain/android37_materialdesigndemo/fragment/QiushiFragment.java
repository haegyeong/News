package org.mobiletrain.android37_materialdesigndemo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.mobiletrain.android37_materialdesigndemo.R;
import org.mobiletrain.android37_materialdesigndemo.activity.DetailQiuShiActivity;
import org.mobiletrain.android37_materialdesigndemo.adapter.QiuShiAdapter;
import org.mobiletrain.android37_materialdesigndemo.bean.Qiushi;
import org.mobiletrain.android37_materialdesigndemo.utils.ThreadManager;
import org.mobiletrain.android37_materialdesigndemo.utils.URLSet;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Administrator on 2016-03-22.
 */
public class QiushiFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ListView listView;
    private QiuShiAdapter adapter;
    private List<Qiushi.ItemsEntity> items;
    private int page = 1;
    private boolean isBottom;
    private View footView;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Qiushi qiushi = (Qiushi) msg.obj;
            items = qiushi.getItems();
            switch (msg.what) {
                case 0:
                    adapter = new QiuShiAdapter(getActivity(), items);
                    listView.setAdapter(adapter);
                    progress.setVisibility(View.GONE);
                    break;
                case 1:
                    adapter.addData(items);
                    footView.setVisibility(View.GONE);
                    break;
            }

            super.handleMessage(msg);
        }
    };
    private PtrFrameLayout ptrFrameLayout;
    private RelativeLayout progress;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_qiushi, container, false);
        progress = ((RelativeLayout) view.findViewById(R.id.loadmore));
        footView = view.findViewById(R.id.footer_layout);
        listView = ((ListView) view.findViewById(R.id.listview));
        listView.setOnItemClickListener(this);
        ptrFrameLayout = ((PtrFrameLayout) view.findViewById(R.id.ptrframrLayout));
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE: // 停止时
                        if (isBottom) {
                            footView.setVisibility(View.VISIBLE);
                            downloadNetData(++page);
                        }
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount) {
                    isBottom = true;
                } else {
                    isBottom = false;
                }
            }
        });
        refresh(1);
        downloadNetData(page);

        return view;
    }

    private void refresh(final int page) {
        PtrClassicDefaultHeader defaultHeader = new PtrClassicDefaultHeader(getActivity());
        ptrFrameLayout.setHeaderView(defaultHeader);
        ptrFrameLayout.addPtrUIHandler(defaultHeader);
        ptrFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                downloadNetData(page);
                ptrFrameLayout.refreshComplete();
            }
        });
    }

    private void downloadNetData(final int page) {
        Log.e("main", "页码: " + page);
        final OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(20, TimeUnit.SECONDS);
        final Request request = new Request.Builder().get().url(URLSet.URL_LATEST.replaceAll("@", page + "")).build();
        ThreadManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String json = response.body().string();
                        Gson gson = new Gson();
                        Qiushi qiushi = gson.fromJson(json, Qiushi.class);
                        Message msg = Message.obtain();
                        if (page == 1) {
                            msg.what = 0;
                        } else {
                            msg.what = 1;
                        }
                        msg.obj = qiushi;
                        handler.sendMessage(msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Qiushi.ItemsEntity item = (Qiushi.ItemsEntity) adapter.getItem(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        Intent intent = new Intent(getActivity(), DetailQiuShiActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
