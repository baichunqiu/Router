
package org.basis.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import org.basis.network.controller.NetController;
import org.basis.ui.base.BaseActivity;
import org.loader.utilslib.R;

import java.util.List;
import java.util.Map;

/**
 * @author: BaiCQ
 * @createTime: 2017/1/13 11:38
 * @className: AbsActivity
 * @Description: 没有列表的activity
 */
public abstract class AbsActivity<T> extends BaseActivity {
    protected LinearLayout ll_contain;
    private View contentView;
    private NetController<T> mController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abs);
        _init();
    }

    public void _init() {
        titleBar = findViewById(R.id.titleBar);
        ll_contain = findViewById(R.id.ll_content);
        //初始化内容视图
        contentView = onCreateView(LayoutInflater.from(mActivity));
        ll_contain.addView(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        mController = new NetController<T>() {
            @Override
            public void _onResponceCallBack(String url, int state, String msg) {
                onResponceCallBack(url, state, msg);
            }

            @Override
            public void _bindData(List<T> mNetData) {
                if (null != mNetData && !mNetData.isEmpty()) {
                    bindData(mNetData.get(0));
                }
            }
        };
    }

    /**
     * post有结果集请求
     * @param mUrl
     * @param params
     * @param tClass
     * @param mDialogMsg
     */
    public void getNetData(final String mUrl, final Map<String, String> params, Class<T> tClass, final String mDialogMsg) {
        if (null != mController) mController.postArr(mUrl, params, tClass, buildDailog(mDialogMsg));
    }

    /**
     * post只有状态没有结果集的请求
     * @param mUrl
     * @param params
     * @param mDialogMsg
     */
    public void postOperate(final String mUrl, final Map<String, String> params, final String mDialogMsg) {
        if (null != mController) mController.post(mUrl, params, buildDailog(mDialogMsg));
    }

    /**
     * 接口响应回调
     * @param url   接口url 多种请求 根据url匹配响应回调
     * @param stateCode 状态 1：成功 其他：失败
     * @param msg   回调info信息
     */
    public void onResponceCallBack(String url, int stateCode, String msg) {
    }

    /**
     * 绑定数据
     * @param t 调接口获取数据后必执行此方法 故可能为null
     */
    public void bindData(T t) {
    }

    protected abstract View onCreateView(LayoutInflater inflater);
}
