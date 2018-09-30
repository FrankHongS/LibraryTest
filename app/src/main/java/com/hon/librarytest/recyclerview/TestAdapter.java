package com.hon.librarytest.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Frank_Hon on 9/30/2018.
 * E-mail: v-shhong@microsoft.com
 */
public class TestAdapter extends RecyclerView.Adapter{

    private Context mContext;
    private List<String> mDataList;

    public TestAdapter(Context context, List<String> dataList) {
        this.mContext = context;
        this.mDataList = dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    class TextViewHolder extends RecyclerView.ViewHolder{
        
        public TextViewHolder(View itemView) {
            super(itemView);
        }
    }
}
