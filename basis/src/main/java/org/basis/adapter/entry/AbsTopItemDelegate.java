package org.basis.adapter.entry;

/**
 * @author: BaiCQ
 * @createTime: 2017/2/26 15:07
 * @className: BaseTopItemDelegate
 * @Description: listview中postion显示的convertView接口实现
 */
public abstract class AbsTopItemDelegate<T> extends BaseItemDelegate<T> {

    /**
     * 填充文件的id
     * @param resouceId
     */
    public AbsTopItemDelegate(int resouceId) {
        super(resouceId);
    }

    /**
     * @param item
     * @param position
     * @return
     */
    public boolean isForViewType(T item, int position){
        return position == 0;
    }
}
