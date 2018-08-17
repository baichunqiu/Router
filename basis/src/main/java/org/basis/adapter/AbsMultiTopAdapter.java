package org.basis.adapter;

import android.content.Context;

import org.basis.adapter.entry.ItemDelegate;
import org.basis.adapter.entry.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: BaiCQ
 * @createTime: 2017/2/28 10:09
 * @className: AbsMultiTopAdapter
 * @Description: position = 0 是特殊处理的 通用adapter
 */
public abstract class AbsMultiTopAdapter<T> extends MultiItemTypeAdapter<T> {
    protected int layoutId;

    public AbsMultiTopAdapter(Context context, final int layoutId) {
        super(context);
        this.layoutId = layoutId;
        addTopItemDelegate();
        //添加数据显示viewtype类型的
        addItemDelegate(new ItemDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return layoutId;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return position > 0;
            }

            @Override
            public void convert(ViewHolder holder, T t, int position) {
                AbsMultiTopAdapter.this.convert(holder, t, position);
            }
        });
    }

    /**
     * 添加头布局的itemView 默认实现
     * 子类有应Override
     */
    protected void addTopItemDelegate() {

    }

    @Override
    public void setData(List<T> mDatas) {
        if (null == mDatas){
            mDatas = new ArrayList<>();
        }
        mDatas.add(0,(T)null);//占位position = 0的位置
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    protected abstract void convert(ViewHolder viewHolder, T item, int position);
}
