package andanyoung.springboot.shardingjdbc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "andanyoung.springboot.shardingjdbc.mapper")
@SpringBootApplication
public class ShardingJdbcApplication {

  public static void main(String[] args) {

    SpringApplication.run(ShardingJdbcApplication.class, args);
  }
}
