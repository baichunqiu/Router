package org.basis.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import org.basis.adapter.AbsBaseAdapter;
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
 *  注意：1.addContentView添加的view里面已经定义了showData控件 则使用该控件 并将控件noData和loadFail同时移动到showData控件的父控件中
 *           默认的showData就是 ll_contain
 *       2.用户必须自己实现以下三布局 @layout/title_line  @layout/no_data  @layout/load_fail
 */
public abstract class AbsListActivity<T> extends BaseActivity {
    private UIController<T> mController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = LayoutInflater.from(this).inflate(R.layout.activity_abs_list,null);
        setContentView(contentView);
        titleBar = contentView.findViewById(R.id.titleBar);
        mController = new BaseUIController<T>(this,contentView,this);
        mController.init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mController)mController.release();
    }

    /**
     * 重新获取数据
     *
     * @param isWait    是否显示进度条
     * @param isRefresh 是否刷新
     */
    public void getNetData(boolean isWait, boolean isRefresh) {
        if (null == mController) return;
        mController.getNetData(isWait, isRefresh);
    }

    public void getNetData(boolean isWait, boolean isRefresh, String mUrl, Map<String, String> params, Class<T> tClass) {
        getNetData(isWait, isRefresh, mUrl, params, tClass, "");
    }

    /**
     * @param isShow     是否显示进度调
     * @param isRefresh  是否刷新
     * @param mUrl       mUrl
     * @param params     参数 注意：不包含page
     * @param tClass     解析的实体
     * @param mDialogMsg 进度条显示信息
     */
    public void getNetData(final boolean isShow, final boolean isRefresh, final String mUrl, final Map<String, String> params, Class<T> tClass, final String mDialogMsg) {
        if (null == mController) return;
        String dialogMsg = mDialogMsg;
        //加载进度条
        LoadDialog loadDialog = null;
        if (isShow) {
            loadDialog = buildDailog(dialogMsg);
        }
        mController.getNetData(isRefresh, mUrl, params, tClass, loadDialog);
    }

    /**
     * 接口解析数据后子线程预处理数据
     * @param netData
     * @return
     */
    public List<T> preHandleData(List<T> netData) {
        return netData;
    }

    /**
     * 设置适配器数据回调
     * @param netData   接口放回数据
     * @param isRefresh 是否刷新
     */
    public void onRefreshData(List<T> netData, boolean isRefresh) {
        if (null == mController) return;
        mController._onRefreshData(netData,isRefresh);
    }

    /**
     * 适配器设置数据前 处理数据 有可能类型转换
     * @param netData
     * @return
     */
    public List preRefreshData(List<T> netData) {
        return netData;
    }

    public void showViewType(int type) {
        if (null == mController) return;
        mController.showViewType(type);
    }

    /**
     * init ContentView
     * @return
     */
    public abstract View addContentView(LayoutInflater inflater);

    /**
     * 初始化view
     * @param parent
     */
    public abstract void initView(View parent);

    /**
     * 设置适配器
     * @return
     */
    public abstract AbsBaseAdapter setAdapter();
}