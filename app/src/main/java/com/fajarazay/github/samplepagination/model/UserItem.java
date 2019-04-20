package com.fajarazay.github.samplepagination.model;

import com.google.gson.annotations.SerializedName;

public class UserItem {

    @SerializedName("login")
    private String login;

    @SerializedName("avatar_url")
    private String avatarUrl;

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return
                "UserItem{" +
                        ",login = '" + login + '\'' +
                        ",avatar_url = '" + avatarUrl + '\'' +
                        "}";
    }
}