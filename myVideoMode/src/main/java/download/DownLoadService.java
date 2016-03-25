package download;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Created by Administrator on 2016/3/19.
 */
public class DownLoadService extends Service {
    public static final String ACTION_START = "ACTION_START";
    public static final String ACTION_STOP = "ACTION_STOP";
    public static final String ACTION_UPDATE = "ACTION_UPDATE";
    public static final String DOWNLOADCOMPLETE = "DOWNCOMPLETE";
    public static final String DOWNLOADCACHE = "DOWNLOADCACHE";
    public static final String DOWNLOADSTART = "DOWNLOADSTART";
    public static final String DOWNLOADPAUSE= "DOWNLOADPAUSE";
    public  static Map<Integer,String> downLoadStatue = new HashMap<>();



    public static final String downloadPath =
            Environment.getExternalStorageDirectory().getAbsolutePath() + "/yunDownLoads";
   //下载任务集合
    private Map<Integer,DownTask> mTasksList = new  LinkedHashMap<Integer,DownTask>();

    FileInfo fileInfoDoubleClick = new FileInfo(0,0,0,"firstliuhuang","urlliuhuang");

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.i("***************", "kkkkkkkkkkkkkkkkkkkkkkkkk");
            switch (msg.what) {
                case 0:
                    FileInfo fileInfo = (FileInfo) msg.obj;
                    Log.i("kkkkkkkkk", ((FileInfo) msg.obj).toString());
                    DownTask mTask = new DownTask(DownLoadService.this, fileInfo);
                    if(downLoadStatue.get(fileInfo.getId()).equals(DOWNLOADCACHE)){
                        return;
                    }else{
                        downLoadStatue.put(fileInfo.getId(),DOWNLOADCACHE);
                    mTask.DownLoad();}

                    //把下载任务加到集合中
                    mTasksList.put(fileInfo.getId(),mTask);
                    break;
            }
        }

    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction().equals(ACTION_START)) {

            FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
            Log.i("fileInfostart", fileInfo.toString());
            new initThread(fileInfo).start();
        } else {
            if (intent.getAction().equals(ACTION_STOP)) {
                FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
                Log.i("fileInfostop", fileInfo.toString());
                DownTask mTask = mTasksList.get(fileInfo.getId());
                if(mTask!=null){
                    mTask.isPause = true;
                }
            }
        }


        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }


    class initThread extends Thread {
        FileInfo fileInfo = null;


        public initThread(FileInfo fileInfo) {
            this.fileInfo = fileInfo;
        }

        @Override
        public void run() {
            HttpURLConnection connection = null;
            RandomAccessFile raf = null;
            try {
                //获取网络文件
                URL url = new URL(fileInfo.getUrl());
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(3000);
                connection.setRequestMethod("GET");
                int length = -1;

                if (connection.getResponseCode() == 200){
                    //获取文件长度
                    length = connection.getContentLength();
                }

                if (length < 0) {

                    return;
                }

                //本地创建文件
                File dir = new File(downloadPath);
                if (!dir.exists()) {
                    dir.mkdir();
                }
                File file = new File(dir, fileInfo.getFileName());
                raf = new RandomAccessFile(file, "rwd");
                //设置文件长度
                raf.setLength(length);
                fileInfo.setLength(length);
                handler.obtainMessage(0, fileInfo).sendToTarget();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

                try {
                    connection.disconnect();
                    raf.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }
    }

}