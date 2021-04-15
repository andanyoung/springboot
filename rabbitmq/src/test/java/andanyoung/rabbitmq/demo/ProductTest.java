package andanyoung.rabbitmq.demo;

import andanyoung.rabbitmq.demo.customer.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author andanyoung
 * @version 1.0
 * @date 2021/4/15 23:50
 */
@SpringBootTest(classes = RabbitmqApplication.class)
public class ProductTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 直接模式
     */
    @Test
    void sendMsg(){
        rabbitTemplate.convertAndSend(Customer.QUEUES_DIRECT,"直接模式测试1");
        rabbitTemplate.convertAndSend(Customer.QUEUES_DIRECT,"直接模式测试2");
        rabbitTemplate.convertAndSend(Customer.QUEUES_DIRECT,"直接模式测试3");
    }

    /**
     * 分列模式（Fanout） 广播
     * 任何发送到Fanout Exchange的消息都会被转发到与该Exchange绑定(Binding)的所有
     * Queue上。
     */
    @Test
    void  sendMsg2(){
        rabbitTemplate.convertAndSend("ex_fanout","分列模式（Fanout）测试");
    }

    /**
     * 主题模式（Topic）
     * 任何发送到Topic Exchange的消息都会被转发到所有关心RouteKey中指定话题的Queue上
     */
    @Test
    void  sendMsg3(){
        rabbitTemplate.convertAndSend("ex_topic","good.log","主题模式（Topic）测试");
    }
}
