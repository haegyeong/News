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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.mobiletrain.android37_materialdesigndemo.R;
import org.mobiletrain.android37_materialdesigndemo.adapter.AdapterImage;
import org.mobiletrain.android37_materialdesigndemo.bean.Images;
import org.mobiletrain.android37_materialdesigndemo.bean.ZhihuImageurl;
import org.mobiletrain.android37_materialdesigndemo.utils.Constant;
import org.mobiletrain.android37_materialdesigndemo.utils.NetStateUtil;
import org.mobiletrain.android37_materialdesigndemo.utils.URLSet;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Administrator on 2016/3/23.
 */
public class ImageFragment extends Fragment implements Constant.BaseListener<Images> {
    private Context context;
    private ListView listView;
    private RecyclerView recyclerView;
    private List<Images.DataEntity.ArticlesEntity> list;
    private Constant<Images> constant;
    private AdapterImage adapter;
    private LinearLayoutManager linearLayoutManager;
    private PtrFrameLayout ptrframrLayout;
    private RelativeLayout progress;

    private Images images;
    private int frag;
    String next_url;
    private LinearLayout type_new_work_disabled;

    public ImageFragment() {
    }

    public ImageFragment(Context mContext) {
        context = mContext;
        constant = new Constant<Images>();

        list = new ArrayList<>();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        recyclerView = ((RecyclerView) view.findViewById(R.id.recyclerView_image));
        ptrframrLayout = ((PtrFrameLayout) view.findViewById(R.id.ptrframrLayout));
        progress = ((RelativeLayout) view.findViewById(R.id.loadmore));
        type_new_work_disabled = ((LinearLayout) view.findViewById(R.id.type_new_work_disabled));


        if (NetStateUtil.checkNetworkType(context)==0){
            Toast.makeText(context,"网络不可用",Toast.LENGTH_SHORT).show();
            progress.setVisibility(View.GONE);
            type_new_work_disabled.setVisibility(View.VISIBLE);
        }else {
            constant.getImageData(URLSet.URL_PICTRUE, Images.class, this);  //加载首页数据
        }

        adapter = new AdapterImage(context);
        recyclerView.setAdapter(adapter);

        linearLayoutManager = new LinearLayoutManager(context);

        if (recyclerView.getLayoutManager()==null){

            recyclerView.setLayoutManager(linearLayoutManager);
        }
        refresh();

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.e("frag",frag+"-----");
                if (frag == adapter.getItemCount() -2) {
                    constant.loadamore(true);
                    constant.getImageData(next_url, Images.class, ImageFragment.this);  //加载下一页数据
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                frag = linearLayoutManager.findLastVisibleItemPosition();

            }
        });


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
                adapter.adapterclean(); //清空数据重新加载
                list.clear();
                constant.getImageData(URLSet.URL_PICTRUE, Images.class, ImageFragment.this);

            }
        });

    }




    @Override
    public void Listener(Images entity) {
        next_url =entity.getData().getInfo().getNext_url();
        adapter.addData(entity);
        images = entity;
        list.addAll(entity.getData().getArticles());
        if (list.size()!=0){
            progress.setVisibility(View.GONE);
            ptrframrLayout.refreshComplete();  //隐藏刷新进度
        }
    }

    @Override
    public void Listenerimageurl(List<ZhihuImageurl> list) {

    }
}
