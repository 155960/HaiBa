package com.zengqiang.future.common;

public class Notice {

    private boolean flag=true;

    public void process(){
        while (flag){
           // System.out.println("**"+Thread.currentThread().getName()+"阻塞中");
        }

    }

    public void clearFlag(){
        flag=false;
    }
}
