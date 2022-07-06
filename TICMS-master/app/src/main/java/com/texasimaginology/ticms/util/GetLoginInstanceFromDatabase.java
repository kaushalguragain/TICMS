package com.texasimaginology.ticms.util;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.texasimaginology.ticms.RoomDatabase.TicmsRoomDatabase;
import com.texasimaginology.ticms.RoomDatabase.UserLoginResponse.UserLoginResponseDao;
import com.texasimaginology.ticms.RoomDatabase.UserLoginResponse.UserLoginResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class GetLoginInstanceFromDatabase {
    private  Context mContext;

    public GetLoginInstanceFromDatabase(Context mContext) {
        this.mContext=mContext;
    }

    public UserLoginResponseEntity getLoginInstance() {
        UserLoginResponseEntity loginResponseEntity = new UserLoginResponseEntity();
        try {
           loginResponseEntity= new LoginInstanceAsync().execute().get();
           Log.d("getLoginInstance", loginResponseEntity.getUserName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return loginResponseEntity;
    }

    private class LoginInstanceAsync extends AsyncTask<Void, Void, UserLoginResponseEntity> {
        TicmsRoomDatabase ticmsRoomDatabase= TicmsRoomDatabase.getDatabaseInstance(mContext);
        UserLoginResponseDao userLoginResponseDao= ticmsRoomDatabase.userLoginResponseDao();

        @Override
        protected UserLoginResponseEntity doInBackground(Void... voids) {
            Log.d("InBackground ", userLoginResponseDao.getLoginInstance().getUserName());
            return userLoginResponseDao.getLoginInstance();
        }
    }

}
