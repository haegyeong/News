package org.mobiletrain.android37_materialdesigndemo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import org.mobiletrain.android37_materialdesigndemo.R;
import org.mobiletrain.android37_materialdesigndemo.adapter.Adapterzhihu;
import org.mobiletrain.android37_materialdesigndemo.bean.Zhihu;
import org.mobiletrain.android37_materialdesigndemo.bean.ZhihuImageurl;
import org.mobiletrain.android37_materialdesigndemo.utils.Constant;
import org.mobiletrain.android37_materialdesigndemo.utils.NetStateUtil;

import java.util.List;

import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Administrator on 2016/3/22.
 */
public class ZhihuFragment extends Fragment implements Constant.BaseListener<Zhihu> {

    private static final String TAG = ZhihuFragment.class.getSimpleName();
    private Constant<Zhihu> constant;
    private Zhihu zhihu = new Zhihu();
    private PtrFrameLayout ptrframrLayout;
    //private LinearLayoutManager linearLayoutManager;
    private Context context;
    private int id = 0;
    private RelativeLayout progress;


    private RecyclerView recyclerView;
    private Adapterzhihu adapter;

    private Gson gson ;
    int frag;
    private LinearLayout type_new_work_disabled;


    public ZhihuFragment(Context context) {
        this.context = context;
        gson = new Gson();
        constant = new Constant<Zhihu>();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zhihu, container, false);
        ptrframrLayout = ((PtrFrameLayout) view.findViewById(R.id.ptrframrLayout));
        recyclerView = ((RecyclerView) view.findViewById(R.id.recyclerView_zhihu));
        progress = ((RelativeLayout) view.findViewById(R.id.loadmore));
        type_new_work_disabled = ((LinearLayout) view.findViewById(R.id.type_new_work_disabled));


        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        if (NetStateUtil.checkNetworkType(context)==0){
            Toast.makeText(context, "网络不可用", Toast.LENGTH_SHORT).show();
            progress.setVisibility(View.GONE);
            type_new_work_disabled.setVisibility(View.VISIBLE);
        }else {
            constant.getZhihuData(0, Zhihu.class, this); //加载第一页数据
        }

        adapter = new Adapterzhihu(context, zhihu.getStories());
        recyclerView.setAdapter(adapter);

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (frag == adapter.getItemCount() - 1) {
                    Log.e("info","到了底部");
                    id = adapter.list.get(adapter.getItemCount() - 1).getId();
                    constant.getZhihuData(id, Zhihu.class, ZhihuFragment.this); //加载第一页数据

                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

             //   frag = linearLayoutManager.findLastVisibleItemPosition();

            }
        });


        refresh();
        return view;
    }


    private void refresh() {
        //3.默认经典的head,刷新状态效果类似pulltorefresh效果
        PtrClassicDefaultHeader defaultHeader = new PtrClassicDefaultHeader(getActivity());
        ptrframrLayout.setHeaderView(defaultHeader);
        ptrframrLayout.addPtrUIHandler(defaultHeader);

        //添加动作监听事件
        ptrframrLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                //刷新事件
                ptrframrLayout.refreshComplete();  //隐藏刷新进度
            }
        });
    }


    @Override
    public void Listener(Zhihu zhihu) {
        adapter.addData(zhihu);
        if (zhihu!=null){
            progress.setVisibility(View.GONE);
        }


    }

    @Override
    public void Listenerimageurl(List<ZhihuImageurl> list) {
        adapter.addImageurl(list);
    }
}
