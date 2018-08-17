package org.basis.ui;

import android.view.LayoutInflater;
import android.view.View;

import org.basis.adapter.AbsBaseAdapter;
import org.basis.network.controller.UIController;
import org.basis.ui.base.BaseFragment;
import org.loader.utilslib.R;

import java.util.List;
import java.util.Map;

/**
 * @author: BaiCQ
 * @createTime: 2017/1/13 11:38
 * @className: AbsListFragment
 * @Description:
 */
public abstract class AbsListFragment<T> extends BaseFragment {

    private UIController<T> mController;

    @Override
    public int setLayoutId() {
        return R.layout.fragment_abs_list;
    }

    @Override
    public void initView(final View parent) {
        mController = new BaseUIController<T>(mActivity,parent,this);
        mController.init();
    }

    @Override
    public void getNetData(boolean showDialog, boolean isRefresh) {
        if (null == mController) return;
        mController.getNetData(showDialog, isRefresh);
    }

    public void getNetData(boolean isRefresh, String url, Map<String, String> params, Class<T> tClass, String dialogMsg) {
        if (null == mController) return;
        mController.getNetData(isRefresh, url, params, tClass, buildDailog(dialogMsg));
    }

    /**
     * 预处理数据
     * @param netData
     * @return
     */
    public List<T> preHandleData(List<T> netData) {
        return netData;
    }

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
     * init ContentView  mController.init() 执行
     * @return
     */
    public abstract View addContentView(LayoutInflater inflater);

    /**
     * 初始化之类控件  mController.init() 执行
     * @param parent
     * @return
     */
    public abstract void initChildView(View parent);

    /**
     * 设置适配器  mController.init() 执行
     * @return
     */
    public abstract AbsBaseAdapter setAdapter();
}
