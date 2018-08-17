package org.basis.adapter.entry;

/**
 * @author: BaiCQ
 * @createTime: 2017/2/26 15:07
 * @className: BaseItemDelegate
 * @Description: 接口ItemDelegate的抽象实现
 */
public abstract class BaseItemDelegate<T> implements ItemDelegate<T> {

    private int layoutId;

    /**
     * 填充View的布局id
     * @param layoutId
     */
    public BaseItemDelegate(int layoutId) {
        this.layoutId = layoutId;
    }

    /**
     * 填充该itemView的布局文件id
     *
     * @return
     */
    public int getItemViewLayoutId() {
        return layoutId;
    }

    /**
     * 什么条件下显示该ViewType  用户通过item 和 position判断
     * @param item
     * @param position
     * @return
     */
    public abstract boolean isForViewType(T item, int position);

    /**
     * 复用 重新设置itemView的数据
     * @param holder   ViewHolder
     * @param t        实体数据
     * @param position 在适配器中的位置
     */
    public abstract void convert(ViewHolder holder, T t, int position);

}
