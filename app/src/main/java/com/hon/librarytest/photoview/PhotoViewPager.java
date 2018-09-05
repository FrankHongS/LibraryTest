package com.hon.librarytest.photoview;

import android.content.Context;
import android.graphics.RectF;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.hon.librarytest.R;
import com.hon.photopreviewlayout.PhotoView;

/**
 * Created by Frank on 2018/5/26.
 * E-mail:frank_hon@foxmail.com
 */

public class PhotoViewPager extends ViewPager{

    private PagerAdapter mPagerAdapter;

    private PhotoView mPhotoView;

    public PhotoViewPager(Context context) {
        this(context,null);
    }

    public PhotoViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPhotoView=getChildAt(getCurrentItem()).findViewById(R.id.pv_image);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mPagerAdapter=getAdapter();
    }

    @Override
    protected void onPageScrolled(int position, float offset, int offsetPixels) {
        super.onPageScrolled(position, offset, offsetPixels);
        mPhotoView=getChildAt(getCurrentItem()).findViewById(R.id.pv_image);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        RectF rect=mPhotoView.getDrawableRectF();
        float width=mPhotoView.getWidth();
        float height=mPhotoView.getHeight();

        switch (ev.getActionMasked()){
            case MotionEvent.ACTION_MOVE:

                if(rect.left<0){}//todo

                break;
            default:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
