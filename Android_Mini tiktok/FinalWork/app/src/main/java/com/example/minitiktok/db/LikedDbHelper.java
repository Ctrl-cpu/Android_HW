package com.example.minitiktok.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class LikedDbHelper extends SQLiteOpenHelper {
    public LikedDbHelper(@Nullable Context context, @Nullable String name, int version) {
        super(context, name,null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(myContract.SQL_CREATE_LIKE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for(int i = oldVersion;i < newVersion;i++){
            switch (i){
                case 1:
                    db.execSQL(myContract.SQL_ADD_LIKE_COLUMN);
                    break;
            }
        }
    }
}
