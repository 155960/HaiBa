package com.zengqiang.future.filter;


import com.zengqiang.future.common.Notice;
import com.zengqiang.future.common.RequestQueue;
import com.zengqiang.future.util.RedisCacheUtil;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 *
 */
public class PostCacheQueryIntercepter implements MethodInterceptor{

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object[] arguments=invocation.getArguments();//参数,第一个参数为ID
        String key=arguments[0].toString();
        int id=Integer.parseInt(key);
        RequestQueue queue=RequestQueue.getInstance();
        boolean flag=queue.getFlag(key);
        int position=id%RequestQueue.QUEUES;
        Notice notice=new Notice();
        Object o;
        //前面有一个更新操作，等待其完成
        if(flag){
            queue.clearFlag(key);//后续更新操作不放入队列
            queue.getQueue(position).offer(notice);
            System.out.println("查询入队"+queue.getQueue(position).size());
            notice.process();//阻塞
            queue.getQueue(position).poll();//自身出队
            System.out.println("查询出队"+queue.getQueue(position).size()+1);
            queue.revive(position);//唤醒下一个更新操作

            o=invocation.proceed();
            RedisCacheUtil.setCacheObject(key,o);
            return o;
        }
        //当队列有查询操作时循环等待200ms，不断尝试取缓存
        if(queue.getQueue(position).size()>0){
            long startTime=System.currentTimeMillis();
            long currentTime=startTime;
            //200ms的超时时间
            while (currentTime-startTime<200){
                o=RedisCacheUtil.getCacheObject(key);
                if(o!=null){
                    return o;
                }
            }
            o=invocation.proceed();
        }else{
            if(RedisCacheUtil.hasCache(key)){
                System.out.println("有cache");
                o=RedisCacheUtil.getCacheObject(key);
            }else{
                System.out.println("无cache");
                o=invocation.proceed();
                RedisCacheUtil.setCacheObject(key,o);
            }
        }
        //此时直接读数据库
        return o;
        /*//队列中有更新操作，或者该ID目前并没有加入缓存
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
        }*/
        /*System.out.println("查询*******");
        Object o=invocation.proceed();
        System.out.println("查询方法结束");*/

    }

   /* @Override
    public void afterPropertiesSet() throws Exception {

    }*/
}
