package download;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 下载任务类
 * Created by Administrator on 2016/3/21.
 */
public class DownTask {
    private Context context;
    private FileInfo fileInfo;
    private IThreadDAO mDao;
    long finished;
    public boolean isPause = false;
    /**
     *创建线程池
     */
    public static ExecutorService es = Executors.newFixedThreadPool(2);
    public DownTask(Context context, FileInfo fileInfo) {
        this.context = context;
        this.fileInfo = fileInfo;
        mDao = new ThreadDAOImpl(context);
    }

    public void DownLoad() {
        List<ThreadInfo> threadInfos = mDao.getThreads(fileInfo.getUrl());
        ThreadInfo threadInfo = null;
        if (threadInfos.size() == 0) {
            threadInfo = new ThreadInfo(fileInfo.getId(), fileInfo.getUrl(), 0, 0, fileInfo.getLength());
            //数据库插入线程信息
            mDao.insertThread(threadInfo);
            Log.i("***************", "插入的线程信息threadInfo" + threadInfo);
        } else {
            threadInfo = threadInfos.get(0);
        }



        DownloadThread a = new DownloadThread(threadInfo);
       DownTask.es.execute(a);

    }

    /**
     * 下载线程
     */
    class DownloadThread extends Thread {
        ThreadInfo threadInfo;

        public DownloadThread(ThreadInfo threadInfo) {

            this.threadInfo = threadInfo;
        }

        @Override
        public void run() {

            HttpURLConnection conn = null;
            RandomAccessFile raf = null;
            InputStream input = null;
            try {
                URL url = new URL(threadInfo.getUrl());
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(3000);
                conn.setRequestMethod("GET");
                //设置下载位置
                long start;
                if(threadInfo.getEnd()>threadInfo.getFinished()){
                    start= threadInfo.getStart() + threadInfo.getFinished();
                }else {
                    System.out.println("下载已经完成");

                    return;
                }

                conn.setRequestProperty("Range", "bytes=" + start +"-"+ threadInfo.getEnd());
                //设置文件写入位置
                File file = new File(DownLoadService.downloadPath, fileInfo.getFileName());
                raf = new RandomAccessFile(file, "rwd");
                raf.seek(start);
                Intent intent = new Intent(DownLoadService.ACTION_UPDATE);
                finished = threadInfo.getFinished();

                //开始下载
                if (conn.getResponseCode() == 206) {

                    //读取文件
                    input = conn.getInputStream();
                    byte[] buffer = new byte[1024 * 4];
                    int length = -1;
                    long time = System.currentTimeMillis();
                    while ((length = input.read(buffer)) != -1) {
                        //写入文件
                        raf.write(buffer, 0, length);
                        //下载进度发送给   activity
                        finished += length;
                        if (System.currentTimeMillis() - time > 1000) {
                            time = System.currentTimeMillis();
                            intent.putExtra("finished", finished * 100 / fileInfo.getLength());
                            Log.i("*******", "下载完成数据==" + finished * 100 / fileInfo.getLength());
                            Log.i("*******", "下载完成数据=="+fileInfo.getLength()+"=="+finished);
                            intent.putExtra("id", fileInfo.getId());
                            //加载下载状态
                            DownLoadService.downLoadStatue.put(threadInfo.getId(), DownLoadService.DOWNLOADCACHE);
                            context.sendBroadcast(intent);
                        }

                        //暂停时保存下载数据
                        if (isPause) {
                            //加载下载状态
                            DownLoadService.downLoadStatue.put(threadInfo.getId(), DownLoadService.DOWNLOADPAUSE);
                            Log.i("*******", "保存下载的数据" + threadInfo + "==" + finished);
                            mDao.updataThread(threadInfo.getUrl(), threadInfo.getId(), finished);
                            DownloadThread.interrupted();
                            return;
                        }
                    }
                    intent.putExtra("finished", finished * 100 / fileInfo.getLength());
                    Log.i("*******", "下载完成数据==" + finished * 100 / fileInfo.getLength());
                    Log.i("*******", "下载完成数据=="+fileInfo.getLength()+"=="+finished);
                    intent.putExtra("id", fileInfo.getId());
                    context.sendBroadcast(intent);

                    //最后完成保存数据
                    mDao.updataThread(threadInfo.getUrl(), threadInfo.getId(), finished);
                    //加载下载状态
                    DownLoadService.downLoadStatue.put(threadInfo.getId(), DownLoadService.DOWNLOADCOMPLETE);
//                    //删除线程信息
//                    mDao.deleteThread(threadInfo.getUrl(), threadInfo.getId());
                }

            } catch (Exception e) {
                //出现异常保存数据
                mDao.updataThread(threadInfo.getUrl(), threadInfo.getId(), finished);
                e.printStackTrace();
            } finally {
                ;
                try {
                    conn.disconnect();
                    raf.close();
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


        }
    }
}
