package com.hon.librarytest.webview;

/**
 * Created by Frank on 2018/7/8.
 * E-mail:frank_hon@foxmail.com
 */

public class WebUtil {

    private static final String NATIVE_JS="<script type=\"text/javascript\" " +
            "src=\"file:///android_asset/jquery-3.2.1.js\"></script>\n" +
            "<script type=\"text/javascript\" " +
            "src=\"file:///android_asset/main.js\"></script>";

    private static final String CSS = "<link rel=\"stylesheet\"" +
            " href=\"file:///android_asset/zhihu_daily.css\" type=\"text/css\">\n" +
            "<link rel=\"stylesheet\" " +
            "href=\"file:///android_asset/extra.css\" type=\"text/css\">";

    private static final String DAY_THEME = "<body className=\"\" onload=\"onLoaded()\">";
    private static final String NIGHT_THEME = "<body className=\"\" onload=\"onLoaded()\" class=\"night\">";

    public static String appendToHTML(String body){
        return "<!DOCTYPE html>\n"
                + "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\">\n"
                + "<head>\n"
                + "\t<meta charset=\"utf-8\" />"
                + CSS
                + "\n</head>\n"
                + DAY_THEME
                + body
                + NATIVE_JS
                + "</body></html>";
    }
}
