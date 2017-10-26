package com.example.cc.asynctackdemo;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initAsyncTask();
    }

    private void initAsyncTask() {
        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute();

    }
    public class MyAsyncTask extends AsyncTask<Void,Integer,Boolean>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //开启一个下载dialog
            new ProgressDialog(MainActivity.this).show();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            int downloadProgress=0;
            publishProgress(downloadProgress);
            return true;
        }

        //调用publishProgress后ui更新回调会执行
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //更新下载进度
            new ProgressDialog(MainActivity.this).setMessage("下载进度："+values[0]);
        }

        //如果doInBackground方法有返回值，则onPostExecute会执行

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                Toast.makeText(MainActivity.this,"下载成功",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(MainActivity.this,"下载失败",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
