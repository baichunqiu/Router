
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
import org.basis.network.utils.NetUtil;
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
public abstract class UIController<T> implements IRefresh ,IOperator<T>{
    private final static String Tag = "UIController";
    public Activity mActivity;
    //控制器的根节点
    private View mContentView;
    private View mNoData;
    private View mLoadFail;
    public LinearLayout mLLContain;

    //控制器寄主子类添加的contentView
    private View childContentView;
    private View childShowData;
    public ListView childListView;
    private BaseListView childBaseListView;

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

    private void initBasic() {
        mContentView = setContentView();
        mNoData = mContentView.findViewById(R.id.no_data);
        mLoadFail = mContentView.findViewById(R.id.load_fail);
        mLLContain = mContentView.findViewById(R.id.ll_content);
    }

    private void initChild() {
        childContentView = onCreateContentView(LayoutInflater.from(mActivity));
        childListView = childContentView.findViewById(R.id.mListView);
        childShowData = childContentView.findViewById(R.id.show_data);
        mLLContain.addView(childContentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        /**
         * 此处特殊处理逻辑:
         * 1.childContentView没有定义showData控件：
         *      默认childContentView
         * 2.childContentView经定义了showData控件：
         *      确保showData noData loadFail 在并列存同一父控件中，
         *      需将将noData 和loadFail的控件移除，并添加到showData控件的父控件。
         */
        if (null == childShowData) {//未定义showData, 使用默认
            childShowData = childContentView;
        } else {
            ViewGroup parent = (ViewGroup) childShowData.getParent();
            if (null != parent) {
                if (null != mNoData.getParent()) {
                    ((ViewGroup) (mNoData.getParent())).removeView(mNoData);
                }
                if (null != mLoadFail.getParent()) {
                    ((ViewGroup) (mLoadFail.getParent())).removeView(mLoadFail);
                }
                parent.addView(mNoData, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                parent.addView(mLoadFail, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            }
        }
        if (null != childListView) {
            mAdapter = setAdapter();
            childListView.setAdapter(mAdapter);
            if (childListView instanceof BaseListView) {
                childBaseListView = (BaseListView) childListView;
                childBaseListView.setOnRefreshListener(new DefaultRefreshListener(this));
                childBaseListView.setOnLoadListener(new DefaultLoadListener(this));
            }
        }
        mNoData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNetData(true, true);
            }
        });
        mLoadFail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNetData(true, true);
            }
        });
    }

    /**
     * 重新获取数据
     * @param isWait    是否显示进度条
     * @param isRefresh 是否刷新
     */
    @Override
    public void getNetData(boolean isWait, boolean isRefresh) {
        if (TextUtils.isEmpty(mUrl)) {
            childBaseListView.onRefreshComplete();
            childBaseListView.onLoadComplete();
            return;
        }
        LoadDialog load = null;
        if (isWait) {
            if (TextUtils.isEmpty(mDialogMsg)) mDialogMsg = mActivity.getString(R.string.str_loading);
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
        if (isRefresh) index = 1;
        if (null == params) params = new HashMap<>(2);

        params.put(Constant.pageIndex, index + "");

        if (!params.containsKey(Constant.pageSize)) {//参数没设置pageSize 使用默认
            params.put(Constant.pageSize, pageSize + "");
        }
        NetUtil.postArr(dialog, mUrl, params, tClass, new BaseListCallback<LoadDialog, T>(childBaseListView) {
                    @Override
                    public List<T> onPreHanleData(List<T> netData) {
                        return onPreHandleData(netData);//数据预处理
                    }

                    @Override
                    public void onSuccess(LoadDialog tag, List<T> netData, int pageIndex, int pageTotal) {
                        super.onSuccess(tag, netData, pageIndex, pageTotal);
                        _onRefreshData(netData, isRefresh);
                        if (pageIndex >= pageTotal) {//最后一页
                            childBaseListView.setLoadFull(true);
                            index = pageIndex;
                        } else {
                            index = pageIndex + 1;
                            childBaseListView.setLoadFull(false);
                        }
                    }

                    @Override
                    public void onNoData(LoadDialog tag) {
                        super.onNoData(tag);
                        _onRefreshData(null, isRefresh);
                    }

                    @Override
                    public void onError(LoadDialog tag, int status, String errMsg) {
                        super.onError(tag, status, errMsg);
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
        List preData = onPreRefreshData(netData);
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
        mNoData.setVisibility(View.GONE);
        mLoadFail.setVisibility(View.GONE);
        childShowData.setVisibility(View.GONE);
        if (Constant.SHOW_DATA == type) {
            childShowData.setVisibility(View.VISIBLE);
        } else if (Constant.NO_DATA == type) {
            mNoData.setVisibility(View.VISIBLE);
        } else {
            mLoadFail.setVisibility(View.VISIBLE);
        }
    }

    public abstract View setContentView();
}