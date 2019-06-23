package com.rajava.demo.rxjavademo2.dynamicproxy;

import android.util.Log;

/**
 * Created by CC on 2019/6/23.
 */

public class MaotaiProducter implements SellWine {
    @Override
    public void maijiu() {
        Log.d("xxx","我是贵州茅台，我生产的茅台酒建议零售价1000元/瓶");
    }
}
