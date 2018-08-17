package org.basis.adapter;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class AbsBaseAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected List<T> mDatas;

    public AbsBaseAdapter(Context context) {
        this.mContext = context;
    }

    public List<T> getData() {
        return mDatas;
    }

    /**
     * 设置数据
     * @param list
     */
    public abstract void setData(List<T> list);

    /**
     * 设置数据 和 检索内容
     * @param list   数据集
     * @param search 搜索内容
     */
    public void setData(List<T> list, String search) {}

    public void release(){
        if (null != mDatas)mDatas.clear();
    }

}
