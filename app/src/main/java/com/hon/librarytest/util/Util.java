package com.hon.librarytest.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.util.DisplayMetrics;

import com.hon.librarytest.LibraryTest;

import java.io.File;

/**
 * Created by Frank on 2018/3/25.
 * E-mail:frank_hon@foxmail.com
 */

public class Util {

    public static int getAppVersion(){
        try {
            PackageInfo info = LibraryTest.sLibraryTest.getPackageManager().getPackageInfo(LibraryTest.sLibraryTest.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public static long getAvailableSpace(){
        if (LibraryTest.sLibraryTest.getExternalCacheDir() != null && LibraryTest.ExistSDCard()) {
            return getSDCardAvailableSpace();
        } else {
            return getInternalStorageAvailableSpace();
        }
    }

    public static long getAvailableSpace(File path){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.GINGERBREAD){
            return path.getUsableSpace();
        }

        final StatFs statFs=new StatFs(path.getPath());
        return statFs.getBlockSize()*statFs.getAvailableBlocks();
    }

    private static long getInternalStorageAvailableSpace(){
        long ret=0;
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        ret = availableBlocks * blockSize;
        return ret;
    }

    private static long getSDCardAvailableSpace(){
        long ret=0;
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        ret = availableBlocks * blockSize;
        return ret;
    }

    public static int dip2px(float dpValue) {
        final float scale = LibraryTest.sLibraryTest.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(float pxValue) {
        final float scale = LibraryTest.sLibraryTest.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int getScreenHeight(){
        DisplayMetrics dm = LibraryTest.sLibraryTest.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    public static int getScreenWidth(){
        DisplayMetrics dm = LibraryTest.sLibraryTest.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

}
