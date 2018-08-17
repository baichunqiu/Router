package org.basis.adapter.entry;


/**
 * @author: BaiCQ
 * @createTime: 2017/2/26 15:07
 * @className:  ItemDelegate
 * @Description: 接口 adapter的复用控件convertView代表 每个类型的view都对应一个实例
 */
public interface ItemDelegate<T> {
    /**
     * 填充该itemView的布局文件id
     * @return
     */
    public abstract int getItemViewLayoutId();

    /**
     * 什么条件下显示该itemView 用户通过item 和 position判断
     * @param item
     * @param position
     * @return
     */
    public abstract boolean isForViewType(T item, int position);

    /**
     * 复用 重新设置itemView的数据
     * @param holder
     * @param t
     * @param position
     */
    public abstract void convert(ViewHolder holder, T t, int position);

}
