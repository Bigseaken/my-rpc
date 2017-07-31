package com.cprc.utils;

import com.cprc.model.Respone;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by withqianqian@163.com on 2017/7/31.
 */
public class CacheUtils {
    private final static ConcurrentHashMap<String, Respone> cache = new ConcurrentHashMap<String, Respone>(256);

    /**
     * 设置缓存
     *
     * @param requestId
     * @param respone
     */
    public static void put(String requestId, Respone respone) {
        if (!cache.contains(requestId)) {
            cache.put(requestId, respone);
        }
    }

    /**
     * 获取缓存
     *
     * @param requestId
     * @return
     */
    public static Respone getCache(String requestId) {
        Respone respone = cache.get(requestId);
        cache.remove(respone);
        return respone;
    }

    public static Boolean contains(String requestId) {
        return cache.contains(requestId);
    }
}
