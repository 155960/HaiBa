package com.zengqiang.future.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class RedisCacheUtil {

    private static RedisTemplate redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate){
        RedisCacheUtil.redisTemplate=redisTemplate;
    }

   public static void removeCacheObject(String key){
       redisTemplate.delete(key);
   }

   public static boolean hasCache(String key){
        return redisTemplate.hasKey(key);
   }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     * @param key    缓存的键值
     * @param value    缓存的值
     * @return        缓存的对象
     */
    public static <T> ValueOperations<String,T> setCacheObject(String key, T value)
    {

        ValueOperations<String,T> operation = redisTemplate.opsForValue();
        operation.set(key,value);
        return operation;
    }

    /**
     * 获得缓存的基本对象。
     * @param key        缓存键值
     * @return            缓存键值对应的数据
     */
    public static <T> T getCacheObject(String key/*,ValueOperations<String,T> operation*/)
    {
        ValueOperations<String,T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * 缓存List数据
     * @param key        缓存的键值
     * @param dataList    待缓存的List数据
     * @return            缓存的对象
     */
    public static <T> ListOperations<String, T> setCacheList(String key, List<T> dataList)
    {
        ListOperations listOperation = redisTemplate.opsForList();
        if(null != dataList)
        {
            int size = dataList.size();
            for(int i = 0; i < size ; i ++)
            {

                listOperation.rightPush(key,dataList.get(i));
            }
        }

        return listOperation;
    }

    /**
     * 获得缓存的list对象
     * @param key    缓存的键值
     * @return        缓存键值对应的数据
     */
    public static <T> List<T> getCacheList(String key)
    {
        List<T> dataList = new ArrayList<T>();
        ListOperations<String,T> listOperation = redisTemplate.opsForList();
        Long size = listOperation.size(key);

        for(int i = 0 ; i < size ; i ++)
        {
            dataList.add((T) listOperation.leftPop(key));
        }

        return dataList;
    }

    /**
     * 缓存Set
     * @param key        缓存键值
     * @param dataSet    缓存的数据
     * @return            缓存数据的对象
     */
    public static <T> BoundSetOperations<String,T> setCacheSet(String key, Set<T> dataSet)
    {
        BoundSetOperations<String,T> setOperation = redisTemplate.boundSetOps(key);
        /*T[] t = (T[]) dataSet.toArray();
             setOperation.add(t);*/


        Iterator<T> it = dataSet.iterator();
        while(it.hasNext())
        {
            setOperation.add(it.next());
        }

        return setOperation;
    }

    /**
     * 获得缓存的set
     * @param key
     * @return
     */
    public static <T> Set<T> getCacheSet(String key/*,BoundSetOperations<String,T> operation*/)
    {
        Set<T> dataSet = new HashSet<T>();
        BoundSetOperations<String,T> operation = redisTemplate.boundSetOps(key);

        Long size = operation.size();
        for(int i = 0 ; i < size ; i++)
        {
            dataSet.add(operation.pop());
        }
        return dataSet;
    }

    /**
     * 缓存Map
     * @param key
     * @param dataMap
     * @return
     */
    public static <T> HashOperations<String,String,T> setCacheMap(String key, Map<String,T> dataMap)
    {

        HashOperations hashOperations = redisTemplate.opsForHash();
        if(null != dataMap)
        {

            for (Map.Entry<String, T> entry : dataMap.entrySet()) {

                /*System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  */
                hashOperations.put(key,entry.getKey(),entry.getValue());
            }

        }

        return hashOperations;
    }

    /**
     * 获得缓存的Map
     * @param key
     * @return
     */
    public static <T> Map<String,T> getCacheMap(String key/*,HashOperations<String,String,T> hashOperation*/)
    {
        Map<String, T> map = redisTemplate.opsForHash().entries(key);
        /*Map<String, T> map = hashOperation.entries(key);*/
        return map;
    }







    /**
     * 缓存Map
     * @param key
     * @param dataMap
     * @return
     */
    public static <T> HashOperations<String,Integer,T> setCacheIntegerMap(String key,Map<Integer,T> dataMap)
    {
        HashOperations hashOperations = redisTemplate.opsForHash();
        if(null != dataMap)
        {

            for (Map.Entry<Integer, T> entry : dataMap.entrySet()) {

                /*System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  */
                hashOperations.put(key,entry.getKey(),entry.getValue());
            }

        }

        return hashOperations;
    }

    /**
     * 获得缓存的Map
     * @param key
     * @return
     */
    public static <T> Map<Integer,T> getCacheIntegerMap(String key/*,HashOperations<String,String,T> hashOperation*/)
    {
        Map<Integer, T> map = redisTemplate.opsForHash().entries(key);
        /*Map<String, T> map = hashOperation.entries(key);*/
        return map;
    }

}