package com.hon.librarytest.eventbus;

import android.util.SparseIntArray;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Frank on 2018/4/16.
 * E-mail:frank_hon@foxmail.com
 */

public class TestEvent {
    private String mMsg;
    private Map<Integer,Integer> map=new HashMap<>();

    public String getMsg() {
        return mMsg;
    }

    public void setMsg(String msg) {
        this.mMsg = msg;
    }
}
