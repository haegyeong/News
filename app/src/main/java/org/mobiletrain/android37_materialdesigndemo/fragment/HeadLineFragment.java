package org.mobiletrain.android37_materialdesigndemo.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.mobiletrain.android37_materialdesigndemo.R;
import org.mobiletrain.android37_materialdesigndemo.activity.HeadlineNormalDetailActivity;
import org.mobiletrain.android37_materialdesigndemo.activity.HeadlineSpecialDetailActivity;
import org.mobiletrain.android37_materialdesigndemo.adapter.HeadADAdapter;
import org.mobiletrain.android37_materialdesigndemo.adapter.HeadLineAdapter;
import org.mobiletrain.android37_materialdesigndemo.bean.HeadLineNews;
import org.mobiletrain.android37_materialdesigndemo.utils.Constant;
import org.mobiletrain.android37_materialdesigndemo.utils.LoadingUtil;
import org.mobiletrain.android37_materialdesigndemo.utils.NetStateUtil;
import org.mobiletrain.android37_materialdesigndemo.utils.OkHttpUtils;
import org.mobiletrain.android37_materialdesigndemo.utils.ThreadManager;
import org.mobiletrain.android37_materialdesigndemo.utils.URLSet;
import org.mobiletrain.android37_materialdesigndemo.utils.UiUitls;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;


public class HeadLineFragment extends Fragment {

    private static final String TAG = "HedaLineFragmen";

    private HeadLineAdapter adapter;
    private Gson gson;
    private View view;
    private ListView mListView;
    private PtrFrameLayout ptrfl;
    private String json;
    private List<HeadLineNews.HeadlineEntity> myheadlineEntity = null;
    private Context mContext;

    private Constant<HeadLineNews> constant;

    private ViewPager viewPager;
    private TextView text_title;
    private LinearLayout dot_layout;
    private HeadADAdapter headAdapter;
    private HeadLineNews headLineNews;
    private List<String> imgPic;
    private List<HeadLineNews.HeadlineEntity.AdsEntity> ads;
    private RelativeLayout progress;


    public HeadLineFragment() {
        gson = new Gson();
    }

    public HeadLineFragment(Context mContext) {
        this.mContext = mContext;
        gson = new Gson();
        constant = new Constant<>();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    headLineNews = (HeadLineNews) msg.obj;
                    myheadlineEntity = headLineNews.getHeadlineEntity();
                    adapter = new HeadLineAdapter(myheadlineEntity, mContext);
                    mListView.setAdapter(adapter);
                    progress.setVisibility(View.GONE);
                    initADView();

                    break;
            }
        }
    };

    private void initADView() {
        dot_layout.removeAllViews();
        ads = myheadlineEntity.get(0).getAds();
        imgPic = new ArrayList<>();
        for (int i = 0; i < ads.size(); i++) {
            imgPic.add(ads.get(i).getImgsrc());
            ImageView dot = new ImageView(UiUitls.getContext());
            dot.setImageResource(R.drawable.dots);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(8, 8);
            params.rightMargin = 8;
            dot.setEnabled(false);
            dot.setLayoutParams(params);
            dot_layout.addView(dot);
        }
        dot_layout.getChildAt(0).setEnabled(true);
        text_title.setText(ads.get(0).getTitle());
        headAdapter = new HeadADAdapter(getActivity(), imgPic);
        viewPager.setAdapter(headAdapter);
        viewPager.setCurrentItem(5 * 8000);
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                text_title.setText(ads.get(position % ads.size()).getTitle());
                for (int i = 0; i < dot_layout.getChildCount(); i++) {
                    Log.e(TAG, "dot_layout.getChildAt(i): " + i + "::" + position % ads.size());
                    dot_layout.getChildAt(i).setEnabled(position % ads.size() == i);
                }
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_headline, container, false);
        ptrfl = ((PtrFrameLayout) view.findViewById(R.id.ptrframrLayout));
        progress = ((RelativeLayout) view.findViewById(R.id.loadmore));

        addHeardView(inflater);
        mListView = ((ListView) view.findViewById(R.id.headline_fragment_lv));

        LoadingUtil.createBox(mContext,mListView,"加载中...");
        loadData();
		loadHeadData();
        itemClick();


        LoadingUtil.createBox(mContext, mListView, "加载中...");
        if(NetStateUtil.checkNetworkType(mContext) != NetStateUtil.TYPE_NET_WORK_DISABLED){
            if(NetStateUtil.isWifiValiable(mContext)){
                loadData();

                itemClick();

                refresh();
            }else{
                Toast.makeText(mContext, "WiFi已连接但没网络", Toast.LENGTH_SHORT).show();
            }

        }else {
            Toast.makeText(mContext, "无网络可用", Toast.LENGTH_SHORT).show();
        }

        return view;
    }


    private void loadData() {
        constant.getHeadlineData(HeadLineNews.class, new Constant.HeadlineListener() {
            @Override
            public void headlineListener(Object headlineEntity) {
                if(headlineEntity != null) {
                    myheadlineEntity = ((HeadLineNews) headlineEntity).getHeadlineEntity();
                    adapter = new HeadLineAdapter(((HeadLineNews) headlineEntity).getHeadlineEntity(), mContext);
                    mListView.setAdapter(adapter);
                    loadHeadData();
                }
            }
        });
    }

    private void addHeardView(LayoutInflater inflater) {
        View headView = inflater.inflate(R.layout.sport_head, null);
        viewPager = ((ViewPager) headView.findViewById(R.id.viewPager));
        text_title = ((TextView) headView.findViewById(R.id.text_title));
        dot_layout = ((LinearLayout) headView.findViewById(R.id.layout_dots));

        mListView = ((ListView) view.findViewById(R.id.headline_fragment_lv));
        mListView.addHeaderView(headView);
    }

    private void loadHeadData() {
        ThreadManager.getInstance().execute(
                new Runnable() {
                    public void run() {
                        json = OkHttpUtils.loadStringFromURL(URLSet.URL_HEADLINE);
                        Log.e(TAG, "json: " + json);
                        headLineNews = gson.fromJson(json, HeadLineNews.class);
                        Message message = Message.obtain();
                        message.what = 0;
                        message.obj = headLineNews;
                        handler.sendMessage(message);
                    }
                }
        );
        Log.e(TAG, "json: " + json);

    }

    private void refresh() {
        PtrClassicDefaultHeader defaultHeader = new PtrClassicDefaultHeader(getActivity());
        ptrfl.setHeaderView(defaultHeader);
        ptrfl.addPtrUIHandler(defaultHeader);
        ptrfl.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                loadData();
                ptrfl.refreshComplete();
            }
        });
    }

    private void itemClick() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    int currentPosition = position -mListView.getHeaderViewsCount();


                if (adapter.getItemViewType(currentPosition) == HeadLineAdapter.NORMAL) {
                    String url = myheadlineEntity.get(currentPosition).getUrl();
                    Intent intent = new Intent(getContext(), HeadlineNormalDetailActivity.class);
                    intent.putExtra("url", url);
                    startActivity(intent);
                } else if (adapter.getItemViewType(currentPosition) == HeadLineAdapter.SPECIAL) {
                    String specialID = myheadlineEntity.get(currentPosition).getSkipID();
                    String SpecialUrl = " http://c.m.163.com/nc/special/" + specialID + ".html";
                    Intent intent = new Intent(getContext(), HeadlineSpecialDetailActivity.class);
                    intent.putExtra("specialid", specialID);
                    intent.putExtra("url", SpecialUrl);
                    startActivity(intent);
                }
            }
        });
    }
}

