package com.hon.librarytest.util;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.hon.librarytest.LibraryTest;

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

}
