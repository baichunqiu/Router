package org.basis.network.listener.net.callback;


import org.basis.network.view.BaseListView;

import java.util.List;

/**
 * @author: BaiCQ
 * @ClassName: BaseListCallback
 * @Description: 有body网络请求的回调
 */
public class BaseListCallback<T, R> implements IListCallback<T, R> {
    private BaseListView baseListView;

    public BaseListCallback(BaseListView baseListView) {
        this.baseListView = baseListView;
    }

    @Override
    public List<R> onPreHanleData(List<R> netData) {
        return netData;
    }

    /**
     * @param tag       Tag
     * @param netData   网络数据
     * @param pageIndex 当前页码<分页使用>
     */
    public void onSuccess(T tag, List<R> netData, int pageIndex, int pageTotal) {
        if (null != baseListView) {
            baseListView.setLoadFull(pageIndex >= pageTotal);
        }
    }

    /**
     * @param tag    Tag
     * @param errMsg 错误信息
     */
    public void onError(T tag, int status, String errMsg) {

    }

    public void onNoData(T tag) {

    }

    @Override
    public void onAfter(T tag) {
        if (null != baseListView) {
            baseListView.onRefreshComplete();
            baseListView.onLoadComplete();
        }
    }
}
