package com.example.cc.downloadbestdemo;

/**
 * @创建者 :           chaochen
 * @创建时间 :         2017/10/16 21:46
 * @描述 :           下载过程中状态
 */


public interface DownloadFileListener {
    void downLoadSuccess();//下载成功
    void downLoadFailed();//下载失败
    void downLoadPause();//下载暂停
    void downLoadCancel();//下载取消
    void downLoadProgress(int progress);//下载进度
}
