package com.hon.librarytest.diffutil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.util.ListUpdateCallback;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hon.librarytest.R;

import java.util.List;

/**
 * Created by Frank on 2018/9/9.
 * E-mail:frank_hon@foxmail.com
 */

public class DiffAdapter extends RecyclerView.Adapter<DiffAdapter.SimpleViewHolder>{

    private static final String TAG=DiffAdapter.class.getSimpleName();

    private Context mContext;
    private List<String> mDataList;

    // each time data is set, we update this variable so that if DiffUtil calculation returns
    // after repetitive updates, we can ignore the old calculation
    private int mDataVersion=0;

    public DiffAdapter(@NonNull Context context, @NonNull List<String> dataList) {
        this.mContext = context;
        this.mDataList = dataList;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SimpleViewHolder
                (LayoutInflater.from(mContext).inflate(R.layout.diff_item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, int position) {
        holder.diffText.setText(mDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @SuppressLint("StaticFieldLeak")
    @MainThread
    public void replace(List<String> update){
        mDataVersion++;

        if(mDataList.isEmpty()){
            if(update==null||update.isEmpty()){
                return;
            }

            mDataList=update;
            notifyDataSetChanged();
        }else if(update==null || update.isEmpty()){
            int oldSize=mDataList.size();
            mDataList=null;
            notifyItemRangeRemoved(0,oldSize);
        }else{
            final int startDataVersion=mDataVersion;
            final List<String> oldItems=mDataList;

            new AsyncTask<Void,Void,DiffUtil.DiffResult>(){
                @Override
                protected DiffUtil.DiffResult doInBackground(Void... voids) {
                    return DiffUtil.calculateDiff(new DiffUtil.Callback() {
                        @Override
                        public int getOldListSize() {
                            return oldItems.size();
                        }

                        @Override
                        public int getNewListSize() {
                            return update.size();
                        }

                        @Override
                        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                            return oldItems.get(oldItemPosition).equals(update.get(newItemPosition));
                        }

                        @Override
                        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                            return oldItems.get(oldItemPosition).equals(update.get(newItemPosition));
                        }
                    });
                }

                @Override
                protected void onPostExecute(DiffUtil.DiffResult diffResult) {
                    if(mDataVersion!=startDataVersion){
                        return;
                    }

                    mDataList=update;

                    diffResult.dispatchUpdatesTo(new ListUpdateCallback() {
                        @Override
                        public void onInserted(int position, int count) {
                            Log.d(TAG, "onInserted: "+position+" "+count);
                            notifyItemRangeInserted(position, count);
                        }

                        @Override
                        public void onRemoved(int position, int count) {
                            Log.d(TAG, "onRemoved: "+position+" "+count);
                            notifyItemRangeRemoved(position, count);
                        }

                        @Override
                        public void onMoved(int fromPosition, int toPosition) {
                            Log.d(TAG, "onMoved: "+fromPosition+" "+toPosition);
                            notifyItemMoved(fromPosition, toPosition);
                        }

                        @Override
                        public void onChanged(int position, int count, Object payload) {
                            Log.d(TAG, "onChanged: ");
                            notifyItemRangeChanged(position, count, payload);
                        }
                    });
                }
            }.execute();
        }
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder{

        TextView diffText;

        public SimpleViewHolder(View itemView) {
            super(itemView);

            diffText=itemView.findViewById(R.id.tv_diff);
        }
    }
}
