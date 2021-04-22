package andanyoung.springboot.redis.config.cache;

/** @author andyoung */
public enum RedisCacheType {

  /** 分布式缓存 */
  DEFAULT(0),
  DEFAULT_60(60);

  /** 缓存时间 */
  private final int expires;

  RedisCacheType(int expires) {
    this.expires = expires;
  }

  public int getExpires() {
    return expires;
  }
}
