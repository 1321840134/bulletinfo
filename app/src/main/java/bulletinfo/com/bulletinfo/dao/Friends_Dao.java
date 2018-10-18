package bulletinfo.com.bulletinfo.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import bulletinfo.com.bulletinfo.bean.Friends;
import bulletinfo.com.bulletinfo.db.DatabaseHelper;

/**
 * Created by foxcold on 2018/10/9.
 */

public class Friends_Dao {
    private DatabaseHelper helper;
    private SQLiteDatabase db;

    public Friends_Dao(Context context){
        helper = new DatabaseHelper(context);//初始化DatabaseHelper对象
    }

    /**
     * 建立新的好友关系
     * @param friends
     * @return
     */
    public boolean new_friends(Friends friends){
        db = helper.getWritableDatabase();//
        String sql = "INSERT INTO `friend` VALUES("+friends.getFid()+", '"+friends.getTime()+"' ,"+friends.getUid()+", '" +friends.getUser_name()+ "');";
        Cursor cursor = db.rawQuery(sql , null);
        if (cursor.moveToNext()){//如果有返回数据
            cursor.close();
            db.close();
            return  true;
        }
        cursor.close();
        db.close();
        return  true;
    }
    public Friends getFriends(){
        db = helper.getWritableDatabase();
        Friends friends = new Friends();
        Cursor cursor = db.rawQuery("SELECT * FROM friend",null);
        if (cursor.moveToNext()){
            friends.setId(cursor.getInt(cursor.getColumnIndex("id")));
            friends.setFid(cursor.getInt(cursor.getColumnIndex("fid")));
            friends.setTime(cursor.getString(cursor.getColumnIndex("time")));
            friends.setUid(cursor.getInt(cursor.getColumnIndex("uid")));
            friends.setUser_name(cursor.getString(cursor.getColumnIndex("user_name")));
        }
        cursor.close();
        db.close();
        return friends;
    }


}
