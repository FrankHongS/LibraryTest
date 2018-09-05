package com.hon.swipebacklayout;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Scroller;

/**
 * Created by Frank on 2018/4/25.
 * E-mail:frank_hon@foxmail.com
 */

public class SwipeBackLayout extends FrameLayout{

    private Scroller mScroller;

    public SwipeBackLayout(@NonNull Context context) {
        this(context,null);
    }

    public SwipeBackLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SwipeBackLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mScroller=new Scroller(context);
    }

    public void attachToActivity(Activity activity){
        ViewGroup decor= (ViewGroup) activity.getWindow().getDecorView();
        ViewGroup decorChild= (ViewGroup) decor.getChildAt(0);
        decor.removeView(decorChild);
        addView(decorChild);
        decor.addView(this);
    }

    @Override
    public void computeScroll() {
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            postInvalidate();
        }
    }
}
