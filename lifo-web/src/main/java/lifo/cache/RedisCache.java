package lifo.cache;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
/**
 * 自定义缓存类，实现Mybatis接口Cache
 * 用Redis做Mybatis二级缓存
 */
public class RedisCache implements Cache{
	
	private static final Logger logger = LoggerFactory.getLogger(RedisCache.class);
	private static JedisConnectionFactory jedisConnectionFactory;
	private final String id;
	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	
	public static JedisConnectionFactory getJedisConnectionFactory() {
		return jedisConnectionFactory;
	}

	public static void setJedisConnectionFactory(JedisConnectionFactory jedisConnectionFactory) {
		RedisCache.jedisConnectionFactory = jedisConnectionFactory;
	}

	public RedisCache(String id) {
		if(id == null) {
			throw new IllegalArgumentException("需要一个缓存ID");
		}
		logger.debug("RedisCache: id=" + id);
		this.id = id;
	}

	@Override
	public String getId() {
		return this.id;
	}
	/**
	 * 序列化对象
	 * 写入Redis缓存，key-value形式
	 */
	@Override
	public void putObject(Object key, Object value) {
		RedisConnection redisConnection = jedisConnectionFactory.getConnection();
		RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer();
		redisConnection.set(serializer.serialize(key),serializer.serialize(value));
		redisConnection.close();
	}
	/**
	 * 反序列化对象，
	 * 从Redis中获取缓存
	 */
	@Override
	public Object getObject(Object key) {
		RedisConnection redisConnection = jedisConnectionFactory.getConnection();
		RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer();
		Object result = serializer.deserialize(redisConnection.get(serializer.serialize(key)));
		redisConnection.close();
		return result;
	}
	/**
	 * 移除单个缓存
	 */
	@Override
	public Object removeObject(Object key) {
		RedisConnection redisConnection = jedisConnectionFactory.getConnection();
		RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer();
		Object result = redisConnection.expire(serializer.serialize(key), 0);
		redisConnection.close();
		return result;
	}
	/**
	 * 清空所有缓存
	 */
	@Override
	public void clear() {
		RedisConnection redisConnection = jedisConnectionFactory.getConnection();
		redisConnection.flushDb();
		redisConnection.flushAll();
		redisConnection.close();
	}
	/**
	 * 获取缓存数量
	 */
	@Override
	public int getSize() {
		RedisConnection redisConnection = jedisConnectionFactory.getConnection();
		int result = redisConnection.dbSize().intValue();
		redisConnection.close();
		return result;
	}

	@Override
	public ReadWriteLock getReadWriteLock() {
		return this.readWriteLock;
	}
	
	
}