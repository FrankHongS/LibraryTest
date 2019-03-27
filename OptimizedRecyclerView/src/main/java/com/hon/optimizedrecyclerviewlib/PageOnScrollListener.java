package com.hon.optimizedrecyclerviewlib;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

/**
 * Created by Frank_Hon on 3/27/2019.
 * E-mail: v-shhong@microsoft.com
 */
public class PageOnScrollListener extends RecyclerView.OnScrollListener {

    private boolean mIsLoading;

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if(dy>0){
            PageAdapter pageAdapter= (PageAdapter) recyclerView.getAdapter();
            if(!pageAdapter.isLoading()&&isLastItem(recyclerView)){
                PageRecyclerView pageRecyclerView= (PageRecyclerView) recyclerView;
                PageRecyclerView.OnLoadMoreListener onLoadMoreListener=pageRecyclerView.getOnLoadMoreListener();
                if(onLoadMoreListener!=null){
                    onLoadMoreListener.onLoadMore();
                    pageAdapter.showLoading();
                }
            }
        }
    }

    public void test(RecyclerView recyclerView){
        PageAdapter pageAdapter= (PageAdapter) recyclerView.getAdapter();
        if(!pageAdapter.isLoading()&&isLastItem(recyclerView)){
            PageRecyclerView pageRecyclerView= (PageRecyclerView) recyclerView;
            PageRecyclerView.OnLoadMoreListener onLoadMoreListener=pageRecyclerView.getOnLoadMoreListener();
            if(onLoadMoreListener!=null){
                onLoadMoreListener.onLoadMore();
                pageAdapter.showLoading();
            }
        }
    }

    private boolean isLastItem(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int visible = layoutManager.getChildCount();
        int total = layoutManager.getItemCount();
        int lastVisibleItem = getLastVisibleItemPosition(layoutManager);
        return visible > 0 && lastVisibleItem >= total - 1 &&
                total >= visible;
    }

    private int getLastVisibleItemPosition(RecyclerView.LayoutManager layoutManager) {
        int lastVisibleItemPosition;
        if (layoutManager instanceof GridLayoutManager) {
            lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int[] into = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
            ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(into);
            lastVisibleItemPosition = findMax(into);
        } else {
            lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        }
        return lastVisibleItemPosition;
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    public void setIsLoading(boolean isLoading){
        this.mIsLoading=isLoading;
    }
}
