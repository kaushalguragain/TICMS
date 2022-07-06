package com.texasimaginology.ticms;

import android.util.Log;

/**
 * Created by deepbhai on 2/17/18.
 */

public class CheckError {

    public static String checkNullString(String text){
        if("null".equals(text) || text.equals("")){
            Log.d("ValueOfWard", text);
            return "----";
        }else{
            Log.d("ElseValueOfWard", text);
            return text;
        }
    }
}
