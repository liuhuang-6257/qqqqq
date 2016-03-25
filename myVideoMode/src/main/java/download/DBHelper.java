package download;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/3/20.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "Download_DB";
    private static final int VERSION = 1;
    static DBHelper mHelper = null;
    private static final String SQL_CREAT = "create table thread_info(" +
            "_id integer primary key autoincrement,thread_id integer,url text," +
            "start long,end long,finished long )";
    private static final String SQL_DROP = "drop table if exists thread_info";

    /**
     * 获取数据库对象
     */
    public static DBHelper getInstance(Context context){
        if(mHelper == null){
        mHelper = new DBHelper(context);
        }
        return mHelper;
    }

    private DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREAT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP);
        db.execSQL(SQL_CREAT);
    }
}
