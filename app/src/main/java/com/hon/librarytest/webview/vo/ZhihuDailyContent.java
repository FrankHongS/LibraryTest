package com.hon.librarytest.webview.vo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Frank on 2018/5/1.
 * E-mail:frank_hon@foxmail.com
 */

public class ZhihuDailyContent {
    @SerializedName("body")
    private String body;
    @SerializedName("image_source")
    private String imageSource;
    @SerializedName("title")
    private String title;
    @SerializedName("image")
    private String image;
    @SerializedName("share_url")
    private String shareUrl;
    @SerializedName("js")
    private List<String> js;
    @SerializedName("ga_prefix")
    private String gaPrefix;
    @SerializedName("images")
    private List<String> images;
    @SerializedName("type")
    private int type;
    @SerializedName("id")
    private int id;
    @SerializedName("css")
    private List<String> css;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public List<String> getJs() {
        return js;
    }

    public void setJs(List<String> js) {
        this.js = js;
    }

    public String getGaPrefix() {
        return gaPrefix;
    }

    public void setGaPrefix(String gaPrefix) {
        this.gaPrefix = gaPrefix;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getCss() {
        return css;
    }

    public void setCss(List<String> css) {
        this.css = css;
    }
}

