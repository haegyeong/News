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
import org.mobiletrain.android37_materialdesigndemo.activity.DetailWarActivity;
import org.mobiletrain.android37_materialdesigndemo.adapter.WarAdapter;
import org.mobiletrain.android37_materialdesigndemo.bean.War;
import org.mobiletrain.android37_materialdesigndemo.utils.ThreadManager;
import org.mobiletrain.android37_materialdesigndemo.utils.URLSet;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Administrator on 2016-03-22.
 */
public class WarFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ListView listView;
    private WarAdapter adapter;
    private boolean isBottom;
    private int page = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            War war = (War) msg.obj;
            switch (msg.what) {
                case 0:
                    adapter = new WarAdapter(getActivity(), war.getData().getNewsList());
                    listView.setAdapter(adapter);
                    progress.setVisibility(View.GONE);

                    break;
                case 1:
                    adapter.addData(war.getData().getNewsList());
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

        View view = inflater.inflate(R.layout.fragment_war, container, false);
        listView = ((ListView) view.findViewById(R.id.listview));
        ptrFrameLayout = ((PtrFrameLayout) view.findViewById(R.id.ptrframrLayout));
        progress = ((RelativeLayout) view.findViewById(R.id.loadmore));
        listView.setOnItemClickListener(this);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE & isBottom) {
                    downloadNetData(++page);
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
        downloadNetData(0);
        refresh();
        return view;
    }

    private void refresh() {
        PtrClassicDefaultHeader defaultHeader = new PtrClassicDefaultHeader(getActivity());
        ptrFrameLayout.setHeaderView(defaultHeader);
        ptrFrameLayout.addPtrUIHandler(defaultHeader);
        ptrFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                downloadNetData(0);
                ptrFrameLayout.refreshComplete();
            }
        });
    }

    private void downloadNetData(final int page) {
        Log.e("mamin", "downloadNetData: " + page);
        final OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(20, TimeUnit.SECONDS);
        final Request request = new Request.Builder().get().url(URLSet.URL_JUNSHIAD.replaceAll("@", page + "")).build();
        ThreadManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String json = response.body().string();
                        Gson gson = new Gson();
                        War war = gson.fromJson(json, War.class);
                        Message msg = Message.obtain();
                        msg.obj = war;
                        if (page == 0) {
                            msg.what = 0;
                        } else {
                            msg.what = 1;
                        }
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
        War.DataEntity.NewsListEntity data = (War.DataEntity.NewsListEntity) adapter.getItem(position);
        Bundle bundle = new Bundle();
        bundle.putString("id", data.getId());
        bundle.putString("commentNum", data.getCommentNum());

        Intent intent = new Intent(getActivity(), DetailWarActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
