package org.basis.ui;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;

import org.basis.adapter.AbsBaseAdapter;
import org.basis.network.controller.IOperator;
import org.basis.network.controller.UIController;

import java.util.List;

/**
 * @author: BaiCQ
 * @ClassName:
 * @Description:
 */
public class BaseUIController<T> extends UIController<T> {
    private View contentView;
    private IOperator<T> operator;

    public BaseUIController(Activity activity, View parent, IOperator<T> operator) {
        super(activity);
        this.contentView = parent;
        this.operator = operator;
    }

    @Override
    public View setContentView() {
        if (null == contentView){
            throw new IllegalArgumentException("contentView can not NULL");
        }
        return contentView;
    }

    @Override
    public View onCreateContentView(LayoutInflater inflater) {
        if (null == operator) return null;
        return operator.onCreateContentView(inflater);
    }

    @Override
    public AbsBaseAdapter setAdapter() {
        if (null == operator) return null;
        return operator.setAdapter();
    }

    @Override
    public List<T> onPreHandleData(List<T> netData) {
        if (null == operator) return null;
        return operator.onPreHandleData(netData);
    }

    @Override
    public List onPreRefreshData(List<T> netData) {
        if (null == operator) return null;
        return operator.onPreRefreshData(netData);
    }
}
