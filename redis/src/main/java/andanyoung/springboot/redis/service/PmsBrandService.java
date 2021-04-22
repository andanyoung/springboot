package andanyoung.springboot.redis.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class PmsBrandService {

  @CacheEvict(value = "DEFAULT", key = "'pms:brand:'+#id")
  public Object update(Long id, Object brand) {
    HashMap<String, Object> stringObjectHashMap = new HashMap<>();
    stringObjectHashMap.put("username", "andyoung");
    stringObjectHashMap.put("password", "123456");

    return stringObjectHashMap;
  }

  @CacheEvict(value = "DEFAULT", key = "'pms:brand:'+#id")
  public int delete(Long id) {
    return 1;
  }

  @Cacheable(value = "DEFAULT", key = "'pms:brand:'+#id", unless = "#result==null")
  public Object getItem(Long id) {
    HashMap<String, Object> stringObjectHashMap = new HashMap<>();
    stringObjectHashMap.put("username", "andanyoung");
    stringObjectHashMap.put("password", "andanyoung");
    return stringObjectHashMap;
  }
}
