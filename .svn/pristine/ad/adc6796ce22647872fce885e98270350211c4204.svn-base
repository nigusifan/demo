package net.intelink.zmframework.component;

/**
 * 缓存操作接口
 */
public interface ICache {

    void put(String key, String value);

    void put(String key, String value, long expire);

    String get(String key);

    Long increment(String key, long delta);

    boolean exists(String key);

    boolean expire(String key,Long seconds);

    void delKeyByPrefix(String prefix);

    void delKeyBySuffix(String suffix);

    void hput(String key,String hashKey, Object value);

    String hget(String key,String hashKey);

    Long hdel(String key,String hashKey);

    void delKey(String key);

}
