package download;

import java.util.List;

/**
 * 数据访问接口
 * Created by Administrator on 2016/3/20.
 */
public interface IThreadDAO {
   //插入线程
    public void insertThread(ThreadInfo threadInfo);
    //删除线程
    public  void deleteThread(String url, int thread_id);
    //更新线程
    public void updataThread(String url, int thread_id, long finished);
    //获取线程集合
    public List<ThreadInfo> getThreads(String url);
    //线程信息是否存在
    public boolean isExists(String url);

}
