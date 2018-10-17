package com.hon.librarytest.webview.vo;

/**
 * Created by Frank_Hon on 10/17/2018.
 * E-mail: v-shhong@microsoft.com
 */
public class Profile {

    private String avatar;

    private String bio;

    private String author;

    public Profile(){

    }

    public Profile(String avatar, String bio, String author) {
        this.avatar = avatar;
        this.bio = bio;
        this.author = author;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "avatar='" + avatar + '\'' +
                ", bio='" + bio + '\'' +
                ", author='" + author + '\'' +
                '}';
    }


}
