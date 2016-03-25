package download;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/20.
 */
public class ThreadDAOImpl implements IThreadDAO {
    private DBHelper mHelp = null;

    public ThreadDAOImpl(Context context) {

        mHelp = DBHelper.getInstance(context);
    }

    @Override
    public synchronized void insertThread(ThreadInfo threadInfo) {
        SQLiteDatabase db = mHelp.getWritableDatabase();

        db.execSQL(
                "insert into thread_info(thread_id,url,start,end,finished) values (?,?,?,?,?)",
                new Object[]{threadInfo.getId(), threadInfo.getUrl(),threadInfo.getStart(), threadInfo.getEnd(), threadInfo.getFinished()}
        );
        db.close();
    }

    @Override
    public synchronized void deleteThread(String url, int thread_id) {
        SQLiteDatabase db = mHelp.getWritableDatabase();
        db.execSQL("delete from thread_info where url = ? and thread_id = ?",
                new Object[]{url, thread_id});
        db.close();
    }

    @Override
    public synchronized void updataThread(String url,int thread_id,long finished) {
        SQLiteDatabase db = mHelp.getWritableDatabase();
        db.execSQL("update  thread_info set finished = ? where url = ? and thread_id = ?",
                new Object[]{finished,url, thread_id});
        db.close();
    }

    /**
     * 此处为多线程下载作了准备，本应用暂时并未实现
     * 返回的线程集合只有一个数据，相当于只获取了一个线程数据
     * @param url
     * @return
     */

    @Override
    public  List<ThreadInfo> getThreads(String url) {
        SQLiteDatabase db = mHelp.getReadableDatabase();
        List<ThreadInfo> list = new ArrayList<ThreadInfo>();
        Cursor cursor = db.rawQuery("select * from thread_info where url = ? ",
                new String[]{url});
        while (cursor.moveToNext()){
            ThreadInfo threadInfo = new ThreadInfo();
            threadInfo.setId(cursor.getInt(cursor.getColumnIndex("thread_id")));
            threadInfo.setEnd(cursor.getInt(cursor.getColumnIndex("end")));
            threadInfo.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            threadInfo.setStart(cursor.getInt(cursor.getColumnIndex("start")));
            threadInfo.setFinished(cursor.getInt(cursor.getColumnIndex("finished")));
            list.add(threadInfo);
        }
        cursor.close();
        db.close();
        return list;
    }

    @Override
    public boolean isExists(String url) {
        SQLiteDatabase db = mHelp.getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from thread_info where url = ? ",
                new String[]{url});
        boolean exist = cursor.moveToNext();

        cursor.close();
        db.close();
        return exist;

    }
}
