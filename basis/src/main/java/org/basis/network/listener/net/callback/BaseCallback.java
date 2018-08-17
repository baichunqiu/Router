package org.basis.network.listener.net.callback;

/**
 * @author: BaiCQ
 * @ClassName: ICallback
 * @Description: 没有body请求回调
 */
public class BaseCallback<T> implements ICallback<T> {
    /**
     * @param tag    Tag
     * @param status
     */
    public void onSuccess(T tag, int status) {

    }

    /**
     * @param tag    Tag
     * @param errMsg 错误信息
     */
    public void onError(T tag, int status, String errMsg) {

    }

}
