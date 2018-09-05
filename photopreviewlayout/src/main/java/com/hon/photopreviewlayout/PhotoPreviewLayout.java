package com.hon.photopreviewlayout;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Frank on 2018/5/27.
 * E-mail:frank_hon@foxmail.com
 */

public class PhotoPreviewLayout extends RelativeLayout{

    private ViewPager mViewPager;
    private TextView mTextIndicator;

    private int mCount;

    public PhotoPreviewLayout(Context context) {
        this(context,null);
    }

    public PhotoPreviewLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PhotoPreviewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_photo_preview,
                this,true);

        mViewPager=findViewById(R.id.vp_image);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                String indicator=String.valueOf(position+1)+"/"+mCount;
                mTextIndicator.setText(indicator);
            }
        });

        mTextIndicator=findViewById(R.id.tv_indicator);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    public void setViewPagerAdapter(PagerAdapter adapter){
        if (adapter==null)
            throw new RuntimeException("Adapter can not be null in PhotoPreviewLayout!");
        mViewPager.setAdapter(adapter);

        mCount=adapter.getCount();
        if(mCount>0)
            mTextIndicator.setText("1/"+mCount);
    }

    public void setViewPagerCurrentItem(int position){
        mViewPager.setCurrentItem(position);
    }
}
