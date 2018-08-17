package org.basis.ui.base;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.basis.network.view.LoadDialog;
import org.basis.network.view.TitleBar;
import org.loader.utilslib.R;

/**
 * @author: BaiCQ
 * @ClassName: BaseFragment
 * @date: 2018/8/17
 * @Description: Fragment 的基类
 */
public abstract class BaseFragment extends Fragment implements IRefresh {
    protected final String TAG = this.getClass().getSimpleName();
    public BaseActivity mActivity;
    private View rootView;
    public TitleBar titleBar;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (BaseActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(setLayoutId(), null);
        titleBar = rootView.findViewById(R.id.titleBar);
        initView(rootView);
        return rootView;
    }

    /**
     * 布局的资源ID
     * @return
     */
    public abstract int setLayoutId();

    /**
     * 初始化view
     * @param parent fragment的根布局
     */
    public abstract void initView(View parent);

    /**
     * 刷新UI回调接口
     * @param obj
     */
    @Override
    public void onRefresh(Object obj) {
        Log.e(TAG, "onRefresh");
    }

    /**
     * 网络变化回调
     * @param netType 网络类型
     */
    @Override
    public void onNetRefresh(String netType) {
        Log.e(TAG, "onNetRefresh");
    }

    @Override
    public void getNetData(boolean showDialog, boolean isRefresh) {
    }

    public LoadDialog buildDailog(String dialogMsg) {
        if (TextUtils.isEmpty(dialogMsg)) return null;
        return new LoadDialog(mActivity, dialogMsg);
    }
}
