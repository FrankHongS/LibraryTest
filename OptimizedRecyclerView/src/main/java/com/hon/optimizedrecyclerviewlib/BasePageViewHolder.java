package com.hon.optimizedrecyclerviewlib;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hon.optimizedrecyclerviewlib.item.PageItem;

/**
 * Created by Frank_Hon on 3/27/2019.
 * E-mail: v-shhong@microsoft.com
 */
public abstract class BasePageViewHolder<T extends PageItem> extends RecyclerView.ViewHolder {

    public BasePageViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bindView(T item);
}
