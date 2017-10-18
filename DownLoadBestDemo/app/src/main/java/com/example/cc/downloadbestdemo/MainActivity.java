package com.example.cc.downloadbestdemo;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

//远程下载断点续传
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private DownLoadService.DownLoadBinder mIbinder;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {
        mIntent = new Intent(this, DownLoadService.class);
        startService(mIntent);
        bindService(mIntent,connection,BIND_AUTO_CREATE);
    }

    private void initView() {
        Button btn_start = (Button) findViewById(R.id.btn_start);
        Button btn_pause = (Button) findViewById(R.id.btn_pause);
        Button btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_start.setOnClickListener(this);
        btn_pause.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        //判断内存卡读写权限
        checkSdPermission();
    }

    private void checkSdPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1001);
        }
    }

    ServiceConnection connection =new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
           mIbinder= (  DownLoadService.DownLoadBinder )iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d("xxx","onServiceDisconnected");
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start://开始下载
                mIbinder.startDownLoad("https://raw.githubusercontent.com/guolindev/eclipse/master/eclipse-inst-win64.exe");
                break;
            case R.id.btn_pause://暂停下载
                mIbinder.pauseDownLoad();
                break;
            case R.id.btn_cancel://取消下载
                mIbinder.cancleDownLoad();
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1001:
                if(grantResults.length>0&&grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"拒绝权限将无法使用程序",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        stopService(mIntent);
        unbindService(connection);
    }
}
