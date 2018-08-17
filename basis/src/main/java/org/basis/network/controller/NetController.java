package org.basis.network.controller;


import org.basis.network.listener.net.callback.BaseCallback;
import org.basis.network.listener.net.callback.BaseListCallback;
import org.basis.network.tools.NetUtil;
import org.basis.network.view.LoadDialog;

import java.util.List;
import java.util.Map;

/**
 * @author: BaiCQ
 * @createTime: 2017/1/13 11:38
 * @className: NetController
 * @Description: 供没有listview的页面使用的控制器
 */
public abstract class NetController<T> {
    private final static String Tag = "NetController";
    private List<T> mNetData;

    /**
     * @param mUrl       mUrl
     * @param params     参数 注意：不包含page
     * @param tClass     解析的实体
     * @param dialog     进度条
     */
    public void postArr(final String mUrl, final Map<String, String> params, Class<T> tClass, LoadDialog dialog) {
        //缓存参数
        NetUtil.postArr(dialog, mUrl, params, tClass, new BaseListCallback<LoadDialog, T>(null) {
                    int state = 0;
                    String msg = "";
                    @Override
                    public void onSuccess(LoadDialog tag, List<T> netData, int pageIndex, int pageTotal) {
                        super.onSuccess(tag, netData, pageIndex, pageTotal);
                        mNetData = netData;
                        state = 1;
                    }

                    @Override
                    public void onError(LoadDialog tag, int status, String errMsg) {
                        super.onError(tag, status, errMsg);
                        state = status;
                        msg = errMsg;
                    }

                    @Override
                    public void onAfter(LoadDialog tag) {
                        super.onAfter(tag);
                        _bindData(mNetData);
                        _onResponceCallBack(mUrl, state, msg);
                    }
                }
        );
    }

    /**
     * @param mUrl       url
     * @param params     参数
     * @param dialog    进度条
     */
    public void post(final String mUrl, final Map<String, String> params, LoadDialog dialog) {
        NetUtil.post(dialog, mUrl, params, new BaseCallback<LoadDialog>() {
                    @Override
                    public void onSuccess(LoadDialog tag, int status) {
                        super.onSuccess(tag, status);
                        _onResponceCallBack(mUrl, status, "");
                    }

                    @Override
                    public void onError(LoadDialog tag, int status, String errMsg) {
                        super.onError(tag, status, errMsg);
                        _onResponceCallBack(mUrl, status, errMsg);
                    }
                }
        );
    }

    /**
     * 接口响应回调
     * @param url   接口连接
     * @param state 状态
     * @param msg   服务器返回msg
     */
    public abstract void _onResponceCallBack(String url, int state, String msg);

    /**
     * 绑定数据
     * @param mNetData
     */
    public abstract void _bindData(List<T> mNetData);
}