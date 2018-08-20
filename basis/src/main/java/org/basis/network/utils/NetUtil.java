package org.basis.network.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.FileCallBack;

import org.basis.UIUtil;
import org.basis.common.Constant;
import org.basis.network.Parser;
import org.basis.network.listener.net.BusinessSampleCallBack;
import org.basis.network.listener.net.BussinessListCallBack;
import org.basis.network.listener.net.callback.ICallback;
import org.basis.network.listener.net.callback.IListCallback;
import org.basis.network.view.LoadDialog;
import org.basis.utils.Logger;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * @author: BaiCQ
 * @ClassName: OkUtil
 * @Description: OkHttp网络请求工具类
 */
public class NetUtil {
    public final static String TAG = "NetUtil";
    public final static int CODE_OK = 0;
    public final static String _unKnow_error = "未知错误!";

    private static void logParams(String msg, String url, Map<String, String> params) {
        Logger.e(TAG, msg + " : start---------------------------------");
        Logger.e(TAG, msg + " : url = " + url);
        int size = params.size();
        if (size > 0) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                Logger.i(TAG, msg + " : " + entry.getKey() + " = " + entry.getValue());
            }
        }
        Logger.e(TAG, msg + " : end---------------------------------");
    }

    private static <T> GetBuilder initGetBuider(T tag, String url, Map<String, String> params) {
        if (null == params) params = new HashMap<>(2);
        logParams("GET", url, params);
        return OkHttpUtils.get()
                .url(url)
                .tag(tag)
                .params(params);

    }

    private static <T> PostFormBuilder initPostBuilder(T tag, String url, Map<String, String> params) {
        if (null == params) params = new HashMap<>(2);
        logParams("POST", url, params);
        return OkHttpUtils.post()
                .url(url)
                .tag(tag)
                .params(params);
    }

    public static <T, R> void postArr(String url, Map<String, String> params, Class<R> tClazz, IListCallback<T, R> iListCallback) {
        postArr(null, url, params, tClazz, null, iListCallback);
    }

    public static <T, R> void postArr(T tag, String url, Map<String, String> params, Class<R> tClazz, IListCallback<T, R> iListCallback) {
        postArr(tag, url, params, tClazz, null, iListCallback);
    }

    /**
     * @param tag           请求的tag
     * @param url           url
     * @param params        Map<String,String>
     * @param tClazz        解析字段的class
     * @param parser        解析器
     * @param iListCallback 有body的网络回调
     * @param <T>           body实体的泛型
     * @param <R>           tag的泛型
     */
    public static <T, R> void postArr(T tag, String url, Map<String, String> params, Class<R> tClazz, Parser parser, IListCallback<T, R> iListCallback) {
        if (!checkNet(tag)) {
            iListCallback.onAfter(tag);
            return;
        }
        initPostBuilder(tag, url, params)
                .build()
                .execute(new BussinessListCallBack(tag, tClazz, parser, iListCallback));
    }

    public static <T, R> void getArr(String url, Map<String, String> params, Class<R> tClazz, IListCallback<T, R> iListCallback) {
        getArr(null, url, params, tClazz, null, iListCallback);
    }

    public static <T, R> void getArr(T tag, String url, Map<String, String> params, Class<R> tClazz, IListCallback<T, R> iListCallback) {
        getArr(tag, url, params, tClazz, null, iListCallback);
    }

    /**
     * @param tag           请求的tag
     * @param url           url
     * @param params        Map<String,String>
     * @param rClass        解析字段的class
     * @param parser        解析器
     * @param iListCallback 有body的网络回调
     * @param <T>           body实体的泛型
     * @param <R>           tag的泛型
     */
    public static <T, R> void getArr(T tag, String url, Map<String, String> params, Class<R> rClass, Parser parser, IListCallback<T, R> iListCallback) {
        if (!checkNet(tag)) {
            iListCallback.onAfter(tag);
            return;
        }
        initGetBuider(tag, url, params)
                .build()
                .execute(new BussinessListCallBack(tag, rClass, parser, iListCallback));
    }


    public static <T> void post(String url, Map<String, String> params, ICallback<T> iCallback) {
        post(null, url, params, null, iCallback);
    }

    public static <T> void post(T tag, String url, Map<String, String> params, ICallback<T> iCallback) {
        post(tag, url, params, null, iCallback);
    }

    /**
     * @param tag       请求的tag
     * @param url       url
     * @param params    Map<String,String>
     * @param parser    解析器
     * @param iCallback 无body的网络回调
     * @param <T>       tag的泛型
     */
    public static <T> void post(T tag, String url, Map<String, String> params, Parser parser, ICallback<T> iCallback) {
        if (!checkNet(tag)) {
            return;
        }
        initPostBuilder(tag, url, params)
                .build()
                .execute(new BusinessSampleCallBack(tag, parser, iCallback));
    }

    public static <T> void get(String url, Map<String, String> params, ICallback<T> iCallback) {
        get(null, url, params, null, iCallback);
    }

    public static <T> void get(T tag, String url, Map<String, String> params, ICallback<T> iCallback) {
        get(tag, url, params, null, iCallback);
    }

    /**
     * @param tag       请求的tag
     * @param url       url
     * @param params    Map<String,String>
     * @param parser    解析器
     * @param iCallback 无body的网络回调
     * @param <T>       tag的泛型
     */
    public static <T> void get(T tag, String url, Map<String, String> params, Parser parser, ICallback<T> iCallback) {
        if (!checkNet(tag)) {
            return;
        }
        initGetBuider(tag, url, params)
                .build()
                .execute(new BusinessSampleCallBack(tag, parser, iCallback));
    }

    /**
     * okhttp 直接显示image 没有缓存
     *
     * @param url
     * @param params
     * @param imageView
     */
    public static void display(String url, Map<String, String> params, final ImageView imageView) {
        initGetBuider(null, url, params)
                .build()
                .execute(new BitmapCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(Bitmap bitmap, int id) {
                        imageView.setImageBitmap(bitmap);
                    }
                });
    }

    /**
     * 下载文件
     *
     * @param tag
     * @param url
     * @param params
     * @param fileName 文件名称 存放在downLoad 目录下
     * @param <T>
     */
    public static <T> void downLoad(T tag, String url, Map<String, String> params, String fileName, final ProgressBar mProgressBar) {
        initGetBuider(tag, url, params)
                .build()
                .execute(new FileCallBack(Constant.BASE_PATH, fileName) {
                    @Override
                    public void onError(Call call, Exception e, int id) {}

                    @Override
                    public void onResponse(File response, int id) {}

                    @Override
                    public void inProgress(float progress, long total, int id) {
                        super.inProgress(progress, total, id);
                        mProgressBar.setProgress((int) (100 * progress));
                    }
                });
    }

    /**
     * 检查网 并根据tag的类型 取消加载动画
     *
     * @param tag 请求的TAG
     * @return
     */
    public static <T> boolean checkNet(T tag) {
        boolean connected = isNetworkConnected();
        if (!connected && null != tag && tag instanceof LoadDialog) {
            ((LoadDialog) tag).dismiss();
        }
        return connected;
    }

    /**
     * 是否连接上网络
     * @return 连接上true，未连接上false
     * @date: 2017-1-16 下午17:52
     */
    public static boolean isNetworkConnected() {
        // 网络连接的状态
        boolean isConnected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) UIUtil.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            // 建立网络数组
            NetworkInfo[] net_info = connectivityManager.getAllNetworkInfo();
            if (net_info != null) {
                for (int i = 0; i < net_info.length; i++) {
                    // 判断获得的网络状态是否是处于连接状态
                    if (net_info[i].getState() == NetworkInfo.State.CONNECTED) {
                        isConnected = true;
                        break;
                    }
                }
            }
        }
        return isConnected;
    }
}
