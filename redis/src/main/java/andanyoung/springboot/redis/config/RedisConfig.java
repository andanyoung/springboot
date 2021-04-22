package andanyoung.springboot.redis.config;

import andanyoung.springboot.redis.config.cache.RedisCacheType;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@EnableCaching
@Configuration
public class RedisConfig extends CachingConfigurerSupport {

  /**
   * 自定义key生成器
   *
   * @return
   */
  @Bean
  @Override
  public KeyGenerator keyGenerator() {
    return (target, method, params) -> {
      // 采取拼接的方式生成key
      StringBuilder stringBuilder = new StringBuilder();
      // 目标类的类名
      stringBuilder.append(target.getClass().getName());
      stringBuilder.append(":");
      // 目标方法名
      stringBuilder.append(method.getName());
      // 参数
      for (Object object : params) {
        stringBuilder.append(":").append(object);
      }
      return stringBuilder;
    };
  }

  @Bean
  public RedisTemplate<String, Object> redisTemplate(
      RedisConnectionFactory redisConnectionFactory) {
    RedisSerializer<Object> serializer = RedisConfig.jackson2JsonRedisSerializer();
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisConnectionFactory);
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(serializer);
    redisTemplate.setHashKeySerializer(new StringRedisSerializer());
    redisTemplate.setHashValueSerializer(serializer);
    redisTemplate.afterPropertiesSet();
    return redisTemplate;
  }

  protected static RedisSerializer<Object> jackson2JsonRedisSerializer() {
    // 创建JSON序列化器
    Jackson2JsonRedisSerializer<Object> serializer =
        new Jackson2JsonRedisSerializer<>(Object.class);
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
    objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
    serializer.setObjectMapper(objectMapper);
    return serializer;
  }

  @Bean
  public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
    // 生成一个默认配置，通过config对象即可对缓存进行自定义配置
    RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
    // 设置缓存的默认过期时间，也是使用Duration设置
    config =
        config
            .entryTtl(Duration.ofMinutes(30L))
            // 设置 key为string序列化
            .serializeKeysWith(
                RedisSerializationContext.SerializationPair.fromSerializer(
                    new StringRedisSerializer()))
            // 设置value为json序列化
            .serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(
                    RedisConfig.jackson2JsonRedisSerializer()))
            // 不缓存空值
            .disableCachingNullValues();

    // 设置一个初始化的缓存空间set集合
    Set<String> cacheNames = new HashSet<>();
    // 对每个缓存空间应用不同的配置
    Map<String, RedisCacheConfiguration> configMap = new HashMap<>();

    for (RedisCacheType localCacheType : RedisCacheType.values()) {

      cacheNames.add(localCacheType.name());
      configMap.put(
          localCacheType.name(), config.entryTtl(Duration.ofMinutes(localCacheType.getExpires())));
    }

    // 使用自定义的缓存配置初始化一个cacheManager
    return RedisCacheManager.builder(redisConnectionFactory)
        .cacheDefaults(config)
        // 一定要先调用该方法设置初始化的缓存名，再初始化相关的配置
        .initialCacheNames(cacheNames)
        .withInitialCacheConfigurations(configMap)
        .transactionAware()
        .build();
  }
}
