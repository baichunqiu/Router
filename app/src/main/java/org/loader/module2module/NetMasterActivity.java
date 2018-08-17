package org.loader.module2module;

import android.view.LayoutInflater;
import android.view.View;

import org.basis.adapter.AbsBaseAdapter;
import org.basis.ui.AbsListActivity;


public class NetMasterActivity extends AbsListActivity<ResponBean> {

    @Override
    public View addContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_net_master, null);
    }

    @Override
    public void initView(View parent) {
        getNetData(true,true);
    }

    public void getNetData(boolean isWait, boolean isRefresh) {
        // 作品评论列表
        String url ="https://alaya2.sabinetek.com/response/list";
        super.getNetData(isWait,isRefresh,url,null,ResponBean.class);
    }

    @Override
    public AbsBaseAdapter setAdapter() {
        return new NetAdapter(mActivity);
    }
}
