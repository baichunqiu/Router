package org.basis.ui.base;

/**
 * @author: BaiCQ
 * @ClassName: IRefresh
 * @Description: 刷新UI的接口
 */
public interface IRefresh {

    /**
     * 刷新UI回调接口
     *
     * @param obj
     */
    void onRefresh(Object obj);

    /**
     * 网络变化回调接口
     *
     * @param netType
     */
    void onNetRefresh(String netType);

    /**
     * 获取网络数据接口
     *
     * @param showDialog 是否显示Dialog
     * @param isRefresh  是否刷新
     */
    void getNetData(boolean showDialog, boolean isRefresh);
}
