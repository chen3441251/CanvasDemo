package com.example.cc.materialdesigndemo.bean;

/**
 * @创建者 :           chaochen
 * @创建时间 :         2017/10/23 20:32
 * @描述 :           ${TODO}
 */


public class Fruit {
    public String name;
    public int resId;


    public Fruit(String name, int resId) {
        this.name = name;
        this.resId = resId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }


}
