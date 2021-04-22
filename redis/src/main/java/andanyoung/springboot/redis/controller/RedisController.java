package andanyoung.springboot.redis.controller;

import andanyoung.springboot.redis.utils.RedisTemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RedisController {

  @Autowired private RedisTemplateUtil redisTemplateUtil;

  /**
   * 测试简单缓存
   *
   * @return
   */
  @RequestMapping(value = "/simpleTest", method = RequestMethod.GET)
  @ResponseBody
  public Object simpleTest() {

    String key = "redis:simple:" + 1;
    redisTemplateUtil.set(key, "abc");
    Object o = redisTemplateUtil.get(key);
    return 0;
  }

  /**
   * 测试Hash结构的缓存
   *
   * @return
   */
  @RequestMapping(value = "/hashTest", method = RequestMethod.GET)
  @ResponseBody
  public Object hashTest() {

    String key = "redis:hash:" + 2;
    redisTemplateUtil.hSet(key, "username", "andanyoung");
    redisTemplateUtil.hSet(key, "password", 123456);
    return redisTemplateUtil.hGetAll(key);
  }
}
