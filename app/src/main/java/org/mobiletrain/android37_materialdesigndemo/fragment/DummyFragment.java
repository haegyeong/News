package org.mobiletrain.android37_materialdesigndemo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.mobiletrain.android37_materialdesigndemo.R;
import org.mobiletrain.android37_materialdesigndemo.adapter.MainRecyclerAdapter;

import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class DummyFragment extends Fragment {
    private RecyclerView recyclerView_fragment;
    private Context context;
    private PtrFrameLayout ptrframrLayout;

    public DummyFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dummy, container, false);
        recyclerView_fragment = ((RecyclerView) view.findViewById(R.id.recyclerView_fragment));
        ptrframrLayout = ((PtrFrameLayout) view.findViewById(R.id.ptrframrLayout));
        refresh();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView_fragment.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView_fragment.setAdapter(new MainRecyclerAdapter(getActivity()));
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
                Toast.makeText(context,"刷新成功",Toast.LENGTH_SHORT ).show();
                ptrframrLayout.refreshComplete();  //隐藏刷新进度
            }
        });
    }
}
