package com.hon.librarytest.imageloader;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.FileDescriptor;

/**
 * Created by Frank on 2018/9/9.
 * E-mail:frank_hon@foxmail.com
 */

public class ImageResizer {
    private static final String TAG=ImageResizer.class.getSimpleName();

    public Bitmap decodeSampledBitmapFromResource(Resources res,
                              int resId,int reqWidth,int reqHeight){
        final BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeResource(res,resId,options);

        options.inSampleSize=calculateInSampleSize(options,reqWidth,reqHeight);

        options.inJustDecodeBounds=false;
        return BitmapFactory.decodeResource(res,resId,options);
    }

    public Bitmap decodeSampledBitmapFromFileDescriptor(FileDescriptor fileDescriptor,
                                                        int reqWidth,int reqHeight){
        final BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeFileDescriptor(fileDescriptor,null,options);

        options.inSampleSize=calculateInSampleSize(options,reqWidth,reqHeight);

        options.inJustDecodeBounds=false;
        return BitmapFactory.decodeFileDescriptor(fileDescriptor,null,options);
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        if(reqWidth==0||reqHeight==0)
            return 1;

        final int height=options.outHeight;
        final int width=options.outWidth;

        int inSampleSize=1;

        if(height>reqHeight||width>reqWidth){
            final int halfHeight=height/2;
            final int halfWidth=width/2;

            while (halfWidth/inSampleSize>=reqWidth&&halfHeight/inSampleSize>=reqHeight){
                inSampleSize*=2;
            }

        }
        Log.d(TAG, "inSampleSize: "+inSampleSize);
        return inSampleSize;
    }
}
