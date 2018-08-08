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
public class PostCacheUpdateIntercepter implements MethodInterceptor {

    public static Object object = new Object();

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object argument = invocation.getArguments()[0];
        int id;
        try {
            id = Integer.parseInt(argument.toString());
        } catch (Exception e) {
            e.printStackTrace();
            id = ((Post) argument).getId();
        }
        String key = id + "";
        RequestQueue queue = RequestQueue.getInstance();
        int position = id % RequestQueue.QUEUES;
        Notice notice = new Notice();
        Object o = null;
        //前面有一个更新操作
        queue.getQueue(id % RequestQueue.QUEUES).offer(notice);
        System.out.println("入队" + queue.getQueue(id % RequestQueue.QUEUES).size());
        if(queue.getFlag(key)){
            o=invocation.proceed();
            queue.getQueue(position).poll();
            queue.clearFlag(key);
            queue.revive(position);
            return o;
        }
        queue.setFlag(key);
        if (queue.getQueue(position).size() > 1) {
            System.out.println("阻塞");
            //阻塞，等待被执行
            notice.process();
            System.out.println("释放");
        }
        RedisCacheUtil.removeCacheObject(key);
        o = invocation.proceed();
        queue.getQueue(id % RequestQueue.QUEUES).poll();
        queue.clearFlag(key);
        System.out.println("出队"+queue.getQueue(position).size()+1);
        //唤醒下一个
        queue.revive(id % RequestQueue.QUEUES);
        return o;
    }
}
