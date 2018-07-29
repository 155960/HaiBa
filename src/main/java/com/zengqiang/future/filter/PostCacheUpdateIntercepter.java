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
public class PostCacheCreateIntercepter implements MethodInterceptor{

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
        //队列中有更新操作或者缓存存在
        boolean flag=queue.getFlag(key);
        Notice notice=new Notice();
        if(flag||RedisCacheUtil.getCacheObject(key)!=null){
            queue.getQueue(id%RequestQueue.QUEUES).offer(notice);
            queue.setFlag(key);
            //如果当前队列中有相同id任务，等待，超时500ms后自动执行
            if(flag){
                notice.process();
            }
            RedisCacheUtil.removeCacheObject(key);
            Object o=invocation.proceed();
            queue.clearFlag(key);
            queue.getQueue(id%RequestQueue.QUEUES).take();
            queue.revive(id%RequestQueue.QUEUES);
            return o;
        }else{
            return invocation.proceed();
        }
    }
}
