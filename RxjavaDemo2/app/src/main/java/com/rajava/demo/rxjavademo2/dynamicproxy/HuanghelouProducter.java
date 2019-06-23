package com.rajava.demo.rxjavademo2.dynamicproxy;

import android.util.Log;

/**
 * Created by CC on 2019/6/23.
 */

public class HuanghelouProducter implements SellCigerater {
    @Override
    public void sell() {
        Log.d("xxx","黄鹤楼香烟建议零售价17元/包");
    }
}
