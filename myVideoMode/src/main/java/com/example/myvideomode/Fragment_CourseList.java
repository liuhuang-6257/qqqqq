package com.example.myvideomode;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import download.DownLoadService;
import download.FileInfo;
import download.ThreadDAOImpl;
import download.ThreadInfo;
import io.vov.vitamio.utils.Log;

public class Fragment_CourseList extends Fragment implements iVideoCompleteListener, iPlayFirstVideo {
    /**
     * 全局代理
     */
    public static MPSMonitorProxy listeners = new MPSMonitorProxy(iVideoCompleteListener.class, iPlayFirstVideo.class);
    static int CurrentPosion, maxPosion;

    Intent intent;
    BaseAdapter adapter;

    /**
     * 测试类数据
     */
    String[] mVideoViewUrl = new String[]{
            "http://iap-bucket.qiniudn.com/DF7AF72DF41BA26F.mp4",
            "http://iap-bucket.qiniudn.com/B88C77CB69465E24.mp4",
            "http://iap-bucket.qiniudn.com/o_1a3lgtn4t8g81ru319sj1esd168u14.mp4",
            "http://iap-bucket.qiniudn.com/o_1a3lgtccu14q7fokcb1f8k1vbh15.mp4",
            "http://dlqncdn.miaopai.com/stream/MVaux41A4lkuWloBbGUGaQ__.mp4"};

    String[] mVideoViewTitle = new String[]{"第一个视频", "第二个视频", "第三个视频", "第四个视频", "第五个视频",};
    String[] mVideoViewHead = new String[]{"第一章", "第二章", "第三章", "第四章", "第五章",};

    FileInfo[] fileInfos = new FileInfo[]{
            new FileInfo(0, 0, 0, mVideoViewTitle[0], mVideoViewUrl[0]),
            new FileInfo(1, 0, 0, mVideoViewTitle[1], mVideoViewUrl[1]),
            new FileInfo(2, 0, 0, mVideoViewTitle[2], mVideoViewUrl[2]),
            new FileInfo(3, 0, 0, mVideoViewTitle[3], mVideoViewUrl[3]),
            new FileInfo(4, 0, 0, mVideoViewTitle[4], mVideoViewUrl[4])};

    Activity activity;
    public static ThreadDAOImpl mDB;
   public static boolean isFirstLoad =  true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_courselist, null);
        ListView listview_CourseList = (ListView) view.findViewById(R.id.listview_CourseList);
        listeners.addListener(this);
        //初始化数据库接口
        mDB = new ThreadDAOImpl(getActivity());
        //初始化宿主activity
        activity = getActivity();
        //注册广播接受者
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownLoadService.ACTION_UPDATE);
        activity.getApplication().registerReceiver(mReceiver, filter);

        listview_CourseList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /**给activity传递数据*/
                CurrentPosion = position;
                Bundle bundle = new Bundle();
                PutBundleToPlay(position, bundle);
                PlayVideoProxy playVideoProxy = BaseVideoPlayer.listeners.getListener();
                if (playVideoProxy != null) {
                    playVideoProxy.iPlayVideo(bundle);
                }
            }
        });
        class ViewHolder {
            TextView textView_courseHead;
            TextView textview_CourseTitle;
            TextView textview_downLoand;
            ImageView imageView_downLoand, imageView_CourseLock;
        }
//        adapter = new BaseAdapter() {
//
//            ViewHolder viewHolder;
//
//            @Override
//            public View getView(final int position, View convertView, ViewGroup parent) {
//                final FileInfo fileInfo = fileInfos[position];
//
//                if (convertView == null) {
//                    viewHolder = new ViewHolder();
//                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.course_detail_list_item, null);
//                    viewHolder.textView_courseHead = (TextView) convertView.findViewById(R.id.course_head);
//                    viewHolder.textview_CourseTitle = (TextView) convertView.findViewById(R.id.course_title);
//                    viewHolder.textview_downLoand = (TextView) convertView.findViewById(R.id.download_text);
//                    viewHolder.imageView_downLoand = (ImageView) convertView.findViewById(R.id.download_btn);
//                    viewHolder.imageView_CourseLock = (ImageView) convertView.findViewById(R.id.courseLock);
//                    convertView.setTag(viewHolder);
//                } else {
//                    viewHolder = (ViewHolder) convertView.getTag();
//                }
//                viewHolder.textView_courseHead.setText(mVideoViewHead[position]);
//                viewHolder.textview_CourseTitle.setText(mVideoViewTitle[position]);
//
//                viewHolder.imageView_downLoand.setOnClickListener(new OnClickListener() {
//
//                    @SuppressLint("NewApi")
//                    @Override
//                    public void onClick(View v) {
//                        if (isExists(mVideoViewTitle[position], position)) {
//                            //删除文件
//                            File file = new File(DownLoadService.downloadPath + "/" + mVideoViewTitle[position]);
//                            file.delete();
//                            //删除线程信息
//                            System.out.println(mDB.getThreads(mVideoViewUrl[position]).toString());
//                            mDB.deleteThread(mVideoViewUrl[position], position);
//                            ImageView tem_iv = (ImageView) v;
//                            tem_iv.setImageDrawable(getResources().getDrawable(R.drawable.icon_download));
//                            System.out.println(mVideoViewUrl[position] + "=========================" + position);
//                        } else {
//                            String start = "ACTION_START";
//                            IStartService mStartService = BaseVideoPlayer.listeners.getListener();
//                            if (mStartService != null) {
//                                mStartService.startService(fileInfo, start);
//                                v.setVisibility(View.GONE);
//                            }
//
////                            notifyDataSetChanged();
//                        }
//
//                    }
//                });
//                if (View.GONE == viewHolder.imageView_downLoand.getVisibility()) {
//                    viewHolder.textview_downLoand.setVisibility(View.VISIBLE);
//                    viewHolder.textview_downLoand.setText(fileInfos[position].getFinished() + "");
//
//                }
//                viewHolder.textview_downLoand.setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        FileInfo fileInfo = fileInfos[position];
//                        String start = "ACTION_STOP";
//                        IStartService startService = BaseVideoPlayer.listeners.getListener();
//                        if (startService != null) {
//                            startService.startService(fileInfo, start);
//                        }
//                    }
//                });
//                if(isExists((mVideoViewTitle[position]),position)){
//                    System.out.println(position+"下载了的文件ID");
//                    viewHolder.textview_downLoand.setVisibility(View.GONE);
//                    viewHolder.imageView_downLoand.setVisibility(View.VISIBLE);
//                    viewHolder.imageView_downLoand.setImageDrawable(getResources().getDrawable(R.drawable.delete));
//                };
//                return convertView;
//
//            }
//
//            @Override
//            public long getItemId(int position) {
//                // TODO Auto-generated method stub
//                return position;
//            }
//
//            @Override
//            public Object getItem(int position) {
//                // TODO Auto-generated method stub
//                return null;
//            }
//
//            @Override
//            public int getCount() {
//                // TODO Auto-generated method stub
//                return mVideoViewUrl.length;
//            }
//        };
        adapter = new FragmentCourseList_Adapter(getActivity(), mVideoViewUrl, mVideoViewTitle, mVideoViewHead, fileInfos);
        listview_CourseList.setAdapter(adapter);

        return view;
    }

    @Override
    public void VideoCompleteListener() {
        Bundle bundle = new Bundle();
        maxPosion = mVideoViewUrl.length;
        if (CurrentPosion < maxPosion - 1) {
            CurrentPosion = CurrentPosion + 1;

            PutBundleToPlay(CurrentPosion, bundle);
            PlayVideoProxy playVideoProxy = BaseVideoPlayer.listeners.getListener();
            if (playVideoProxy != null) {
                playVideoProxy.iPlayVideo(bundle);
            }
        } else {
            Toast.makeText(getContext(), "您已全部看完了", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void playFirstVideo() {
        Bundle bundle = new Bundle();
        CurrentPosion = 0;
        PutBundleToPlay(CurrentPosion, bundle);
        PlayVideoProxy playVideoProxy = BaseVideoPlayer.listeners.getListener();
        if (playVideoProxy != null) {
            playVideoProxy.iPlayVideo(bundle);
        }
    }

    /**
     *
     */
    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (DownLoadService.ACTION_UPDATE.equals(intent.getAction())) {
                long finished = intent.getLongExtra("finished", 0);
                int id = intent.getIntExtra("id", -1);
                fileInfos[id].setFinished(finished);
                System.out.println("==================" + finished);
                adapter.notifyDataSetChanged();
                isFirstLoad = false;
            }
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        System.out.println("销毁了view界面" + getClass());
        isFirstLoad = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("销毁了"+getClass());
        activity.getApplication().unregisterReceiver(mReceiver);
    }


    private void PutBundleToPlay(int position, Bundle bundle) {

        if (isExists(mVideoViewTitle[position], position)) {
            bundle.putString("url", DownLoadService.downloadPath + "/" + mVideoViewTitle[position]);
        } else {
            bundle.putString("url", mVideoViewUrl[position]);
        }
        bundle.putString("title", mVideoViewTitle[position]);
        System.out.println(DownLoadService.downloadPath + "/" + mVideoViewTitle[position]);
        bundle.putString("head", mVideoViewHead[position]);
        System.out.println(bundle + "********");
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
                if (mDB.getThreads(mVideoViewUrl[position]) != null) {
                    ThreadInfo threadInfo = mDB.getThreads(mVideoViewUrl[position]).get(0);

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
