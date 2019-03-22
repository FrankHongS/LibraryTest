package com.hon.librarytest.pagerecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hon.librarytest.R;

import java.util.List;

/**
 * Created by Frank_Hon on 3/22/2019.
 * E-mail: v-shhong@microsoft.com
 */
public class PageRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder<String>> {

    private Context mContext;
    private List<String> mDataList;

    public PageRecyclerAdapter(Context context,List<String> list){
        this.mContext=context;
        this.mDataList=list;
    }

    @Override
    public BaseViewHolder<String> onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(mContext)
                .inflate(R.layout.item_page_recycler,parent,false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder<String> holder, int position) {
        holder.bindView(mDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }
}
