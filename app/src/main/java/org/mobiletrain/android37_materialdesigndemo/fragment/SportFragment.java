package org.mobiletrain.android37_materialdesigndemo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.mobiletrain.android37_materialdesigndemo.R;
import org.mobiletrain.android37_materialdesigndemo.activity.SportDetailActivity;
import org.mobiletrain.android37_materialdesigndemo.adapter.SportHeadAdapter;
import org.mobiletrain.android37_materialdesigndemo.adapter.SportTotalAdapter;
import org.mobiletrain.android37_materialdesigndemo.bean.SportTotal;
import org.mobiletrain.android37_materialdesigndemo.utils.SportThreadUtil;
import org.mobiletrain.android37_materialdesigndemo.utils.URLSet;
import org.mobiletrain.android37_materialdesigndemo.utils.UiUitls;
import org.mobiletrain.android37_materialdesigndemo.view.MyViewPager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Administrator on 2016/3/22.
 */
public class SportFragment extends Fragment {
    private static final String TAG = SportFragment.class.getSimpleName();
    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.layout_total)
    PtrFrameLayout layout_total;

    private OkHttpClient client;
    private Gson gson;
    private SportTotal total;
    private SportTotalAdapter adapter = null;
    private static List<SportTotal.DataEntity.ListEntity> entity;

    private int page = 1;
//    private ViewPager viewPager;
    private MyViewPager viewPager;
    private TextView text_title;
    private List<HashMap<String, String>> items = new ArrayList<>();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            entity = (List<SportTotal.DataEntity.ListEntity>) msg.obj;
            switch (msg.what) {
                case 0:
                    viewPager.setAdapter(new SportHeadAdapter(getActivity(), items));
                    adapter = new SportTotalAdapter(getActivity(), entity);
                    listView.setAdapter(adapter);
                    initView();
                    progress.setVisibility(View.GONE);
                    break;
                case 1:
                    adapter.addData(entity);
                    Log.e(TAG, "handleMessage: " + entity.size());
                    break;
            }
        }
    };
    private LinearLayout layout_dots;
    private boolean isBottom;
    private RelativeLayout progress;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.sportactivity_total, container, false);
        progress = ((RelativeLayout) view.findViewById(R.id.loadmore));
        ButterKnife.bind(this, view);

        View heatView = getHeatView();

        PtrClassicDefaultHeader defaultHeader = new PtrClassicDefaultHeader(getActivity());
        layout_total.setHeaderView(defaultHeader);
        layout_total.addPtrUIHandler(defaultHeader);
        layout_total.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                initNetData(1);
                layout_total.refreshComplete();
            }
        });
        listView.addHeaderView(heatView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), SportDetailActivity.class);
                SportTotal.DataEntity.ListEntity listEntiry = (SportTotal.DataEntity.ListEntity) adapter.getItem(position - listView.getHeaderViewsCount());
                intent.putExtra("url", listEntiry.getLink());
                intent.putExtra("comments", listEntiry.getComments());
                getActivity().startActivity(intent);
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (isBottom && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    initNetData(++page);
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

        initOkHttpClient();
        initNetData(1);

        return view;
    }

    private View getHeatView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.sport_head, null);
//        viewPager = ((ViewPager) view.findViewById(R.id.viewPager));
        viewPager = ((MyViewPager) view.findViewById(R.id.viewPager));
        text_title = (TextView) view.findViewById(R.id.text_title);
        layout_dots = ((LinearLayout) view.findViewById(R.id.layout_dots));
        return view;
    }

    private void initOkHttpClient() {
        gson = new Gson();
        client = new OkHttpClient();
        client.setConnectTimeout(10, TimeUnit.SECONDS);
    }

    private void initNetData(final int page) {
        Request request = new Request.Builder().url(URLSet.URL_SPORT.replaceAll("@", page + "")).build();
        Log.e(TAG, "initNetData: " + URLSet.URL_SPORT.replaceAll("@", page + ""));
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                SportThreadUtil.showToast(getActivity(), handler, "获取失败！");
                Log.i(TAG, "onFailure: " + "获取失败");

            }

            @Override
            public void onResponse(Response response) throws IOException {
                SportThreadUtil.showToast(getActivity(), handler, "获取成功！");
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    Log.e(TAG, "onResponse: " + json);

                    total = gson.fromJson(json, SportTotal.class);
                    entity = total.getData().getList();
                    Message message = Message.obtain();
                    message.obj = entity;
                    if (page == 1) {
                        message.what = 0;
                    } else {
                        message.what = 1;
                    }
                    handler.sendMessage(message);
                }
            }
        });
    }

    private void initView() {
        layout_dots.removeAllViews();
        for (int i = 0; i < entity.size(); i++) {
            if (entity.get(i).getCategory().equals("hdpic")) {
                HashMap<String, String> map = new HashMap<>();
                map.put("path", entity.get(i).getKpic());
                map.put("text", entity.get(i).getTitle());
                ImageView dot = new ImageView(UiUitls.getContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(8, 8);
                params.rightMargin = 10;
                dot.setBackgroundResource(R.drawable.dots);
                dot.setLayoutParams(params);
                dot.setEnabled(false);
                layout_dots.addView(dot);
                items.add(map);
            }
        }
        layout_dots.getChildAt(0).setEnabled(true);
        text_title.setText(items.get(0).get("text"));
        viewPager.setCurrentItem(5 * 8000);
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                text_title.setText(items.get(position % layout_dots.getChildCount()).get("text"));
                for (int i = 0; i < layout_dots.getChildCount(); i++) {
                    layout_dots.getChildAt(i).setEnabled(position % layout_dots.getChildCount() == i);
                }
                super.onPageSelected(position);
            }
        });
    }

}
