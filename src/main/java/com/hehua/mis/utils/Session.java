package com.hehua.mis.utils;

import com.alibaba.fastjson.JSON;
import com.hehua.framework.cache.AbstractRedisCache;
import com.hehua.framework.jedis.PoolableJedisManager;

import java.util.concurrent.TimeUnit;

/**
 * Date 14/10/22.
 * Author WangJun
 * Email wangjuntytl@163.com
 */
public class Session extends AbstractRedisCache<String, String> {

    public Session() {
        super(PoolableJedisManager.getDefaultCacheJedis());
    }

    @Override
    public String encode(String object) {
        return JSON.toJSONString(object);
    }

    @Override
    public String decode(String object) {
        return JSON.parseObject(object, String.class);
    }

    @Override
    public String buildKey(String key) {
        return "hehua_mis_session:" + key;
    }

    public int getExpire() {
        return (int) TimeUnit.MINUTES.toSeconds(30);
    }




}
