package andanyoung.redis.rediscluster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @author andanyang
 * @since 2022/3/29 13:05
 */
@RestController
public class RedisController {

    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @GetMapping("get")
    public String get(String key){

       return stringRedisTemplate.opsForValue().get(key);
    }

    @RequestMapping("put")
    public String put(String key,String value){

          stringRedisTemplate.opsForValue().set (key,value);
          return get(key);
    }
}
