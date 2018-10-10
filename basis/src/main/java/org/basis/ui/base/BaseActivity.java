package org.basis.ui.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import org.basis.network.view.LoadDialog;
import org.basis.network.view.TitleBar;
import org.basis.utils.Logger;
import org.loader.utilslib.R;

import java.util.List;

/**
 * @author: BaiCQ
 * @ClassName: BaseActivity
 * @date: 2018/8/17
 * @Description: 基类，AbsExitActivity的空实现
 */
public class BaseActivity extends AbsExitActivity implements IRefresh{
    protected BaseActivity mActivity;
    protected TitleBar titleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
    }

    /**
     * 自定义添加action的方法
     * @param actionList 存储广播action集合
     */
    @Override
    protected void buildFilterAction(List<String> actionList) {}

    @Override
    protected void onReceive(Context context, Intent intent) {
        Logger.e(TAG, "onReceive");
    }

    @Override
    public void getNetData(boolean showDialog, boolean isRefresh) {
        Logger.e(TAG, "getNetData");
    }

    /**
     * 刷新UI回调
     * @param obj
     */
    @Override
    public void onRefresh(Object obj) {
        Logger.e(TAG, "onRefresh");
    }

    /**
     * 网络变化回调
     * @param netType 网络类型
     */
    @Override
    public void onNetRefresh(String netType) {
        Logger.e(TAG, "onNetRefresh");
    }

    public LoadDialog buildDailog(String dialogMsg){
        if (TextUtils.isEmpty(dialogMsg)) return null;
        return new LoadDialog(mActivity, dialogMsg);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // TODO: 2018/10/10  伪代码 默认处理 titlebar
        if (null == titleBar) return;
        titleBar.setTitle(TAG, R.color.color_white)
                .setRightIcon(R.mipmap.icon_add)
                .setOnLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackCode();
            }
        });
    }
}
