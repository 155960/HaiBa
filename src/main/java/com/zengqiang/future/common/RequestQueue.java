package com.zengqiang.future.common;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class RequestQueue {
    public static final int QUEUES=20;

    private ArrayBlockingQueue<Notice>[] threadQueue=new ArrayBlockingQueue[QUEUES];

    private ConcurrentHashMap<String,Boolean> flagMap=new ConcurrentHashMap<>();
    private RequestQueue(){
    }
    private static class Singleton{
        private static RequestQueue queue;
        static {
            queue=new RequestQueue();
        }

        public static RequestQueue getQueue(){
            return queue;
        }
    }

    public static RequestQueue getInstance(){
        return RequestQueue.Singleton.getQueue();
    }

    public synchronized void addQueue(int i){
        if(threadQueue.length<QUEUES){
            threadQueue[i]=new ArrayBlockingQueue<Notice>(10);
        }
    }

    public ArrayBlockingQueue getQueue(int i){
        if(i>=QUEUES){
            throw new IllegalArgumentException("超过范围");
        }
        if(threadQueue[i]==null){
            addQueue(i);
        }
        return threadQueue[i];
    }

    public boolean getFlag(String key){
        Boolean b= flagMap.get(key);
        if(b==null||!b){
            return false;
        }else{
            return true;
        }
    }

    public void setFlag(String key){
        flagMap.put(key,true);
    }

    public void clearFlag(String key){
        flagMap.remove(key);
    }

    //唤醒
    public void revive(int i){
        Notice notice=threadQueue[i].peek();
        if(notice!=null){
            notice.clearFlag();
        }

    }

    //防止出队时有更新操作进入并导致其唤醒
    public synchronized void reviveAll(int i){
        Notice[] notices=(Notice[]) threadQueue[i].toArray();
        for(int j=0;j<notices.length;j++){
            notices[i].clearFlag();
        }
    }
}
