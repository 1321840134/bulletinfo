package bulletinfo.com.bulletinfo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by foxcold on 2018/10/9.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    //数据库名称
    private static final String BASENAME = "bulletinfo";
    //版本号
    private static final int VERSION = 1;

    private Context context;

    public DatabaseHelper(Context context){
        //第三个参数CursorFactory指定在执行查询时获得一个游标实例的工厂类,设置为null,代表使用系统默认的工厂类
        super(context,BASENAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //好友表
        db.execSQL(
                " create table friend ("+
                " id integer primary key autoincrement,"+
                " fid integer,"+
                " time text,"+
                " uid integer,"+
                " user_name text"+
                " )"
        );
        Toast.makeText(context,"Create Success",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //db.execSQL("ALTER TABLE friend ADD time VARCHAR(255)");添加一行
    }
    public boolean deleteDatabase(Context context) {
        return context.deleteDatabase(BASENAME);
    }
}
