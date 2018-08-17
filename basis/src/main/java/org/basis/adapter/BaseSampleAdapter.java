package org.basis.adapter;

import android.content.Context;

import org.basis.adapter.entry.BaseItemDelegate;
import org.basis.adapter.entry.ViewHolder;

/**
 * @author: BaiCQ
 * @createTime: 2017/2/28 10:11
 * @className:  BaseSampleAdapter
 * @Description: 简单 只有一种viewType通用adapter 只需重写convert
 */
public abstract class BaseSampleAdapter<T> extends MultiItemTypeAdapter<T> {

    protected int layoutId;

    public BaseSampleAdapter(Context context, final int layoutId) {
        super(context);
        this.layoutId = layoutId;
        addContentItemDelegate();
    }

    /**
     * default implementation:add a ItemDelegate;
     * if need,can Override
     */
    public void addContentItemDelegate(){
        addItemDelegate(new BaseItemDelegate<T>(layoutId) {
            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(ViewHolder holder, T t, int position) {
                BaseSampleAdapter.this.convert(holder, t, position);
            }
        });
    }

    /**
     * convertView的复用逻辑
     * @param viewHolder convertView对应的ViewHolder
     * @param item 数据类型的实体
     * @param position adpater中的位置索引
     */
    protected abstract void convert(ViewHolder viewHolder, T item, int position);

}
