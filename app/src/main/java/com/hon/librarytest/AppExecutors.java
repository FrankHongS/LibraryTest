package com.hon.librarytest;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Frank_Hon on 9/21/2018.
 * E-mail: v-shhong@microsoft.com
 */
public class AppExecutors {

    private static volatile AppExecutors sInstance;

    private final Executor diskExecutors;

    private final Executor ioExecutors;

    private AppExecutors(){
        this.diskExecutors=Executors.newSingleThreadExecutor();
        this.ioExecutors=Executors.newFixedThreadPool(3);
    }

    public static AppExecutors getInstance(){
        if(sInstance==null){
            synchronized (AppExecutors.class){
                if(sInstance==null){
                    sInstance=new AppExecutors();
                }
            }
        }

        return sInstance;
    }

    public Executor getDiskExecutors() {
        return diskExecutors;
    }

    public Executor getIoExecutors() {
        return ioExecutors;
    }
}
