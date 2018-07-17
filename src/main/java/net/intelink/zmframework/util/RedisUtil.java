package net.intelink.zmframework.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;


public class RedisUtil {

	private static Logger logger = LoggerFactory.getLogger(RedisUtil.class);

	private static RedisUtilManager cache;

	public void setCache(RedisUtilManager cache) {
		RedisUtil.cache = cache;
	}

	public static void flushExpire(String key) {
		if (StringUtil.isNotEmpty(key)) {
			cache.flushExpire(getByteKey(key));
		}
	}

	public static void flushExpire(String key, int expire) {
		if (StringUtil.isNotEmpty(key)) {
			cache.flushExpire(getByteKey(key), expire);
		}
	}

	/**
	 * 获得byte[]型的key
	 * 
	 * @param key
	 * @return
	 */
	private static byte[] getByteKey(String key) {
		return key.getBytes();
	}

	@SuppressWarnings("unchecked")
	private static <V> V get(byte[] key) {
		try {
			byte[] rawValue = cache.get(key);
//			return (V) SerializeUtils.deserialize(rawValue);
			return null;
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public static <V> V get(String key) {
		logger.debug("根据key从Redis中获取对象 key [" + key + "]");
		if (key == null) {
			return null;
		} else {
			return get(getByteKey(key));
		}
	}

	public static <V> V put(String key, V value) {
		logger.debug("根据key从存储 key [" + key + "]");
		try {
//			cache.set(getByteKey(key), SerializeUtils.serialize(value));
			return value;
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public static <V> V remove(String key) {
		logger.debug("从redis中删除 key [" + key + "]");
		try {
			V previous = get(key);
			cache.del(getByteKey(key));
			return previous;
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public static void clear() {
		logger.debug("从redis中删除所有元素");
		try {
			cache.flushDB();
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public static int size() {
		try {
			Long longSize = new Long(cache.dbSize());
			return longSize.intValue();
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public static Set<String> keys() {
		try {
			Set<byte[]> keys = cache.keys("*");
			if (CollectionUtils.isEmpty(keys)) {
				return Collections.emptySet();
			} else {
				Set<String> newKeys = new HashSet<>();
				for (byte[] key : keys) {
					String kk = new String(key);
					newKeys.add(kk);
				}
				return newKeys;
			}
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public static <V> Collection<V> values() {
		try {
			Set<byte[]> keys = cache.keys("*");
			if (!CollectionUtils.isEmpty(keys)) {
				List<V> values = new ArrayList<V>(keys.size());
				for (byte[] key : keys) {
					V value = get(key);
					if (value != null) {
						values.add(value);
					}
				}
				return Collections.unmodifiableList(values);
			} else {
				return Collections.emptyList();
			}
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}
}
