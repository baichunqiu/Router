package org.basis.adapter;

import android.content.Context;


import org.basis.adapter.entry.BaseItemDelegate;

import java.util.ArrayList;

/**
 * @author: BaiCQ
 * @createTime: 2017/2/28 10:10
 * @className: BaseComplexAdapter
 * @Description: 多种类型View的通用adapter
 */
public abstract class BaseComplexAdapter<T> extends MultiItemTypeAdapter<T> {

    protected ArrayList<BaseItemDelegate<T>> mDelegates = new ArrayList();

    public BaseComplexAdapter(Context context) {
        super(context);
        addContentItemDelegate();
    }

    public void addContentItemDelegate() {
        initItemDelegate();
        int size = mDelegates.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                addItemDelegate(mDelegates.get(i));
            }
        }else{
            throw new IllegalArgumentException("No ItemDelegate added for adapter，place Override method initItemDelegate");
        }
    }

    /**
     * 初始化各种viewType类型的的ItemDelegate填充到mDelegates
     */
    protected abstract void initItemDelegate();

}
