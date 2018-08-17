package org.basis.network.listener.net.callback;

import java.util.List;

/**
 * @author: BaiCQ
 * @ClassName: IListCallback
 * @Description: 有body网络请求的回调
 */
public interface IListCallback<T,R>{

    /**
     * 数据预处理
     * @param netData
     * @return
     */
    List<R> onPreHanleData(List<R> netData);
    /**
     * @param tag Tag
     * @param netData 网络数据
     * @param pageIndex 当前页码<分页使用>
     * @param pageTotal 总页码<分页使用>
     */
     void onSuccess(T tag, List<R> netData, int pageIndex, int pageTotal);

    /**
     * @param tag Tag
     * @param errMsg 错误信息
     */
     void onError(T tag, int state, String errMsg);

    /**
     * noData 回调
     * @param tag
     */
     void onNoData(T tag);

    /**
     * @param tag
     */
     void onAfter(T tag);
}
