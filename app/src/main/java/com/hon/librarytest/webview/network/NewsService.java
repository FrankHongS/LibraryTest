package com.hon.librarytest.webview.network;

import com.hon.librarytest.webview.vo.ZhihuDailyContent;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Frank on 2018/5/1.
 * E-mail:frank_hon@foxmail.com
 */

public interface NewsService {
    String NEWS_BASE_URL="https://news-at.zhihu.com/api/4/news/";

    @GET("{id}")
    Observable<ZhihuDailyContent> getNewsContent(@Path("id") int id);

}
