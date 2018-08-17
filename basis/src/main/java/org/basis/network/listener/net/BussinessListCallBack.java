package org.basis.network.listener.net;

import android.text.TextUtils;
import android.util.Log;

import com.zhy.http.okhttp.callback.Callback;

import org.basis.CommonHelper;
import org.basis.network.ParseBean;
import org.basis.network.Parser;
import org.basis.network.listener.net.callback.IListCallback;
import org.basis.network.tools.GsonUtil;
import org.basis.network.tools.NetUtil;
import org.basis.network.view.LoadDialog;
import org.json.JSONObject;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @author: BaiCQ
 * @ClassName: BussinessListCallBack
 * @Description: 业务处理回调
 */
public class BussinessListCallBack<T, R> extends Callback<List<R>> {
    private final String Tag = this.getClass().getSimpleName();
    private IListCallback<T, R> iListCallback;
    private T tag;
    private Class<R> rClass;
    private Parser parser;

    public BussinessListCallBack(T tag, Class<R> rClass, Parser parser,IListCallback<T, R> iListCallback) {
        this.iListCallback = iListCallback;
        this.tag = tag;
        this.rClass = rClass;
        //没有设置解析器 使用默认
        this.parser = null == parser ? CommonHelper.getParser() : parser;
    }
    private int status = 0;
    private String sysMsg = "";
    private int pageIndex = 0;
    private int pageTotal = 0;

    @Override
    public List<R> parseNetworkResponse(Response response, int id) throws Exception {
        // 请求提示语
        String result = response.body().string();
        List<R> resultData = null;
        if (!TextUtils.isEmpty(result)) {
            Log.e(Tag,"result = "+result);
            JSONObject resulObject = new JSONObject(result);
            if (null != parser){
                ParseBean parseBean = parser.parse(resulObject);
                if (null != parseBean) {
                    sysMsg = parseBean.getSysMsg();
                    status = parseBean.getStatus();
                    pageIndex = parseBean.getPageIndex();
                    pageTotal = parseBean.getPageTotal();
                    //body 原始数据解析
                    resultData = GsonUtil.json2List(parseBean.getBody(), rClass);
                    //预处理数据
                    resultData = iListCallback.onPreHanleData(resultData);
                }
            }
        }
        return resultData;
    }

    @Override
    public void onResponse(List<R> response, int id) {
        if (null != response) {
            if (!response.isEmpty()) {
                iListCallback.onSuccess(tag, response, pageIndex, pageTotal);
            } else {
                iListCallback.onNoData(tag);
            }
        } else {
            iListCallback.onError(tag, status, NetUtil._unKnow_error);
        }
    }

    @Override
    public void onError(Call call, Exception e, int id) {
        iListCallback.onError(tag, status, e.getMessage());
    }

    @Override
    public void onAfter(int id) {
        if (null != tag && tag instanceof LoadDialog) {
            ((LoadDialog) tag).dismiss();
        }
        iListCallback.onAfter(tag);
    }
}
