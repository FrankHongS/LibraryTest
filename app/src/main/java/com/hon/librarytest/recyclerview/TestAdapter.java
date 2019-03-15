package com.hon.librarytest.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hon.librarytest.R;

import java.util.List;

/**
 * Created by Frank_Hon on 9/30/2018.
 * E-mail: v-shhong@microsoft.com
 */
public class TestAdapter extends RecyclerView.Adapter<BaseViewHolder<String>>{

    private Context mContext;
    private List<String> mDataList;

    public TestAdapter(Context context, List<String> dataList) {
        this.mContext = context;
        this.mDataList = dataList;
    }

    @Override
    public BaseViewHolder<String> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview,parent,false);
        return new TextViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder<String> holder, int position) {
        holder.bindView(mDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    class TextViewHolder extends BaseViewHolder<String>{

        TextView text;

        public TextViewHolder(View itemView) {
            super(itemView);

            text=itemView.findViewById(R.id.tv_friends);
        }

        @Override
        public void bindView(String data) {
            text.setText(data);
        }
    }
}
