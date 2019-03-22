package com.hon.librarytest.pagerecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Frank_Hon on 3/22/2019.
 * E-mail: v-shhong@microsoft.com
 */
public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {
    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bindView(T item);
}
