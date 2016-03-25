package com.example.myvideomode;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import download.DownLoadService;
import download.FileInfo;
import download.IThreadDAO;
import download.ThreadDAOImpl;
import download.ThreadInfo;

/**
 * Created by Administrator on 2016/3/24.
 */

public class FragmentCourseList_Adapter extends BaseAdapter {
    ViewHolder viewHolder;
    Context context;
    String[] mVideoViewUrl;
    String[] mVideoViewTitle;
    String[] mVideoViewHead;
    FileInfo[] fileInfos;

    int finished ;

    public FragmentCourseList_Adapter(Context context, String[] mVideoViewUrl,
                                      String[] mVideoViewTitle, String[] mVideoViewHead, FileInfo[] fileInfos) {
        this.mVideoViewUrl = mVideoViewUrl;
        this.mVideoViewTitle = mVideoViewTitle;
        this.mVideoViewHead = mVideoViewHead;
        this.fileInfos = fileInfos;
        this.context = context;


    }


//    @Override
//    public int getCount() {
//        return fileInfos.length;
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return fileInfos[position];
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(final int position, View convertView, ViewGroup parent) {
//        final FileInfo fileInfo = fileInfos[position];
//        ThreadInfo pp = mDB.getThreads(mVideoViewUrl[position]).get(0);
//        finished = (int) (pp.getFinished()*100/pp.getEnd());
//        if (convertView == null) {
//            viewHolder = new ViewHolder();
//            convertView = LayoutInflater.from(context).inflate(R.layout.course_detail_list_item, null);
//            viewHolder.textView_courseHead = (TextView) convertView.findViewById(R.id.course_head);
//            viewHolder.textview_CourseTitle = (TextView) convertView.findViewById(R.id.course_title);
//            viewHolder.textview_downLoand = (TextView) convertView.findViewById(R.id.download_text);
//            viewHolder.imageView_downLoand = (ImageView) convertView.findViewById(R.id.download_btn);
//            viewHolder.imageView_CourseLock = (ImageView) convertView.findViewById(R.id.courseLock);
//            convertView.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
//        viewHolder.textView_courseHead.setText(mVideoViewHead[position]);
//        viewHolder.textview_CourseTitle.setText(mVideoViewTitle[position]);
//
//        viewHolder.imageView_downLoand.setOnClickListener(new View.OnClickListener() {
//
//
//            @Override
//            public void onClick(View v) {
//                if (isExists(mVideoViewTitle[position], position)) {
//                    //删除文件
//                    File file = new File(DownLoadService.downloadPath + "/" + mVideoViewTitle[position]);
//                    file.delete();
//                    //删除线程信息
//                    System.out.println(mDB.getThreads(mVideoViewUrl[position]).toString());
//                    mDB.deleteThread(mVideoViewUrl[position], position);
//                    ImageView tem_iv = (ImageView) v;
//                    tem_iv.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_download));
//                    System.out.println(mVideoViewUrl[position] + "=========================" + position);
//                } else {
//                    String start = "ACTION_START";
//                    IStartService mStartService = BaseVideoPlayer.listeners.getListener();
//                    if (mStartService != null) {
//                        mStartService.startService(fileInfo, start);
//                        v.setVisibility(View.GONE);
//                    }
//
////                            notifyDataSetChanged();
//                }
//
//            }
//        });
//        if (View.GONE == viewHolder.imageView_downLoand.getVisibility()) {
//            viewHolder.textview_downLoand.setVisibility(View.VISIBLE);
//            viewHolder.textview_downLoand.setText(fileInfos[position].getFinished() + "%");
//        }
//        if(new File(DownLoadService.downloadPath + "/" + mVideoViewTitle[position]).exists()){
//            if(isExists(mVideoViewTitle[position],position)){
//
//            }else {
//                viewHolder.imageView_downLoand.setVisibility(View.GONE);
//
//                viewHolder.textview_downLoand.setVisibility(View.VISIBLE);
//                viewHolder.textview_downLoand.setText(finished+"%");
//
//            }
//        }
//        viewHolder.textview_downLoand.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if(isStart){
//                FileInfo fileInfo = fileInfos[position];
//                String start = "ACTION_STOP";
//                IStartService startService = BaseVideoPlayer.listeners.getListener();
//                if (startService != null) {
//                    startService.startService(fileInfo, start);
//                }
//                    isStart = false;
//            }else{
//                    FileInfo fileInfo = fileInfos[position];
//                    String start = "ACTION_START";
//                    IStartService startService = BaseVideoPlayer.listeners.getListener();
//                    if (startService != null) {
//                        startService.startService(fileInfo, start);
//                    }
//                    isStart = true;
//                }
//            }
//        });
//        if (isExists((mVideoViewTitle[position]), position)) {
//            System.out.println(position + "下载了的文件ID");
//            viewHolder.textview_downLoand.setVisibility(View.GONE);
//            viewHolder.imageView_downLoand.setVisibility(View.VISIBLE);
//            viewHolder.imageView_downLoand.setImageDrawable(context.getResources().getDrawable(R.drawable.delete));
//        };
//        return convertView;
//
//    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final FileInfo fileInfo = fileInfos[position];
        finished = -1;


        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.course_detail_list_item, null);
            viewHolder.textView_courseHead = (TextView) convertView.findViewById(R.id.course_head);
            viewHolder.textview_CourseTitle = (TextView) convertView.findViewById(R.id.course_title);
            viewHolder.textview_downLoand = (TextView) convertView.findViewById(R.id.download_text);
            viewHolder.imageView_downLoand = (ImageView) convertView.findViewById(R.id.download_btn);
            viewHolder.imageView_CourseLock = (ImageView) convertView.findViewById(R.id.courseLock);
            viewHolder.textview_downLoand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (DownLoadService.downLoadStatue.get(fileInfo.getId()).equals(DownLoadService.DOWNLOADCACHE)) {
                        FileInfo fileInfo = fileInfos[position];
                        String start = "ACTION_STOP";
                        IStartService startService = BaseVideoPlayer.listeners.getListener();
                        if (startService != null) {
                            startService.startService(fileInfo, start);
                        }
                    }
                    if (DownLoadService.downLoadStatue.get(fileInfo.getId()).equals(DownLoadService.DOWNLOADPAUSE)) {
                        FileInfo fileInfo = fileInfos[position];
                        String start = "ACTION_START";
                        IStartService startService = BaseVideoPlayer.listeners.getListener();
                        if (startService != null) {
                            startService.startService(fileInfo, start);
                        }
                    }
                }
            });
            viewHolder.imageView_downLoand.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (isExists(mVideoViewTitle[position], position)) {
                        //删除文件
                        File file = new File(DownLoadService.downloadPath + "/" + mVideoViewTitle[position]);
                        file.delete();
                        //删除线程信息
                        Fragment_CourseList.mDB.deleteThread(mVideoViewUrl[position], position);
                        ImageView tem_iv = (ImageView) v;
                        tem_iv.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_download));
                        System.out.println(mVideoViewUrl[position] + "删除的url和id==================" + position);
                    } else {
                        String start = "ACTION_START";
                        IStartService mStartService = BaseVideoPlayer.listeners.getListener();
                        if (mStartService != null) {
                            mStartService.startService(fileInfo, start);
                            v.setVisibility(View.GONE);
                        }

//                            notifyDataSetChanged();
                    }

                }
            });
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView_courseHead.setText(mVideoViewHead[position]);
        viewHolder.textview_CourseTitle.setText(mVideoViewTitle[position]);


        /**
         * 第一次加载相关
         */
        if(Fragment_CourseList.isFirstLoad){
            List<ThreadInfo> localThreadInfos = null;
            try {
                localThreadInfos = Fragment_CourseList.mDB.getThreads(mVideoViewUrl[position]);
            }catch (Exception e){
                e.printStackTrace();
            }
            /**
             * 初始化下载信息 默认是start状态
             */
            DownLoadService.downLoadStatue.put(fileInfo.getId(), DownLoadService.DOWNLOADPAUSE);
            System.out.println("localThreadInfos" + localThreadInfos.size());

            if (localThreadInfos.size()>0) {
                ThreadInfo pp = localThreadInfos.get(0);

                finished = (int) (pp.getFinished() * 100 / pp.getEnd());
                if (finished>0&&finished<100) {
                    viewHolder.imageView_downLoand.setVisibility(View.GONE);
                    viewHolder.textview_downLoand.setVisibility(View.VISIBLE);
                    viewHolder.textview_downLoand.setText(finished + "%");
                    DownLoadService.downLoadStatue.put(pp.getId(), DownLoadService.DOWNLOADPAUSE);
                }
            }
        }else
        {

            if (DownLoadService.downLoadStatue.get(fileInfo.getId()).equals(DownLoadService.DOWNLOADCACHE)) {
                viewHolder.imageView_downLoand.setVisibility(View.GONE);
                viewHolder.textview_downLoand.setVisibility(View.VISIBLE);
                viewHolder.textview_downLoand.setText(fileInfo.getFinished() + "%");
            }
        }
        if (isExists((mVideoViewTitle[position]), position)) {
            System.out.println(position + "下载了的文件ID");
            viewHolder.textview_downLoand.setVisibility(View.GONE);
            viewHolder.imageView_downLoand.setVisibility(View.VISIBLE);
            viewHolder.imageView_downLoand.setImageDrawable(context.getResources().getDrawable(R.drawable.delete));
        }
        ;
        return convertView;

    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mVideoViewUrl.length;
    }

    class ViewHolder {
        TextView textView_courseHead;
        TextView textview_CourseTitle;
        TextView textview_downLoand;
        ImageView imageView_downLoand, imageView_CourseLock;
    }

    /**
     * 判断本地文件是否存在并且是否下载完成
     */
    public boolean isExists(String fileName, int position) {
        try {
            File file = new File(DownLoadService.downloadPath + "/" + fileName);
            if (!file.exists()) {
                return false;
            } else {
                if (Fragment_CourseList.mDB.getThreads(mVideoViewUrl[position]) != null) {
                    ThreadInfo threadInfo = Fragment_CourseList.mDB.getThreads(mVideoViewUrl[position]).get(0);

                    if (threadInfo.getFinished() == threadInfo.getEnd()) {
                        System.out.println(threadInfo.getEnd() + "文件存在" + threadInfo.getFinished());
                        return true;

                    } else {

                        return false;
                    }
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}




