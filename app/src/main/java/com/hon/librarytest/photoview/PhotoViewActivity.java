package com.hon.librarytest.photoview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hon.librarytest.R;
import com.hon.librarytest.util.Constants;
import com.hon.photopreviewlayout.ImageData;
import com.hon.photopreviewlayout.PhotoPreviewLayout;
import com.hon.photopreviewlayout.PhotoViewPagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Frank on 2018/5/22.
 * E-mail:frank_hon@foxmail.com
 */

public class PhotoViewActivity extends AppCompatActivity{

    private PhotoPreviewLayout mPhotoPreviewLayout;

    private ImageData<String> mImageData;

    private PhotoViewPagerAdapter mAdapter;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view_02);
        mPhotoPreviewLayout=findViewById(R.id.ppl_image);

        initData();
        mAdapter=new PhotoViewPagerAdapter(this,mImageData);
        mPhotoPreviewLayout.setViewPagerAdapter(mAdapter);
        mPhotoPreviewLayout.setViewPagerCurrentItem(3);
//        Glide.with(this)
//                .load(Constants.IMAGE_URI)
//                .placeholder(R.mipmap.placeholder)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .into(mPhotoView);
    }

    private void initData(){
//        Integer[] imageArray=new Integer[]{
//                R.mipmap.placeholder
//                R.mipmap.test,
//                R.mipmap.test02,
//                R.mipmap.test03,
//                R.mipmap.test04
//        };

        String[] imageArray=new String[]{
                Constants.IMAGE_URI_01,
                Constants.IMAGE_URI_01,
                Constants.IMAGE_URI_01,
                Constants.IMAGE_URI_02
        };

        List<String> imageData= Arrays.asList(imageArray);

        mImageData=new ImageData<>(ImageData.URL,imageData);
    }
}
