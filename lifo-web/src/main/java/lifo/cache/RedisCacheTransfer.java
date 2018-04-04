package lifo.cache;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
/**
 * RedisCacheTransfer中间类
 * 用于注入静态对象jedisConnectionFactory
 */
public class RedisCacheTransfer {
	
	private JedisConnectionFactory jedisConnectionFactory;

	public JedisConnectionFactory getJedisConnectionFactory() {
		return jedisConnectionFactory;
	}

	@Autowired
	public void setJedisConnectionFactory(JedisConnectionFactory jedisConnectionFactory) {
		RedisCache.setJedisConnectionFactory(jedisConnectionFactory);
	}
	
	
}
