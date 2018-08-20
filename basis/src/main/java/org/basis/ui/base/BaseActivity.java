package org.basis.ui.base;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import org.basis.network.view.LoadDialog;
import org.basis.network.view.TitleBar;
import org.basis.utils.ToastManager;
import org.loader.utilslib.R;

/**
 * @author: BaiCQ
 * @ClassName: AbsBaseActivity
 * @date: 2018/8/17
 * @Description: 基类 AbsBaseActivity的空实现
 */
public class BaseActivity extends AbsBaseActivity {
    public BaseActivity mActivity;
    protected TitleBar titleBar;

    @Override
    protected void onResume() {
        super.onResume();
        titleBar = findViewById(R.id.titleBar);
        if (null == titleBar) return;
        titleBar.setDefault(mActivity,TAG,"more",new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ToastManager.show("more");
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
    }

    /**
     * 自定义添加action的方法
     * @param intentfilter
     */
    @Override
    protected void buildFilterAction(IntentFilter intentfilter) {
    }

    /**
     * 处理自定义action的onReceive方法
     * @param context
     * @param intent
     */
    @Override
    protected void onReceive(Context context, Intent intent) {
    }

    @Override
    public void getNetData(boolean showDialog, boolean isRefresh) {
    }

    /**
     * 刷新UI回调
     * @param obj
     */
    @Override
    public void onRefresh(Object obj) {
    }

    /**
     * 网络变化回调
     * @param netType 网络类型
     */
    @Override
    public void onNetRefresh(String netType) {
    }

    public LoadDialog buildDailog(String dialogMsg){
        if (TextUtils.isEmpty(dialogMsg)) return null;
        return new LoadDialog(mActivity, dialogMsg);
    }

    protected  void initView(){};
}
