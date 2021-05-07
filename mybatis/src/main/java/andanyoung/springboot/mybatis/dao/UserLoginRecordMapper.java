package andanyoung.springboot.mybatis.dao;

import andanyoung.springboot.mybatis.entity.UserLoginRecord;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserLoginRecordMapper {
  @Select("SELECT * FROM `admin4j`.`sys_user_login_record`")
  List<UserLoginRecord> selectList();
}
