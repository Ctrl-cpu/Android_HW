package com.example.minitiktok.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.minitiktok.home_page.Video;

import java.util.ArrayList;
import java.util.List;

public class LikedUtil {
    public Context mContext;
    private static LikedUtil mLikedUtil;
    private LikedDbHelper mLikedDbHelper;

    private LikedUtil(Context context){
        mLikedDbHelper = new LikedDbHelper(context, "LikeRecord.db",2);
        mContext = context;
    }

    public static LikedUtil getInstance(Context context){
        if(mLikedUtil == null){
            mLikedUtil = new LikedUtil(context);
        } else if((!mLikedUtil.mContext.getClass().equals(context.getClass()))){
            mLikedUtil = new LikedUtil(context);
        }
        return mLikedUtil;
    }

    public boolean isExist(String id){
        SQLiteDatabase db = mLikedDbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + myContract.Record.TABLE_LIKE + " where id = ?", new String[]{id});
        if(cursor.moveToFirst()){
            return true;
        } else{
            return false;
        }
    }

    public void addLike(Video video){
        SQLiteDatabase db = mLikedDbHelper.getWritableDatabase();
        if(!isExist(video.getId())){
            try{
                ContentValues values = new ContentValues();
                values.put(myContract.Record.ID, video.getId());
                values.put(myContract.Record.DATA_TIME, System.currentTimeMillis());
                values.put(myContract.Record.STUDENT_ID, video.getStudentId());
                values.put(myContract.Record.USER_NAME, video.getUserName());
                values.put(myContract.Record.EXTRA_VALUE, video.getExtraValue());
                values.put(myContract.Record.VIDEO_URL, video.getVideoUrl());
                values.put(myContract.Record.IMAGE_URL, video.getImageUrl());
                values.put(myContract.Record.IMAGEW, video.getImageW());
                values.put(myContract.Record.IMAGEH, video.getImageH());
                db.insert(myContract.Record.TABLE_LIKE, null, values);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public List<Video> queryLikeList(){
        SQLiteDatabase db = mLikedDbHelper.getWritableDatabase();
        List<Video> ret = new ArrayList<>();
        Cursor cursor = null;
        try{
            cursor = db.query(myContract.Record.TABLE_LIKE,null,null,null,null,null,myContract.Record.DATA_TIME + " DESC");
            while(cursor.moveToNext()){
                String id = cursor.getString(cursor.getColumnIndex(myContract.Record.ID));
                String student_id = cursor.getString(cursor.getColumnIndex(myContract.Record.STUDENT_ID));
                String user_name = cursor.getString(cursor.getColumnIndex(myContract.Record.USER_NAME));
                String extra_value = cursor.getString(cursor.getColumnIndex(myContract.Record.EXTRA_VALUE));
                String video_url = cursor.getString(cursor.getColumnIndex(myContract.Record.VIDEO_URL));
                String image_url = cursor.getString(cursor.getColumnIndex(myContract.Record.IMAGE_URL));
                int image_w = cursor.getInt(cursor.getColumnIndex(myContract.Record.IMAGEW));
                int image_h = cursor.getInt(cursor.getColumnIndex(myContract.Record.IMAGEH));
                Video video = new Video(id,student_id,user_name,extra_value,video_url,image_url);
                video.setImageH(image_h);
                video.setImageW(image_w);
                ret.add(video);
            }
        } finally {
            if(cursor != null){
                cursor.close();
            }
        }
        return ret;
    }

    public void deleteLike(String id){
        SQLiteDatabase db = mLikedDbHelper.getWritableDatabase();
        if(isExist(id)){
            db.delete(myContract.Record.TABLE_LIKE, "id = " + "'" + id + "'",null);
        }
    }

    public void deleteAllLike(){
        SQLiteDatabase db = mLikedDbHelper.getWritableDatabase();
        db.delete(myContract.Record.TABLE_LIKE,null,null);
    }

}
