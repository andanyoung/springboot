package andanyoung.springboot.mybatis.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
  private static final long serialVersionUID = 5607141057471974365L;
  Integer uid;
  private String name;
  private String password;
  private String mobile;
  private String avatarUrl;
  private Integer status;
}
