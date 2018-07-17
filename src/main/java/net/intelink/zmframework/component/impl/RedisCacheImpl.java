package net.intelink.zmframework.component.impl;

import net.intelink.zmframework.component.ICache;
import net.intelink.zmframework.constants.SysConstants;
import net.intelink.zmframework.exception.BaseException;
import net.intelink.zmframework.exception.ServiceException;
import net.intelink.zmframework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis缓存实现
 */
@Component
public class RedisCacheImpl implements ICache {

    @Autowired
    private RedisOperations<String, String> stringRedisTemplate;


    @Override
    public void put(String key, String value){
        stringRedisTemplate.opsForValue().set(key,value);
    }

    @Override
    public void put(String key, String value, long expire){
        stringRedisTemplate.opsForValue().set(key,value,expire, TimeUnit.SECONDS);
    }

    @Override
    public String get(String key){
        if(StringUtil.isEmpty(key))
            return "";
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public Long increment(String key, long delta){
        return stringRedisTemplate.opsForValue().increment(key,delta);
    }

    @Override
    public boolean exists(String key) {
        if(StringUtil.isEmpty(key))
            return false;
        return stringRedisTemplate.hasKey(key);
    }

    @Override
    public boolean expire(String key, Long seconds) {
        if(StringUtil.isEmpty(key))
            return false;
        return stringRedisTemplate.expire(key, seconds, TimeUnit.SECONDS);
    }

    public void expireBatch(String... keys){
        stringRedisTemplate.executePipelined(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                for (String key : keys) {
                    connection.pExpire(key.getBytes(Charset.forName("UTF8")), SysConstants.SESSION_VALID_SECONDS);
                }
                return null;
            }
        });

    }

    public void delKeyByPrefix(String prefix){
        if (StringUtil.isEmpty(prefix))
            throw new BaseException("Del key by prefix, but prefix is null.");
        Set<String> keys = stringRedisTemplate.keys(prefix + "*");
        stringRedisTemplate.delete(keys);
    }

    public void delKeyBySuffix(String suffix) {
        if (StringUtil.isEmpty(suffix))
            throw new ServiceException("del key by suffix ,but suffix is null.");
        Set<String> keys = stringRedisTemplate.keys("*" + suffix);
        stringRedisTemplate.delete(keys);
    }

    public void hput(String key,String hashKey, Object value) {
        HashOperations<String, String, String> stringObjectObjectHashOperations = stringRedisTemplate.opsForHash();
        stringObjectObjectHashOperations.put(key,hashKey,value.toString());
    }

    @Override
    public String hget(String key, String hashKey) {
        HashOperations<String, String, String> stringObjectObjectHashOperations = stringRedisTemplate.opsForHash();
        String valObj = stringObjectObjectHashOperations.get(key, hashKey);
        if(valObj != null)
            return (String) valObj;
        return null;

    }

    @Override
    public Long hdel(String key, String hashKey) {
        return stringRedisTemplate.opsForHash().delete(key,hashKey);
    }

    @Override
    public void delKey(String key) {
        stringRedisTemplate.delete(key);
    }


}
