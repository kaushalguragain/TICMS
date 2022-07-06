package com.texasimaginology.ticms.RoomDatabase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.texasimaginology.ticms.RoomDatabase.UserLoginResponse.UserLoginResponseDao;
import com.texasimaginology.ticms.RoomDatabase.UserLoginResponse.UserLoginResponseEntity;

@Database(entities=UserLoginResponseEntity.class,version = 1)
public abstract class TicmsRoomDatabase extends RoomDatabase {
    private static String TAG= TicmsRoomDatabase.class.getSimpleName();
    public abstract UserLoginResponseDao userLoginResponseDao();
    private static TicmsRoomDatabase ticmsRoomDatabase=null;

    public static TicmsRoomDatabase getDatabaseInstance(Context context){
        Log.d(TAG, "Get database instance called");
        if(ticmsRoomDatabase==null){
            synchronized (TicmsRoomDatabase.class){
                if(ticmsRoomDatabase==null){
                    ticmsRoomDatabase=Room.databaseBuilder(context.getApplicationContext(),
                            TicmsRoomDatabase.class, "ticms_database").build();
                    Log.d(TAG, "Database instance created");
                }
            }
        }
        return ticmsRoomDatabase;
    }



}
