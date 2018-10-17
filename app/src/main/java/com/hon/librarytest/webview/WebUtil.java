package com.hon.librarytest.webview;

import android.util.Log;
import android.widget.Toast;

import com.hon.librarytest.LibraryTest;
import com.hon.librarytest.webview.vo.Profile;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static String filterBody(String body, Profile profile){
        String profileStr="";
        String result=body.replaceAll("\r|\n|\t","");
        Pattern pattern=Pattern.compile("<div class=\"meta\".*avatar.*</span></div>");
        Matcher matcher=pattern.matcher(result);
        while (matcher.find()){
            profileStr=matcher.group();
        }
        result=matcher.replaceAll("");
        Log.e("hon", "result: "+result);

        Log.e("hon", "profileStr: "+profileStr);
        String avatar=matchString(profileStr, "(src=\")(.*)(\"><span)", 2);

        String author=matchString(profileStr, "(<span class=\"author\">)(.*)(</span><span)", 2);

        String bio=matchString(profileStr, "(<span class=\"bio\">)(.*)(</span>)", 2);

        if(profile!=null){
            profile.setAvatar(avatar);
            profile.setAuthor(author);
            profile.setBio(bio);
        }

        return result;
    }

    private static String matchString(String target, String regex,int groupIndex){
        String result="";
        Pattern p=Pattern.compile(regex);
        Matcher m=p.matcher(target);
        while(m.find()){
            result=m.group(groupIndex);
        }

        return result;
    }
}
