package com.hon.librarytest.paging;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.DiffCallback;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by Frank_Hon on 9/21/2018.
 * E-mail: v-shhong@microsoft.com
 */
public class CheeseAdapter extends PagedListAdapter{
    public CheeseAdapter(@NonNull DiffCallback diffCallback) {
        super(diffCallback);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }
}
