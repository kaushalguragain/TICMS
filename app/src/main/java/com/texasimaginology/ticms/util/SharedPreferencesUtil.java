package com.texasimaginology.ticms.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Yubaraj on 12/20/2017.
 */

public class SharedPreferencesUtil {
    private static String userRole;
    private static long customerId;
    private static long loginId;
    private static String token;
    public static Long getUserId(Activity activity){
        SharedPreferences sharedPref = activity.getSharedPreferences("loginDetails", Context.MODE_PRIVATE);
        Long userId= sharedPref.getLong("id", Long.MIN_VALUE);
        userRole = sharedPref.getString("userrole","");
        customerId=sharedPref.getLong("customerId",Long.MIN_VALUE);
        loginId=sharedPref.getLong("loginId",Long.MIN_VALUE);
        token=sharedPref.getString("token","");
        return userId;
    }
    public static String getUserRole() {
        return userRole;
    }

    public static long getLoginId() {
        return loginId;
    }

    public static String getToken() {
        return token;
    }

    public static void setLoginId(long loginId) {
        SharedPreferencesUtil.loginId = loginId;
    }

    public static long getCustomerId() {
        return customerId;
    }

    public static void setCustomerId(long customerId) {
        SharedPreferencesUtil.customerId = customerId;
    }
}
