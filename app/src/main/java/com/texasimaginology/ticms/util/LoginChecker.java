package com.texasimaginology.ticms.util;

import android.content.Context;
import android.content.SharedPreferences;

public class LoginChecker {

    public static Boolean IsLoggedIn(Context mContext) {
        Boolean isLoggedIn=false;
        SharedPreferences sharedPref = mContext.getSharedPreferences("loginDetails", Context.MODE_PRIVATE);
        if (!sharedPref.getString("token", "").equals("")) {
            isLoggedIn=true;
        }else
            isLoggedIn=false;

        return isLoggedIn;
    }
}
