package andanyoung.springboot.mybatis.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserLoginRecord {
  private Integer id;
  private Integer uid;
  private Long ip;
  private LocalDateTime createTime;
}
