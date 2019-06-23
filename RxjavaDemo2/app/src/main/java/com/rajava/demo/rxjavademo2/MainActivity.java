package com.rajava.demo.rxjavademo2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.rajava.demo.rxjavademo2.annotation.AnnotationTest;
import com.rajava.demo.rxjavademo2.dynamicproxy.CommGuitai;
import com.rajava.demo.rxjavademo2.dynamicproxy.HuanghelouProducter;
import com.rajava.demo.rxjavademo2.dynamicproxy.MaotaiProducter;
import com.rajava.demo.rxjavademo2.dynamicproxy.SellCigerater;
import com.rajava.demo.rxjavademo2.dynamicproxy.SellWine;
import com.rajava.demo.rxjavademo2.staticproxy.RealMovie;
import com.rajava.demo.rxjavademo2.staticproxy.WandaMovieCity;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

@AnnotationTest(id = 1, name = "cc")
public class MainActivity extends AppCompatActivity {
    @AnnotationTest(id = 8, name = "age")
    int age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //创建rxjava
//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
//                Log.d("xxx", "subscribe");
//                e.onNext(1);
//                e.onNext(2);
//                e.onNext(3);
//                e.onComplete();
//            }
//
//        }).subscribe(new Observer<Integer>() {
//            Disposable mDisposable;
//
//            @Override
//            public void onSubscribe(Disposable d) {
//                mDisposable = d;
//                Log.d("xxx", "onSubscribe=" + d.isDisposed());
//            }
//
//            @Override
//            public void onNext(Integer value) {
//                if (value == 2) {
//                    mDisposable.dispose();
//                }
//                Log.d("xxx", "onNext=" + value);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.d("xxx", "onError");
//            }
//
//            @Override
//            public void onComplete() {
//                Log.d("xxx", "onComplete");
//            }
//        });
//        //提取注解属性
//        boolean annotationPresent = getClass().isAnnotationPresent(AnnotationTest.class);
//        if (annotationPresent) {
//            AnnotationTest annotation = getClass().getAnnotation(AnnotationTest.class);
//            if (annotation != null) {
//                int id = annotation.id();
//                String name = annotation.name();
//                Log.d("xxx", "id=" + id + ",,,name=" + name);
//            }
//        }
//
//        try {
//            //反射获取成员变量上的注解
//            Field age = getClass().getDeclaredField("age");
//            if (age != null) {
//                //                age.setAccessible(true);
//                if (age.isAccessible()) {
//                    AnnotationTest annotation = age.getAnnotation(AnnotationTest.class);
//                    if (annotation != null) {
//                        Log.d("xxx", "成员变量注解：id=" + annotation.id() + "name=" + annotation.name());
//                    }
//                }
//
//            }
//            //获取方法上的注解
//            Method method = getClass().getDeclaredMethod("say", int.class, int.class);
//            Log.d("xxx", "method=" + method);
//            if (method != null) {
//                //method.setAccessible(true);
//
//                AnnotationTest annotation = method.getAnnotation(AnnotationTest.class);
//                if (annotation != null) {
//                    Log.d("xxx", "方法注解：id=" + annotation.id() + "name=" + annotation.name());
//                }
//                Object invoke = method.invoke(getClass().newInstance(), new Object[]{100, 2});
//                Log.d("xxx", "result=" + (Integer) invoke);
//            }
//
//
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }

        //静态代理
        RealMovie realMovie = new RealMovie();
        WandaMovieCity wandaMovieCity = new WandaMovieCity(realMovie);
        wandaMovieCity.play();

        //动态代理
        MaotaiProducter maotaiProducter = new MaotaiProducter();
        InvocationHandler commGuitai = new CommGuitai(maotaiProducter);
        SellWine sellWine = (SellWine) Proxy.newProxyInstance(MaotaiProducter.class.getClassLoader(), MaotaiProducter.class.getInterfaces(), commGuitai);
        sellWine.maijiu();

        HuanghelouProducter huanghelouProducter = new HuanghelouProducter();
        InvocationHandler commGuitai1 = new CommGuitai(huanghelouProducter);
        SellCigerater sellCigerater = (SellCigerater) Proxy.newProxyInstance(HuanghelouProducter.class.getClassLoader(), HuanghelouProducter.class.getInterfaces(), commGuitai1);
        sellCigerater.sell();
        Log.d("xxx","sellWine动态代理类名"+sellWine.getClass().getName());
        Log.d("xxx","sellCigerater动态代理类名"+sellCigerater.getClass().getName());
    }

    @AnnotationTest(id = 2, name = "say")
    private int say(int a, int b) {
        return a + b;
    }

}
