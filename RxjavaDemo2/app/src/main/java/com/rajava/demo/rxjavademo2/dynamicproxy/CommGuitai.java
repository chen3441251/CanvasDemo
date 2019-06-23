package com.rajava.demo.rxjavademo2.dynamicproxy;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by CC on 2019/6/23.
 */
//通用柜台什么都可以卖
public class CommGuitai implements InvocationHandler {
    private final Object productType;
    //商品名称
    public CommGuitai(Object productType) {
       this. productType=productType;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log.d("xxx","您购买的商品来自："+getClass().getSimpleName());
        method.invoke(productType,args);
        Log.d("xxx","交易完成");
        return null;
    }
}
