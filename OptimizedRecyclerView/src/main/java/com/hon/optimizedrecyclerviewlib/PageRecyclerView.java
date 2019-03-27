package com.hon.optimizedrecyclerviewlib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by Frank_Hon on 3/27/2019.
 * E-mail: v-shhong@microsoft.com
 */
public class PageRecyclerView extends RecyclerView{

    private OnScrollListener mOnScrollListener;
    private OnLoadMoreListener mOnLoadMoreListener;

    private float y1,y2;
    private ScrollDetector mScrollDetector;
    private static final int Y_THRESHOLD=100;

    public PageRecyclerView(Context context) {
        this(context,null);
    }

    public PageRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PageRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mScrollDetector=new ScrollDetector();

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent e) {

        switch (e.getAction()){
            case MotionEvent.ACTION_DOWN:
                y1=e.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                y2=e.getY();
                if(y2<y1-Y_THRESHOLD){
                    mScrollDetector.handleLoadMore(this);
                }
                break;
            default:
                break;
        }

        return super.onTouchEvent(e);
    }

    public void setCustomOnScrollListener(OnScrollListener onScrollListener) {
        this.mOnScrollListener = onScrollListener;
    }

    public OnLoadMoreListener getOnLoadMoreListener() {
        return mOnLoadMoreListener;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.mOnLoadMoreListener = onLoadMoreListener;
    }

    public interface OnLoadMoreListener{

        void onLoadMore();

    }
}
