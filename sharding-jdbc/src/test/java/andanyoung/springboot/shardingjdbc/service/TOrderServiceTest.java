package andanyoung.springboot.shardingjdbc.service;

import andanyoung.springboot.shardingjdbc.ShardingJdbcApplication;
import andanyoung.springboot.shardingjdbc.domain.TOrder;
import andanyoung.springboot.shardingjdbc.domain.TOrderItem;
import andanyoung.springboot.shardingjdbc.mapper.TOrderItemMapper;
import andanyoung.springboot.shardingjdbc.mapper.TOrderMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;

@SpringBootTest(classes = ShardingJdbcApplication.class)
class TOrderServiceTest {

  @Resource TOrderMapper orderMapper;
  @Resource TOrderItemMapper orderItemMapper;

  @Test
  public void insertOrderTest() {

    for (int i = 0; i < 4; i++) {

      TOrder order = new TOrder();
      order.setOrderNo("A000" + i);
      order.setCreateName("订单 " + i);
      order.setPrice(new BigDecimal("" + i));
      orderMapper.insert(order);

      TOrderItem orderItem = new TOrderItem();
      orderItem.setOrderNo(order.getOrderId().toString());
      orderItem.setOrderNo("A000" + i);
      orderItem.setItemName("服务项目" + i);
      orderItem.setPrice(new BigDecimal("" + i));
      orderItemMapper.insert(orderItem);
    }
  }
}
