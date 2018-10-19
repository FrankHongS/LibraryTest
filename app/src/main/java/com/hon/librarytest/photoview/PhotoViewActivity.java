package com.hon.librarytest.photoview;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListPopupWindow;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.hon.librarytest.AppExecutors;
import com.hon.librarytest.BuildConfig;
import com.hon.librarytest.R;
import com.hon.librarytest.util.Constants;
import com.hon.librarytest.util.FileUtil;
import com.hon.librarytest.util.ToastUtil;
import com.hon.librarytest.util.Util;
import com.hon.photopreviewlayout.ImageData;
import com.hon.photopreviewlayout.PhotoPreviewLayout;
import com.hon.photopreviewlayout.PhotoViewPagerAdapter;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import io.reactivex.schedulers.Schedulers;

/**
 * Created by Frank on 2018/5/22.
 * E-mail:frank_hon@foxmail.com
 */

public class PhotoViewActivity extends AppCompatActivity {

    private PhotoPreviewLayout mPhotoPreviewLayout;

    private ImageData<String> mImageData;

    private PhotoViewPagerAdapter mAdapter;

    private String[] mStrData = {"download"};
    private int[] mImgData = {R.drawable.ic_file_download_black_24dp};

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view_02);
        mPhotoPreviewLayout = findViewById(R.id.ppl_image);

        initData();
        mAdapter = new PhotoViewPagerAdapter(this, mImageData);
        mPhotoPreviewLayout.setViewPagerAdapter(mAdapter);
        mPhotoPreviewLayout.setViewPagerCurrentItem(3);
//        Glide.with(this)
//                .load(Constants.IMAGE_URI)
//                .placeholder(R.mipmap.placeholder)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .into(mPhotoView);
    }

    private void initData() {
//        Integer[] imageArray=new Integer[]{
//                R.mipmap.placeholder
//                R.mipmap.test,
//                R.mipmap.test02,
//                R.mipmap.test03,
//                R.mipmap.test04
//        };

        String[] imageArray = new String[]{
                Constants.IMAGE_URI_01,
                Constants.IMAGE_URI_01,
                Constants.IMAGE_URI_01,
                Constants.IMAGE_URI_02
        };

        List<String> imageData = Arrays.asList(imageArray);

        mImageData = new ImageData<>(ImageData.URL, imageData);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.ib_more:
                showPopupWindow(view);
                break;
            default:
                break;
        }
    }

    //todo
    // difeerence between ListPopupWindow and MenuPopupWindow,
    // check if it's possible to customize

    private void showPopupWindow(View view) {
        ListPopupWindow popupWindow = new ListPopupWindow(this);
        popupWindow.setAnchorView(view);
        popupWindow.setWidth(Util.dip2px(150));
//        popupWindow.setContentWidth();
//        popupWindow.setHorizontalOffset(-view.getWidth()/2);
//        popupWindow.setVerticalOffset(-view.getHeight()/2);
        popupWindow.setModal(true);
        popupWindow.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return mStrData.length;
            }

            @Override
            public Object getItem(int position) {
                return mStrData[position];
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view;
                if (convertView == null) {
                    view = LayoutInflater.from(PhotoViewActivity.this).inflate(R.layout.item_popup, parent, false);
                } else {
                    view = convertView;
                }

                ImageView imageView = view.findViewById(R.id.iv_icon);
                TextView textView = view.findViewById(R.id.tv_text);

                imageView.setImageResource(mImgData[position]);
                textView.setText(mStrData[position]);
                textView.setOnClickListener(
                        v -> {
                            popupWindow.dismiss();
                            downloadImageFromNetwork();
                        }
                );

                return view;
            }
        });
        popupWindow.show();
    }

    private void downloadImageFromNetwork() {
        String imageUrl = mImageData.getData().get(mPhotoPreviewLayout.getCurrentItem());
        Glide.with(this)
                .load(imageUrl)
                .downloadOnly(new SimpleTarget<File>() {
                    @Override
                    public void onResourceReady(File resource, GlideAnimation<? super File> glideAnimation) {
                        saveToPicturesDir(resource);
                    }
                });
    }

    private void saveToPicturesDir(File resource) {
        new RxPermissions(this)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(
                        granted -> {
                            if (granted) {
                                AppExecutors.getInstance().getIoExecutors()
                                        .execute(
                                                () -> {
                                                    FileUtil.savePicturesToGallery(resource);
                                                }
                                        );


                            }
                        }
                ).dispose();
    }
}
