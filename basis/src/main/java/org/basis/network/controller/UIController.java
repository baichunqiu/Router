
package org.basis.network.controller;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.basis.adapter.AbsBaseAdapter;
import org.basis.ui.base.IRefresh;
import org.basis.common.Constant;
import org.basis.network.listener.net.callback.BaseListCallback;
import org.basis.network.listener.refresh.defauleinstance.DefaultLoadListener;
import org.basis.network.listener.refresh.defauleinstance.DefaultRefreshListener;
import org.basis.network.tools.NetUtil;
import org.basis.network.view.BaseListView;
import org.basis.network.view.LoadDialog;
import org.loader.utilslib.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: BaiCQ
 * @createTime: 2017/1/13 11:38
 * @className: UIController
 * @Description: 供列表显示页面使用的控制器
 */
public abstract class UIController<T> implements IRefresh {
    private final static String Tag = "UIController";
    public Activity mActivity;
    //控制器的根节点
    private View mContentView;
    //控制器寄主子类添加的contentView
    private View contentView;
    private View noData;
    private View loadFail;
    private View showData;
    public LinearLayout ll_contain;
    public ListView currentListView;
    private BaseListView baseListView;

    public int pageSize = 10;//每页显示的记录数据 默认10条
    private int index = 0;//当前页的索引
    private String mUrl = "";
    private String mDialogMsg = "";
    private Map<String, String> mParams;
    public Class<T> tClass;
    //适配器使用功能集合 泛型不能使用 T 接口返回类型有可能和适配器使用的不一致
    private List adapterList = new ArrayList<>();
    private AbsBaseAdapter mAdapter;

    public UIController(Activity mActivity) {
        this.mActivity = mActivity;
    }

    //init 手动调用
    public void init() {
        initBasic();
        initChild();
    }

    public void release(){
        if (null != mParams)mParams.clear();
        if (null != mAdapter)mAdapter.release();
    }
    //初始化寄主实体
    private void initBasic() {
        mContentView = setContentView();
        noData = mContentView.findViewById(R.id.no_data);
        loadFail = mContentView.findViewById(R.id.load_fail);
        ll_contain = mContentView.findViewById(R.id.ll_content);
    }

    private void initChild() {
        contentView = initContainView(LayoutInflater.from(mActivity));
        currentListView = contentView.findViewById(R.id.mListView);
        showData = contentView.findViewById(R.id.show_data);
        ll_contain.addView(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        initChildView(contentView);
        if (null != showData) {
            //此处特殊处理 如果contentView 里面已经定义了showData控件 则使用该控件 并将noData 和loadFail的控件同时移动到showData控件的父控件中
            ViewGroup parent = (ViewGroup) showData.getParent();
            if (null != parent) {
                if (null != noData.getParent()) {
                    ((ViewGroup) (noData.getParent())).removeView(noData);
                }
                if (null != loadFail.getParent()) {
                    ((ViewGroup) (loadFail.getParent())).removeView(loadFail);
                }
                parent.addView(noData, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                parent.addView(loadFail, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            }
        } else {
            showData = ll_contain;
        }
        if (null != currentListView) {
            mAdapter = setAdapter();
            currentListView.setAdapter(mAdapter);
            if (currentListView instanceof BaseListView) {
                baseListView = (BaseListView) currentListView;
                baseListView.setOnRefreshListener(new DefaultRefreshListener(this));
                baseListView.setOnLoadListener(new DefaultLoadListener(this));
            }
        }
        noData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNetData(true, true);
            }
        });
        loadFail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNetData(true, true);
            }
        });
    }

    /**
     * 重新获取数据
     *
     * @param isWait    是否显示进度条
     * @param isRefresh 是否刷新
     */
    @Override
    public void getNetData(boolean isWait, boolean isRefresh) {
        if (TextUtils.isEmpty(mUrl)) {
            baseListView.onRefreshComplete();
            baseListView.onLoadComplete();
            return;
        }
        LoadDialog load = null;
        if (isWait) {
            if (TextUtils.isEmpty(mDialogMsg))
                mDialogMsg = mActivity.getString(R.string.str_loading);
            load = new LoadDialog(mActivity, mDialogMsg);
        }
        getNetData(isRefresh, mUrl, mParams, tClass, load);
    }

    private void cacheParam(String mUrl, Map<String, String> params, Class<T> tClass, LoadDialog dialog) {
        this.mUrl = mUrl;
        this.mParams = params;
        if (null != dialog) {
            this.mDialogMsg = dialog.getMsg();
        }
        this.tClass = tClass;
    }

    /**
     * @param isRefresh 是否刷新
     * @param mUrl      mUrl
     * @param params    参数 注意：不包含page
     * @param tClass    解析的实体
     * @param dialog    进度调
     */
    public void getNetData(final boolean isRefresh, final String mUrl, Map<String, String> params, Class<T> tClass, LoadDialog dialog) {
        //缓存参数
        cacheParam(mUrl, params, tClass, dialog);
        if (isRefresh) {
            index = 1;
        }
        if (null == params) {
            params = new HashMap<>(2);
        }
        params.put(Constant.pageIndex, index + "");
        //没有设置size 使用默认 一页10条
        if (!params.containsKey(Constant.pageSize)) {
            params.put(Constant.pageSize, pageSize + "");
        }
        NetUtil.postArr(dialog, mUrl, params, tClass, new BaseListCallback<LoadDialog, T>(baseListView) {
                    @Override
                    public List<T> onPreHanleData(List<T> netData) {
                        return preHandleData(netData);
                    }

                    @Override
                    public void onSuccess(LoadDialog tag, List<T> netData, int pageIndex, int pageTotal) {
                        super.onSuccess(tag, netData, pageIndex, pageTotal);
                        _onRefreshData(netData, isRefresh);
                        if (pageIndex >= pageTotal) {//最后一页
                            baseListView.setLoadFull(true);
                            index = pageIndex;
                        } else {
                            index = pageIndex + 1;
                            baseListView.setLoadFull(false);
                        }
                    }

                    @Override
                    public void onNoData(LoadDialog tag) {
                        super.onNoData(tag);
                        showViewType(Constant.NO_DATA);
                        _onRefreshData(null, isRefresh);
                    }

                    @Override
                    public void onError(LoadDialog tag, int status, String errMsg) {
                        super.onError(tag, status, errMsg);
                        showViewType(Constant.LOAD_FAIL);
                        _onRefreshData(null, isRefresh);
                    }
                }
        );
    }

    @Override
    public void onRefresh(Object obj) {}

    @Override
    public void onNetRefresh(String netType) {}

    /**
     * 设置适配器数据回调
     *
     * @param netData   接口放回数据
     * @param isRefresh 是否刷新
     */
    public final void _onRefreshData(List<T> netData, boolean isRefresh) {
        //设置适配器前  数据处理
        List preData = preRefreshData(netData);
        if (isRefresh) {
            adapterList.clear();
        }
        if (null != preData) {
            adapterList.addAll(preData);
        }
        if (!adapterList.isEmpty()) {
            showViewType(Constant.SHOW_DATA);
            mAdapter.setData(adapterList);
        } else {
            showViewType(Constant.NO_DATA);
        }
    }

    public final void showViewType(final int type) {
        noData.setVisibility(View.GONE);
        loadFail.setVisibility(View.GONE);
        showData.setVisibility(View.GONE);
        if (Constant.SHOW_DATA == type) {
            showData.setVisibility(View.VISIBLE);
        } else if (Constant.NO_DATA == type) {
            noData.setVisibility(View.VISIBLE);
        } else {
            loadFail.setVisibility(View.VISIBLE);
        }
    }


    /**
     * 解析数据时预处理数据
     * @param netData
     * @return
     */
    public List<T> preHandleData(List<T> netData) {
        return netData;
    }

    /**
     * adapter设置数据前处理数据 此处不能使用泛型 特殊情况需要修改类型
     * @param netData
     * @return
     */
    public List preRefreshData(List<T> netData) {
        return netData;
    }

    public abstract View setContentView();

    //init ll_contain 填充 view
    public abstract View initContainView(LayoutInflater inflater);

    //initContainView --> contentView的findviewById
    public abstract void initChildView(View parent);

    public abstract AbsBaseAdapter setAdapter();
}