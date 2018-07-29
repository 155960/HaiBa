package com.zengqiang.future.filter;

import com.zengqiang.future.common.Notice;
import com.zengqiang.future.common.RequestQueue;
import com.zengqiang.future.pojo.Post;
import com.zengqiang.future.util.RedisCacheUtil;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 修改删除等操作时缓存操作
 */
public class PostCacheUpdateIntercepter implements MethodInterceptor{

 //   public static

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object argument=invocation.getArguments()[0];
        int id;
        try{
            id=Integer.parseInt(argument.toString());
        }catch (Exception e){
            e.printStackTrace();
            id=((Post)argument).getId();
        }
        String key=id+"";
        RequestQueue queue=RequestQueue.getInstance();
        int position=id%RequestQueue.QUEUES;
        Notice notice=new Notice();
        queue.getQueue(id%RequestQueue.QUEUES).offer(notice);
        queue.setFlag(key);
        //不是第一个更新请求
        if(queue.getQueue(position).size()>1){
            //阻塞，等待被执行
            notice.process();
        }
        RedisCacheUtil.removeCacheObject(key);
        Object o=invocation.proceed();
        queue.clearFlag(key);
        queue.getQueue(id%RequestQueue.QUEUES).take();
        //唤醒下一个
        queue.revive(id%RequestQueue.QUEUES);
       /* System.out.println("更新*******");
        Object o=invocation.proceed();
        System.out.println("更新方法结束");*/
        return o;
    }
}
