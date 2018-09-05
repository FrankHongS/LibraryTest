package com.hon.librarytest.util;

import com.hon.librarytest.webview.network.NewsService;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Frank on 2018/5/1.
 * E-mail:frank_hon@foxmail.com
 */

public class RetrofitUtil {
    private RetrofitUtil(){}
    private static volatile Retrofit sNewsRetrofit;

    private static Retrofit getNewsRetrofitInstance(){
        if(sNewsRetrofit==null){
            synchronized (RetrofitUtil.class){
                if(sNewsRetrofit==null){
                    sNewsRetrofit=new Retrofit.Builder()
                            .baseUrl(NewsService.NEWS_BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build();
                }
            }
        }

        return sNewsRetrofit;
    }

    public static NewsService createNewsService(){
        return getNewsRetrofitInstance()
                .create(NewsService.class);
    }
}
