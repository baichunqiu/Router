package org.basis.network.listener.net.callback;

/**
 * @author: BaiCQ
 * @ClassName: ICallback
 * @Description: 没有body网络请求回调接口
 */
public interface ICallback<T>{

    /**
     * @param tag Tag
     * @param status 状态
     */
     void onSuccess(T tag, int status);

    /**
     * @param tag Tag
     * @param status 状态
     * @param errMsg 错误信息
     */
     void onError(T tag, int status, String errMsg);
}
