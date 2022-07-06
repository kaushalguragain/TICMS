package com.texasimaginology.ticms.Logout;

/**
 * Created by deepbhai on 9/16/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LogoutDto {

    @SerializedName("token")
    @Expose
    private String token;

    public LogoutDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}