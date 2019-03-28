package com.hon.optimizedrecyclerviewlib;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hon.optimizedrecyclerviewlib.item.LoadingPageItem;
import com.hon.optimizedrecyclerviewlib.item.PageItem;

import java.util.List;

/**
 * Created by Frank_Hon on 3/27/2019.
 * E-mail: v-shhong@microsoft.com
 */
@SuppressWarnings("all")
public abstract class PageAdapter extends RecyclerView.Adapter<BasePageViewHolder<PageItem>> {

    protected Context mContext;
    protected List<PageItem> mItemList;

    private BasePageViewHolder<PageItem> mLoadingViewHolder;
    private BasePageViewHolder<PageItem> mErrorViewHolder;
    private BasePageViewHolder<PageItem> mBottomViewHolder;

    private boolean mIsLoadingShowing = false;
    private boolean mIsErrorShowing = false;
    private boolean mIsBottomShowing=false;

    public PageAdapter(Context context, List<PageItem> itemList) {
        this.mContext = context;
        this.mItemList = itemList;
    }

    @Override
    public BasePageViewHolder<PageItem> onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case PageItem.NORMAL_ITEM:
                return createNormalViewHolder(parent);
            case PageItem.LOADING_ITEM:
                if (mLoadingViewHolder != null)
                    return mLoadingViewHolder;
                else
                    return new LoadingViewHolder(
                            LayoutInflater.from(mContext)
                                    .inflate(R.layout.default_loading_layout, parent, false)
                    );
            case PageItem.ERROR_ITEM:
                if (mErrorViewHolder != null)
                    return mErrorViewHolder;
                else
                    return new ErrorViewHolder(
                            LayoutInflater.from(mContext)
                                    .inflate(R.layout.default_error_layout, parent, false)
                    );
            case PageItem.BOTTOM_ITEM:
                if (mBottomViewHolder != null)
                    return mBottomViewHolder;
                else
                    return new BottomViewHolder(
                            LayoutInflater.from(mContext)
                                    .inflate(R.layout.default_no_more_layout, parent, false)
                    );
            default:
                break;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(BasePageViewHolder<PageItem> holder, int position) {
        holder.bindView(mItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mItemList.get(position).itemType();
    }

    public void showLoading() {
        if (!mIsLoadingShowing&&!mIsBottomShowing) {
            mItemList.add(new LoadingPageItem());
            notifyItemInserted(mItemList.size() - 1);

            mIsLoadingShowing = true;
        }
    }

    public void hideLoading() {
        if (mIsLoadingShowing) {
            int last = mItemList.size() - 1;
            mItemList.remove(last);
            notifyItemRemoved(last);

            mIsLoadingShowing = false;
        }
    }

    public void showBottom(){
        if(!mIsBottomShowing){

            hideLoading();

            mItemList.add(new BottomPageItem());
            notifyItemInserted(mItemList.size() - 1);

            mIsBottomShowing = true;
        }
    }

    public boolean shouldLoadMore() {
        return !mIsLoadingShowing&&!mIsBottomShowing;
    }

    protected abstract BasePageViewHolder<PageItem> createNormalViewHolder(ViewGroup parent);

    protected void setLoadingViewHolder(BasePageViewHolder<PageItem> viewHolder) {
        this.mLoadingViewHolder = viewHolder;
    }

    protected void setErrorViewHolder(BasePageViewHolder<PageItem> viewHolder) {
        this.mErrorViewHolder = viewHolder;
    }

    protected void setBottomViewHolder(BasePageViewHolder<PageItem> viewHolder) {
        this.mBottomViewHolder = viewHolder;
    }

    static class LoadingViewHolder extends BasePageViewHolder<PageItem> {

        LoadingViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(PageItem item) {

        }
    }

    static class ErrorViewHolder extends BasePageViewHolder<PageItem> {

        ErrorViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(PageItem item) {

        }
    }

    static class BottomViewHolder extends BasePageViewHolder<PageItem> {

        public BottomViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(PageItem item) {

        }
    }
}
