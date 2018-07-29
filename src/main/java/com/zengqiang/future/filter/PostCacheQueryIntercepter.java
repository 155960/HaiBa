package com.zengqiang.future.filter;


import com.zengqiang.future.common.Notice;
import com.zengqiang.future.common.RequestQueue;
import com.zengqiang.future.util.RedisCacheUtil;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.InitializingBean;

/**
 * 取缓存没有时存
 */
public class PostCacheIntercepter implements MethodInterceptor{



    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object[] arguments=invocation.getArguments();//参数,第一个参数为ID
        String key=arguments[0].toString();
        int id=Integer.parseInt(key);
        RequestQueue queue=RequestQueue.getInstance();
        Object o= RedisCacheUtil.getCacheObject(key);
        boolean flag=queue.getFlag(key);
        Notice notice=new Notice();
        //队列中有更新操作，或者该ID目前并没有加入缓存
        if(flag||o==null){
            //队列中有更新操作加入队列
            if(flag){
                queue.getQueue(id%RequestQueue.QUEUES).add(notice);
                notice.process();
                queue.getQueue(id%RequestQueue.QUEUES).poll();
            }
            o=RedisCacheUtil.getCacheObject(key);
            if(o!=null){
                return o;
            }
            o=invocation.proceed();
            RedisCacheUtil.setCacheObject(key,o);
            //再一次获取flag，为false清空队列，true则唤醒下一个
            flag=queue.getFlag(key);
            if(flag){
                queue.revive(id%RequestQueue.QUEUES);
            }else{
                queue.reviveAll(id%RequestQueue.QUEUES);
            }
        }
        /*System.out.println("*******");
        Object o=invocation.proceed();
        System.out.println("方法结束");*/
        return o;
    }

   /* @Override
    public void afterPropertiesSet() throws Exception {

    }*/
}
