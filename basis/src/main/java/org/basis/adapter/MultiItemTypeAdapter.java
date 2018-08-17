package org.basis.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.basis.adapter.entry.ItemDelegate;
import org.basis.adapter.entry.ItemDelegateManager;
import org.basis.adapter.entry.ViewHolder;

import java.util.List;

/**
 * @author: BaiCQ
 * @createTime: 2017/2/28 10:12
 * @className:  MultiItemTypeAdapter
 * @Description: 通用适配器:支持多类型viewType 数据和listview的显示要一致，
 *                         例如：listview的第一个条目显示搜索栏，需在数据集postion=0添加占位数据
 */
public class MultiItemTypeAdapter<T> extends AbsBaseAdapter<T> {

    private ItemDelegateManager mItemDelegateManager;

    public MultiItemTypeAdapter(Context context) {
        super(context);
        init();
    }

    public MultiItemTypeAdapter(Context context, List<T> datas) {
        super(context);
        init();
        this.mDatas = datas;
    }

    private void init() {
        mItemDelegateManager = new ItemDelegateManager();
    }

    @Override
    public void setData(List<T> datas) {
        this.mDatas = datas;
        notifyDataSetChanged();
    }

    /**
     * 添加复用view接口
     *
     * @param itemDelegate
     * @return
     */
    public MultiItemTypeAdapter addItemDelegate(ItemDelegate<T> itemDelegate) {
        mItemDelegateManager.addDelegate(itemDelegate);
        return this;
    }

    /**
     * 是否使用 ItemDelegateManager
     * @return
     */
    private boolean useItemDelegateManager() {
        return mItemDelegateManager.getItemDelegateCount() > 0;
    }

    @Override
    public int getViewTypeCount() {
        if (useItemDelegateManager())
            return mItemDelegateManager.getItemDelegateCount();
        return super.getViewTypeCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (useItemDelegateManager()) {
            int viewType = mItemDelegateManager.getItemViewType(getItemData(position), position);
            return viewType;
        }
        return super.getItemViewType(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemDelegate itemDelegate = mItemDelegateManager.getItemDelegate(getItemData(position), position);
        int layoutId = itemDelegate.getItemViewLayoutId();
        ViewHolder viewHolder = null;
        if (convertView == null) {
            View itemView = LayoutInflater.from(mContext).inflate(layoutId, parent, false);
            viewHolder = new ViewHolder(mContext, itemView, parent, position);
            viewHolder.mLayoutId = layoutId;
            onViewHolderCreated(viewHolder, viewHolder.getConvertView());
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.mPosition = position;
        }

        mConvert(viewHolder, getItem(position), position);
        return viewHolder.getConvertView();
    }

    private T getItemData(int position){
        if (null == mDatas || mDatas.isEmpty()) return null;
        if (position >= mDatas.size()) return null;
        return mDatas.get(position);
    }

    /**
     * 避免重载 修改名称convert-->mConvert
     * @param viewHolder
     * @param item
     * @param position
     */
    protected void mConvert(ViewHolder viewHolder, T item, int position) {
        mItemDelegateManager.convert(viewHolder, item, position);
    }

    public void onViewHolderCreated(ViewHolder holder, View itemView) {
    }

    @Override
    public int getCount() {
        return null == mDatas ? 0 : mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return getItemData(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
