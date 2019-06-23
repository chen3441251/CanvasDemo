package com.rajava.demo.rxjavademo2.staticproxy;

import android.util.Log;

/**
 * Created by CC on 2019/6/23.
 */

public class WandaMovieCity implements Movie {
    private final RealMovie realMovie;

    public WandaMovieCity(RealMovie realMovie) {
        this.realMovie=realMovie;
    }

    @Override
    public void play() {
        Log.d("xxx","影片开始我播放10分钟广告");
        realMovie.play();
        Log.d("xxx","影片结束我也播放10分钟广告");
    }
}
