package com.example.cc.downloadbestdemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

/**
 * 为了保持app后台持续下载任务，创建一个service
 */
public class DownLoadService extends Service {
    private DownLoadAsynctask mDownLoadAsynctask;
    private String downLoadUrl;
    //创建一个listener对象，主要是把下载过程中的状态都回调回来作为通知显示出来
    DownloadFileListener downloadFileListener = new DownloadFileListener() {

        @Override
        public void downLoadSuccess() {
            //下载成功的回调
            //下载成功把downloadTask置为null
            mDownLoadAsynctask=null;
            //关闭前台服务显示
            stopForeground(true);
            //显示下载成功的通知
            getNotificaationManager().notify(1,getNotification("下载成功",100));
            Toast.makeText(DownLoadService.this,"下载成功",Toast.LENGTH_SHORT).show();

        }

        @Override
        public void downLoadFailed() {
            mDownLoadAsynctask=null;
                // 下载失败将前台通知关闭，并且创建一个新的通知告知用户下载失败
            stopForeground(true);
            getNotificaationManager().notify(1,getNotification("下载失败",-1));
            Toast.makeText(DownLoadService.this,"下载失败",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void downLoadPause() {
            mDownLoadAsynctask=null;
            Toast.makeText(DownLoadService.this,"下载暂停",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void downLoadCancel() {
            mDownLoadAsynctask=null;
            stopForeground(true);
            Toast.makeText(DownLoadService.this,"下载取消",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void downLoadProgress(int progress) {
            //展示下载进度
            getNotificaationManager().notify(1,getNotification("正在下载中...",progress));
        }
    };


    private NotificationManager getNotificaationManager() {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        return manager;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return new DownLoadBinder();
    }

    //创建Binder对象来提供给外部调用,实现服务与活动之间的通信
    public class DownLoadBinder extends Binder {
        //提供下载/暂停/取消功能
        public void startDownLoad(String url) {
            if (mDownLoadAsynctask == null) {
                downLoadUrl = url;//对外部传入的url进行赋值
                mDownLoadAsynctask = new DownLoadAsynctask(downloadFileListener);//创建synctask对象
                //执行异步任务
                mDownLoadAsynctask.execute(downLoadUrl);
                //置为前台服务
                startForeground(1, getNotification("startDownLoading...", 0));
                //弱提示
                Toast.makeText(DownLoadService.this,"开始下载",Toast.LENGTH_SHORT).show();
            }
        }
        //下载暂停
        public void pauseDownLoad(){
            if (mDownLoadAsynctask != null) {
                mDownLoadAsynctask.pauseDownLoad();//把变量置为暂停
            }
        }
        //取消下载
        public void cancleDownLoad(){
            if (mDownLoadAsynctask != null) {
                mDownLoadAsynctask.cancelDownLoad();
            }else {
                //如果没有进行下载的情况下点击了取消下载按钮则把文件删除
                if (downLoadUrl != null) {
                    //根据url取出文件名,从而得到apk保存的路径，从而来删除路径
                    String  fileName=downLoadUrl.substring(downLoadUrl.lastIndexOf("/"));
                    //指定获取的路径
                    String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                    File file = new File(path + fileName);
                    if (file.exists()) {
                        file.delete();
                    }
                    getNotificaationManager().cancel(1);
                    stopForeground(true);
                    Toast.makeText(DownLoadService.this,"取消下载",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private Notification getNotification(String title, int progress) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle(title);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        builder.setContentIntent(pi);
        if (progress > 0) {
            //下载进度大于0才需要显示
            builder.setContentText(progress + "%");
            builder.setProgress(100, progress, false);
        }
       return builder.build();
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        super.unbindService(conn);
        Log.d("xxx","unbindService");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean bindService(Intent service, ServiceConnection conn, int flags) {
        Log.d("xxx","bindService");
        return super.bindService(service, conn, flags);
    }
}
