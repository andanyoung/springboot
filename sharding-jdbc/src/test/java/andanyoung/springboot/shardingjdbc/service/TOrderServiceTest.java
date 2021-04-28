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

      System.out.println(order);

      TOrderItem orderItem = new TOrderItem();
      orderItem.setOrderNo(order.getOrderId().toString());
      orderItem.setItemName("服务项目" + i);
      orderItem.setPrice(new BigDecimal("" + i));
      orderItemMapper.insert(orderItem);
    }
  }

  @Test
  public void masterSlaveTest() {

    TOrder tOrder = orderMapper.selectById(1387233602040721410L);
    System.out.println(tOrder);

    TOrder order = new TOrder();
    order.setOrderId(1387233602040721410L);
    order.setCreateName("订单 主库master update");
    order.setPrice(new BigDecimal("2.8"));
    orderMapper.updateById(order);

    tOrder = orderMapper.selectById(1387233602040721410L);
    System.out.println(tOrder);
  }
}
