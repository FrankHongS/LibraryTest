package com.hon.photopreviewlayout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

/**
 * Created by Frank on 2018/5/27.
 * E-mail:frank_hon@foxmail.com
 */

public class PhotoViewPagerAdapter<E> extends PagerAdapter {

    private Context mContext;
    private ImageData<E> mImageData;


    public PhotoViewPagerAdapter(Context context,@NonNull ImageData<E> imageData) {
        this.mContext=context;
        this.mImageData=imageData;
    }

    @Override
    public int getCount() {
        return mImageData.getData().size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view= LayoutInflater.from(mContext)
                .inflate(R.layout.layout_photo_view,container,false);
        PhotoView photoView=view.findViewById(R.id.pv_image);
        setImageSource(photoView,position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    private void setImageSource(PhotoView photoView, int position){
        if(mImageData.getData()==null)
            return;

        switch (mImageData.getDataType()){
            case ImageData.URL:
                Glide.with(mContext)
                        .load(mImageData.getData().get(position))
                        .placeholder(R.mipmap.placeholder)
                        .dontTransform()
                        .into(photoView);
                break;
            case ImageData.RES_ID:
                photoView.setImageResource((Integer) mImageData.getData().get(position));
                break;
            default:
                break;
        }
    }
}

