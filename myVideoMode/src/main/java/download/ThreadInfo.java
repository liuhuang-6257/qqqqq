package download;

/**
 * 线程信息
 * Created by Administrator on 2016/3/19.
 */
public class ThreadInfo {

    int id;
    long start;
    long end;
    long finished;
    String url;

    public ThreadInfo() {

    }

    @Override
    public String toString() {
        return "ThreadInfo{" +
                "id=" + id +
                ", start=" + start +
                ", end=" + end +
                ", finished=" + finished +
                ", url='" + url + '\'' +
                '}';
    }

    public ThreadInfo(int id, String url, long start, long finished, long end) {
        this.id = id;
        this.url = url;
        this.start = start;
        this.finished = finished;
        this.end = end;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public long getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
