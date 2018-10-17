package com.hon.librarytest;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * Created by Frank on 2018/3/25.
 * E-mail:frank_hon@foxmail.com
 */

public class LibraryTest extends Application{

    public static Context sLibraryTest;
    public static String sCacheDir;

    @Override
    public void onCreate() {
        super.onCreate();
        sLibraryTest=getApplicationContext();

        if (getApplicationContext().getExternalCacheDir() != null && ExistSDCard()) {
            sCacheDir = getApplicationContext().getExternalCacheDir().toString();
        } else {
            sCacheDir = getApplicationContext().getCacheDir().toString();
        }

        if (BuildConfig.DEBUG){
            ARouter.openLog();
            ARouter.openDebug();
        }

        ARouter.init(this);
    }

    public static boolean ExistSDCard() {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
