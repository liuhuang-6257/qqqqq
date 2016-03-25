package download;

import java.io.Serializable;

/**
 * 定义下载文件的信息
 * Created by Administrator on 2016/3/19.
 */
public class FileInfo implements Serializable {
    int id;
    long length;
    long finished;
    String fileName;
    String url;

    @Override
    public String toString() {
        return "FileInfo{" +
                "id=" + id +
                ", length=" + length +
                ", finished=" + finished +
                ", fileName='" + fileName + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public long getFinished() {
        return finished;
    }

    public void setFinished(long finished) {
        this.finished = finished;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public FileInfo(int id, long length, long finished, String fileName, String url) {

        this.id = id;
        this.length = length;
        this.finished = finished;
        this.fileName = fileName;
        this.url = url;
    }

    public FileInfo() {

    }
}
