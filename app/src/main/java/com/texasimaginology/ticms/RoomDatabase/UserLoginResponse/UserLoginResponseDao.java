package com.texasimaginology.ticms.RoomDatabase.UserLoginResponse;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface UserLoginResponseDao {
    @Insert
    void insertUserDetail(UserLoginResponseEntity mUserLoginResponseEntity);

    @Query("DELETE FROM login_instance")
    void deleteAllLoginInstances();

    @Update
    void updateUerDetail(UserLoginResponseEntity mUserLoginResponseEntity);

    @Query("SELECT * FROM login_instance LIMIT 1 OFFSET 0")
    UserLoginResponseEntity getLoginInstance();



}
