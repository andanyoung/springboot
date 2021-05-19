package andanyoung.springboot.mybatisplus.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author andanyoung
 * @version 1.0
 * @date 2021/5/19 20:38
 */
@Data
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
    private Long managerId;
    private LocalDateTime createTime;
}
