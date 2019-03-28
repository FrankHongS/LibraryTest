package com.hon.optimizedrecyclerviewlib;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.hon.optimizedrecyclerviewlib.item.PageItem;

/**
 * Created by Frank_Hon on 3/27/2019.
 * E-mail: v-shhong@microsoft.com
 */
public abstract class BasePageViewHolder<T extends PageItem> extends RecyclerView.ViewHolder {

    public BasePageViewHolder(View itemView) {
        this(itemView,
                (int) itemView.getContext().getResources().getDimension(R.dimen.page_recycler_item_default_height));
    }

    public BasePageViewHolder(View itemView,int itemHeight) {
        super(itemView);

        ViewGroup.LayoutParams layoutParams=new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                itemHeight
        );

        itemView.setLayoutParams(layoutParams);
    }

    public abstract void bindView(T item);
}
