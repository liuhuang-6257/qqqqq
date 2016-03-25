//package download;
//
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//
//import java.io.File;
//
//public class MainActivity extends AppCompatActivity {
//    TextView textView;
//    Button button1,button2;
//
//    boolean aaaa= true;
//    boolean bbbb= true;
//    boolean cccc= true;
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        textView = (TextView) findViewById(R.id.dowm);
//        button1 = (Button) findViewById(R.id.button);
//        button2 = (Button) findViewById(R.id.button2);
//        /**
//         * 初始化下载服务
//         */
//        intent = new Intent(MainActivity.this, DownLoadService.class);
//
//        String name =new  File(DownLoadService.downloadPath+"testdown1").getName();
//        Log.i("=======","真的有数据啊=="+name);
//        button1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                final FileInfo fileInfo = new FileInfo(1, 100, 50, "testdown1", "http://iap-bucket.qiniudn.com/DF7AF72DF41BA26F.mp4");
//                if(bbbb){
//                    intent.setAction("ACTION_START");
//                    intent.putExtra("fileInfo", fileInfo);
//                    startService(intent);
//                    bbbb=false;
//                }else{
//                    intent.setAction("ACTION_STOP");
//                    intent.putExtra("fileInfo", fileInfo);
//                    startService(intent);
//                    bbbb=true;
//                }
//            }
//        });
//        button2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                final FileInfo fileInfo = new FileInfo(2, 100, 50, "testdown2", "http://iap-bucket.qiniudn.com/B88C77CB69465E24.mp4");
//                if(cccc){
//                    intent.setAction("ACTION_START");
//                    intent.putExtra("fileInfo", fileInfo);
//                    startService(intent);
//                    cccc=false;
//                }else{
//                    intent.setAction("ACTION_STOP");
//                    intent.putExtra("fileInfo", fileInfo);
//                    startService(intent);
//                    cccc=true;
//                }
//            }
//        });
//
//        textView.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                intent = new Intent(MainActivity.this, DownLoadService.class);
//                final FileInfo fileInfo = new FileInfo(0, 100, 50, "testdown", "http://iap-bucket.qiniudn.com/o_1a3lgtn4t8g81ru319sj1esd168u14.mp4");
//                if(aaaa){
//                    intent.setAction("ACTION_START");
//                    intent.putExtra("fileInfo", fileInfo);
//                    startService(intent);
//                    aaaa=false;
//                }else{
//                    intent.setAction("ACTION_STOP");
//                    intent.putExtra("fileInfo", fileInfo);
//                    startService(intent);
//                    aaaa=true;
//                }
//
//            }
//        });;
//        //注册广播接受者
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(DownLoadService.ACTION_UPDATE);
//        registerReceiver(mReceiver,filter);
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        unregisterReceiver(mReceiver);
//        stopService(intent);
//    }
//
//    BroadcastReceiver mReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if(DownLoadService.ACTION_UPDATE.equals(intent.getAction())){
//                long finished = intent.getLongExtra("finished", 0);
//                int id =intent.getIntExtra("id",0);
//                if (id==0){ textView.setText(""+finished);}
//                if (id==1){ button1.setText(""+finished);}
//                if (id==2){ button2.setText(""+finished);}
//            }
//        }
//    };
//}
