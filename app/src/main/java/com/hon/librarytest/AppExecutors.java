package com.hon.librarytest;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

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

    private final Executor mainExecutors;

    private AppExecutors(){
        this.diskExecutors=Executors.newSingleThreadExecutor();
        this.ioExecutors=Executors.newFixedThreadPool(3);
        this.mainExecutors=new MainExecutor();
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

    public Executor getMainExecutors() {
        return mainExecutors;
    }

    private static class MainExecutor implements Executor{

        private static final Handler HANDLER=new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            HANDLER.post(command);
        }
    }
}
