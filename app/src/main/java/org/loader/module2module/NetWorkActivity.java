package org.loader.module2module;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.basis.network.listener.net.callback.BaseListCallback;
import org.basis.network.listener.refresh.defauleinstance.DefaultLoadListener;
import org.basis.network.listener.refresh.defauleinstance.DefaultRefreshListener;
import org.basis.network.utils.NetUtil;
import org.basis.network.view.LoadDialog;
import org.basis.network.view.PullToRefreshListView;
import org.basis.ui.base.BaseActivity;
import org.basis.utils.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NetWorkActivity extends BaseActivity implements View.OnClickListener{
    private PullToRefreshListView prlv;
    private NetAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);
        initView();
        initData();
    }

    public void initView() {
        titleBar = findViewById(R.id.titleBar);
        prlv = findViewById(R.id.prlv);
        findViewById(R.id.select_image).setOnClickListener(this);
        prlv.setOnRefreshListener(new DefaultRefreshListener(this));
        prlv.setOnLoadListener(new DefaultLoadListener(this));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_image:
               startActivity(new Intent(mActivity,NetMasterActivity.class));
        }
    }

    public void initData() {
        adapter = new NetAdapter(mActivity);
        prlv.setAdapter(adapter);
        getNetData(true,true);
    }

    @Override
    public void getNetData(boolean showDialog, boolean isRefresh) {
        Map params = new HashMap<String, String>(2);
        // 作品评论列表
        String url ="https://alaya2.sabinetek.com/response/list";
        NetUtil.postArr(showDialog? new LoadDialog(mActivity):null,url,params,ResponBean.class, new BaseListCallback<LoadDialog, ResponBean>(prlv) {
            @Override
            public void onSuccess(LoadDialog tag, List<ResponBean> responBeanList, int page, int total) {
                Logger.e(TAG,"responBeanList.size() = "+responBeanList.size());
                adapter.setData(responBeanList);
            }

            @Override
            public void onError(LoadDialog tag, int status, String errMsg) {
            }

            @Override
            public void onAfter(LoadDialog tag) {
                super.onAfter(tag);
                prlv.setLoadFull(true);
            }
        });
    }
}
