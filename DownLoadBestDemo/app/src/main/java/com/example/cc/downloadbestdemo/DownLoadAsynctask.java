package com.example.cc.downloadbestdemo;

import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @创建者 :           chaochen
 * @创建时间 :         2017/10/16 21:50
 * @描述 :           通过asyncTask来下载并且同传入的downloadListener把下载状态回调出去
 */

//parm1 ：传入给doingroud； parm2：progress进度；parm3:返回的结果
public class DownLoadAsynctask extends AsyncTask<String, Integer, Integer> {
    private static final int     TYPE_FAILED  = 0;//下载失败
    private static final int     TYPE_SUCCESS = 1;// 下载成功
    private static final int     TYPE_CANCEL  = 2;// 下载取消
    private static final int     TYPE_PAUSE   = 3;// 下载暂停
    private              boolean isCancel     = false;
    private              boolean isPause      = false;
    private final DownloadFileListener downLoadListener;
    private       int                  mLastProgress;

    //构造函数传入listener便于回调状态
    public DownLoadAsynctask(DownloadFileListener listener) {
        this.downLoadListener = listener;
    }

    @Override
    protected Integer doInBackground(String... strings) {
        File mFile = null;
        InputStream inputStream = null;
        RandomAccessFile randomAccessFile = null;
        try {
            //记录已下载的文件长度
            long downLoadLength = 0;
            //赋值下载的地址url
            String downLoadUrl = strings[0];
            //下载的文件名
            String fileName = downLoadUrl.substring(downLoadUrl.lastIndexOf("/"));
            //下载存放的路径
            String downLoadPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
            mFile = new File(downLoadPath + fileName);
            if (mFile.exists()) {
                //如果文件存在,则记录已下载的文件长度
                downLoadLength = mFile.length();
            }
            //计算文件本身的总大小
            long contentLength = getContentLength(downLoadUrl);
            if (contentLength == 0) {//如果文件的总长度为0则认为文件有问题，返回下载失败
                return TYPE_FAILED;
            } else if (contentLength == downLoadLength) {//如果文件长度等于已经下载文件长度则下载成功
                return TYPE_SUCCESS;
            }
            //如果以上都不满足则开始下载
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .addHeader("RANGE", "bytes=" + downLoadLength + "-")//告诉服务器跳过已下载的字段开始下载
                    .url(downLoadUrl)
                    .build();
            Response response = okHttpClient.newCall(request).execute();
            if (response != null) {
                inputStream = response.body().byteStream();
                randomAccessFile = new RandomAccessFile(mFile, "rw");
                //跳过已经下载的字节
                randomAccessFile.seek(downLoadLength);
                byte[] b = new byte[1024];
                int total = 0;
                int len;
                while ((len = inputStream.read(b)) != -1) {
                    //如果取消
                    if (isCancel) {
                        return TYPE_CANCEL;
                    } else if (isPause) {//如果暂停
                        return TYPE_PAUSE;
                    } else {//正常写入，每次读取1024字节写入
                        total += len;//记录下载的总长度
                        randomAccessFile.write(b, 0, len);//每次写len长度的字节
                        //下载过程中计算下载进度
                        int progress = (int) ((total + downLoadLength) * 100 / contentLength);
                        //把下载进度展示出去
                        publishProgress(progress);
                    }

                }
                response.body().close();
                return TYPE_SUCCESS;
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (randomAccessFile != null) {
                    randomAccessFile.close();

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (randomAccessFile != null) {
                try {
                    randomAccessFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //如果取消下载且文件不为null，则删除文件
            if (isCancel && mFile != null) {
                mFile.delete();
            }
        }

        return TYPE_FAILED;
    }

    private long getContentLength(String downLoadUrl) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(downLoadUrl)
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response != null && response.isSuccessful()) {
                long l = response.body().contentLength();
                response.body().close();
                return l;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //处理doingroud里面传出了的进度
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        int currentProgress = values[0];
        if (currentProgress > mLastProgress) {
            //如果进度有增长，则把进度传递出去
            downLoadListener.downLoadProgress(currentProgress);
            //记录最新的进度值
            mLastProgress = currentProgress;
        }

    }

    //处理最终返回的结果，也就是下载的状态值type

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        switch (integer) {
            case TYPE_FAILED://下载失败
                downLoadListener.downLoadFailed();
                break;
            case TYPE_SUCCESS://下载成功
                downLoadListener.downLoadSuccess();
                break;
            case TYPE_CANCEL://下载取消
                downLoadListener.downLoadCancel();
                break;
            case TYPE_PAUSE://下载暂停
                downLoadListener.downLoadPause();
                break;
            default:
                break;
        }
    }

    //暂停任务
    public void pauseDownLoad(){
        isPause=true;
    }
    //取消任务
    public void cancelDownLoad(){
        isCancel=true;
    }
}
