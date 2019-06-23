package com.rajava.demo.rxjavademo2.staticproxy;

import android.util.Log;

/**
 * Created by CC on 2019/6/23.
 */

public class RealMovie implements Movie {
    @Override
    public void play() {
        Log.d("xxx","我是出片方正在播放电影="+"战斗天使阿丽塔");
    }
}
