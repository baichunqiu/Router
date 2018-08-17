package org.loader.module2module;

import android.content.Context;

import org.basis.adapter.BaseSampleAdapter;
import org.basis.adapter.entry.ViewHolder;


public class NetAdapter extends BaseSampleAdapter<ResponBean> {
    public NetAdapter(Context context) {
        super(context, R.layout.layout_net);
    }

    @Override
    protected void convert(ViewHolder viewHolder, ResponBean item, int position) {
        viewHolder.setText(R.id.tv_id, item.get_id());
        viewHolder.setText(R.id.tv_content, item.getContent());
        viewHolder.setText(R.id.tv_create, item.getCreate());
    }
}
