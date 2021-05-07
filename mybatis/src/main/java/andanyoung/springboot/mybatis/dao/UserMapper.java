package andanyoung.springboot.mybatis.dao;

import andanyoung.springboot.mybatis.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {

  @Select("select * from user where id = #{id}")
  @Results({
    @Result(property = "name", column = "name"),
    @Result(property = "password", column = "password")
  })
  User getUser(Long id);

  @Select("select * from user where id = #{id} and user_name=#{name}")
  User getUserByIdAndName(@Param("id") Long id, @Param("name") String username);

  User getUserByName(String username);
}
