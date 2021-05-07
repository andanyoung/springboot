package andanyoung.springboot.mybatis.service;

import andanyoung.springboot.mybatis.PageVo;
import andanyoung.springboot.mybatis.entity.User;
import andanyoung.springboot.mybatis.entity.UserLoginRecord;

import java.util.List;

public interface UserService {
  User getUserByName(String name);

  List<UserLoginRecord> getUserLoginRecord();

  PageVo<UserLoginRecord> getUserLoginRecord(int page, int size);
}
