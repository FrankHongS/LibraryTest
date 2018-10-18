package com.hon.librarytest.util;

import android.widget.Toast;

import com.hon.librarytest.LibraryTest;

/**
 * Created by Frank_Hon on 10/18/2018.
 * E-mail: v-shhong@microsoft.com
 */
public class ToastUtil {
    private static Toast sToast;

    public static void showToast(String content){
        if(sToast==null){
            sToast=Toast.makeText(LibraryTest.sLibraryTest,content,Toast.LENGTH_SHORT);
        }else{
            sToast.setText(content);
        }

        sToast.show();
    }
}
