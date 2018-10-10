package org.basis.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import org.basis.adapter.AbsBaseAdapter;
import org.basis.network.controller.IOperator;
import org.basis.network.controller.UIController;
import org.basis.network.view.LoadDialog;
import org.basis.ui.base.BaseActivity;
import org.loader.utilslib.R;

import java.util.List;
import java.util.Map;

/**
 * @author: BaiCQ
 * @createTime: 2017/1/13 11:38
 * @className: AbsListActivity
 * @Description:
 *  注意：1.onCreateContentView添加的view里面已经定义了showData控件 则使用该控件 并将控件noData和loadFail同时移动到showData控件的父控件中
 *           默认的showData就是 mLLContain
 *       2.用户必须自己实现以下三布局 @layout/title_line  @layout/no_data  @layout/load_fail
 */
public abstract class AbsListActivity<T> extends BaseActivity implements IOperator<T> {
    private UIController<T> mController;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mController) mController.release();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = LayoutInflater.from(mActivity).inflate(R.layout.activity_abs_list,null);
        setContentView(contentView);
        titleBar = contentView.findViewById(R.id.titleBar);
        mController = new BaseUIController<T>(mActivity,contentView,this);
        mController.init();
    }

    /**
     * 重新获取数据
     * @param isShow    是否显示进度条
     * @param isRefresh 是否刷新
     */
    @Override
    public final void getNetData(boolean isShow, boolean isRefresh) {
        if (null != mController) mController.getNetData(isShow, isRefresh);
    }

    public void getNetData(boolean isShow, boolean isRefresh, String mUrl, Map<String, String> params, Class<T> tClass) {
        getNetData(isRefresh, mUrl, params, tClass, isShow? buildDailog("") : null);
    }

    public void getNetData(boolean isShow, boolean isRefresh, String mUrl, Map<String, String> params, Class<T> tClass, String mDialogMsg) {
        getNetData(isRefresh, mUrl, params, tClass, isShow? buildDailog(mDialogMsg) : null);
    }

    /**
     * @param isRefresh  是否刷新
     * @param mUrl       mUrl
     * @param params     参数 注意：不包含page
     * @param tClass     解析的实体
     * @param loadDialog 进度条
     */
    public void getNetData(boolean isRefresh, String mUrl, Map<String, String> params, Class<T> tClass, LoadDialog loadDialog) {
        if (null != mController) mController.getNetData(isRefresh, mUrl, params, tClass, loadDialog);
    }

    /**
     * 设置适配器数据回调
     * @param netData   接口放回数据
     * @param isRefresh 是否刷新
     */
    public void onRefreshData(List<T> netData, boolean isRefresh) {
        if (null != mController) mController._onRefreshData(netData,isRefresh);
    }

    public void showViewType(int type) {
        if (null != mController) mController.showViewType(type);
    }

    /**
     * 接口解析数据后子线程预处理数据
     * @param netData
     */
    @Override
    public List<T> onPreHandleData(List<T> netData) {
        return netData;
    }

    /**
     * 适配器设置数据前 处理数据 有可能类型转换
     * @param netData
     */
    @Override
    public List onPreRefreshData(List<T> netData) {
        return netData;
    }

    @Override
    public abstract View onCreateContentView(LayoutInflater inflater);

    /**
     * 设置适配器
     */
    @Override
    public abstract AbsBaseAdapter setAdapter();
}