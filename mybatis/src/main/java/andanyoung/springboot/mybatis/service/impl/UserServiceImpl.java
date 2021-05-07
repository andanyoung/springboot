package andanyoung.springboot.mybatis.service.impl;

import andanyoung.springboot.mybatis.PageVo;
import andanyoung.springboot.mybatis.dao.UserLoginRecordMapper;
import andanyoung.springboot.mybatis.dao.UserMapper;
import andanyoung.springboot.mybatis.entity.User;
import andanyoung.springboot.mybatis.entity.UserLoginRecord;
import andanyoung.springboot.mybatis.service.UserService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

  @Resource UserMapper userMapper;
  @Resource UserLoginRecordMapper userLoginRecordMapper;

  @Override
  public User getUserByName(String name) {
    return userMapper.getUserByName(name);
  }

  @Override
  public List<UserLoginRecord> getUserLoginRecord() {

    PageHelper.startPage(0, 10);
    List<UserLoginRecord> userLoginRecords = userLoginRecordMapper.selectList();
    return userLoginRecords;
  }

  @Override
  public PageVo<UserLoginRecord> getUserLoginRecord(int page, int size) {
    PageHelper.startPage(page, size);
    List<UserLoginRecord> userLoginRecords = userLoginRecordMapper.selectList();
    PageVo<UserLoginRecord> userLoginRecordPageInfo = new PageVo<>(userLoginRecords);
    return userLoginRecordPageInfo;
  }
}
