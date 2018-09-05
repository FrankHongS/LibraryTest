package com.hon.librarytest.webview;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * Created by Frank on 2018/5/1.
 * E-mail:frank_hon@foxmail.com
 */

public class NativeInterface {

    private Context mContext;

    public NativeInterface(Context context) {
        mContext = context;
    }

    @JavascriptInterface
    public void hello() {
        Toast.makeText(mContext, "hello", Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void hello(String params) {
        Toast.makeText(mContext, params, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void toast(String str) {
        Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public String getAndroidTime() {
        return String.valueOf(System.currentTimeMillis());
    }

    @JavascriptInterface
    public void showImageDetail(String src){
        Toast.makeText(mContext, src, Toast.LENGTH_LONG).show();
    }
}
