package com.hon.librarytest.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Frank_Hon on 10/22/2018.
 * E-mail: v-shhong@microsoft.com
 */
public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {
    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bindView(T data);
}
