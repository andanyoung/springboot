package andanyoung.rabbitmq.demo.customer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author andanyoung
 * @version 1.0
 * @date 2021/4/15 23:54
 */

@Component
@RabbitListener(queues = Customer.QUEUES_DIRECT)
public class Customer {

    public  final static String QUEUES_DIRECT ="queues_direct";

    @RabbitHandler
    void getMsg(String msg){
        System.out.println("直接模式消费消息1:"+msg);
    }


}
