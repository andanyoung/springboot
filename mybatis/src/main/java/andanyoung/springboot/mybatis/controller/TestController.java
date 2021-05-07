package andanyoung.springboot.mybatis.controller;

import andanyoung.springboot.mybatis.PageVo;
import andanyoung.springboot.mybatis.entity.User;
import andanyoung.springboot.mybatis.entity.UserLoginRecord;
import andanyoung.springboot.mybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

  @Autowired private UserService userService;

  @RequestMapping("/getUserByName/{name}")
  public User getUserByName(@PathVariable String name) {
    return userService.getUserByName(name);
  }

  @RequestMapping("/getUserLoginRecords")
  public List<UserLoginRecord> getUserLoginRecords() {
    return userService.getUserLoginRecord();
  }

  @RequestMapping("/getUserLoginRecordsPage")
  public PageVo<UserLoginRecord> getUserLoginRecordsPage() {
    return userService.getUserLoginRecord(1, 10);
  }
}
