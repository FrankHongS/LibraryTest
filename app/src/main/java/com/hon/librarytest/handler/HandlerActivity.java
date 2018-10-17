package com.hon.librarytest.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Frank on 2018/4/14.
 * E-mail:frank_hon@foxmail.com
 *
 * Handler, Looper, MessageQueue, Message
 *
 * HandlerThread IntentService
 */

public class HandlerActivity extends AppCompatActivity{

    private Handler mHandler=new Handler(Looper.getMainLooper(),new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            return false;
        }
    }){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
