package org.basis.network.listener.net;

import android.text.TextUtils;

import com.zhy.http.okhttp.callback.Callback;

import org.basis.CommonHelper;
import org.basis.network.ParseBean;
import org.basis.network.Parser;
import org.basis.network.listener.net.callback.ICallback;
import org.basis.network.utils.NetUtil;
import org.basis.network.view.LoadDialog;
import org.basis.utils.Logger;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @author: BaiCQ
 * @ClassName: BusinessSampleCallBack
 * @Description: 业务处理 没有body的回调
 */
public class BusinessSampleCallBack<T> extends Callback<Integer> {
    private final String Tag = this.getClass().getSimpleName();
    private ICallback<T> iCallback;
    private T tag;
    private int status = 0;
    private String sysMsg = "";
    private Parser parser;

    public BusinessSampleCallBack(T tag, Parser parser,ICallback<T> iCallback) {
        this.iCallback = iCallback;
        this.tag = tag;
        this.parser = null == parser ? CommonHelper.getParser() : parser;
    }

    @Override
    public Integer parseNetworkResponse(Response response, int id) throws Exception {
        // 请求提示语
        String result = response.body().string();
        if (!TextUtils.isEmpty(result)) {
            Logger.e(Tag,"result = "+result);
            JSONObject resulObject = new JSONObject(result);
            if (null != parser){
                ParseBean parseBean = parser.parse(resulObject);
                if (null != parseBean) {
                    sysMsg = parseBean.getSysMsg();
                    status = parseBean.getStatus();
                }
            }
        }
        return status;
    }

    @Override
    public void onResponse(Integer statue, int id) {
        if (NetUtil.CODE_OK <= statue) {
            iCallback.onSuccess(tag, statue);
        } else {
            iCallback.onError(tag, statue, sysMsg);
        }
    }

    @Override
    public void onError(Call call, Exception e, int id) {
        Logger.e(Tag,"onError: "+e.getMessage());
        iCallback.onError(tag, status, e.getMessage());
    }

    @Override
    public void onAfter(int id) {
        if (null != tag && tag instanceof LoadDialog) {
            ((LoadDialog) tag).dismiss();
        }
    }
}
