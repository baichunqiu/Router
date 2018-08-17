package org.basis.ui;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;


import org.basis.adapter.AbsBaseAdapter;
import org.basis.network.controller.UIController;
import org.basis.ui.base.IRefresh;

import java.util.List;

/**
 * @author: BaiCQ
 * @ClassName:
 * @Description:
 */
public class BaseUIController<T> extends UIController<T> {
    private final static String Tag = "BaseUIController";
    private boolean isActivity = false;
    private View contentView;
    private AbsListActivity<T> aOpenator;
    private AbsListFragment<T> fOpenator;

    public <O extends IRefresh> BaseUIController(Activity activity, View parent, O operator) {
        super(activity);
        this.contentView = parent;
        if (operator instanceof Activity) {
            isActivity = true;
            aOpenator = (AbsListActivity) operator;
        } else{
            isActivity = false;
            fOpenator = (AbsListFragment) operator;
        }
    }

    @Override
    public View setContentView() {
        if (null == contentView){
            throw new IllegalArgumentException("No contentView added for UI");
        }
        return contentView;
    }

    @Override
    public View initContainView(LayoutInflater inflater) {
        View contentView;
        if (isActivity) {
            contentView = aOpenator.addContentView(inflater);
        } else {
            contentView = fOpenator.addContentView(inflater);
        }
        return contentView;
    }

    @Override
    public void initChildView(View parent) {
        if (isActivity) {
            aOpenator.initView(parent);
        } else {
            fOpenator.initChildView(parent);
        }
    }

    @Override
    public AbsBaseAdapter setAdapter() {
        AbsBaseAdapter absBaseAdapter;
        if (isActivity) {
            absBaseAdapter = aOpenator.setAdapter();
        } else {
            absBaseAdapter = fOpenator.setAdapter();
        }
        return absBaseAdapter;
    }

    @Override
    public List<T> preHandleData(List<T> netData) {
        List<T> tList;
        if (isActivity) {
            tList = aOpenator.preHandleData(netData);
        } else {
            tList = fOpenator.preHandleData(netData);
        }
        return tList;
    }

    @Override
    public List preRefreshData(List<T> netData) {
        List resultList;
        if (isActivity) {
            resultList = aOpenator.preRefreshData(netData);
        } else {
            resultList = fOpenator.preRefreshData(netData);
        }
        return resultList;
    }
}
