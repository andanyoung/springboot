package andanyoung.rabbitmq.demo.customer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author andanyoung
 * @version 1.0
 * @date 2021/4/15 23:54
 */

@Component
@RabbitListener(queues = {"queues_4","queues_5","queues_6"} )
public class Customer3 {
    @RabbitHandler
    void getMsg(@Payload String body, @Headers Map<String,Object> headers){
        System.out.println(headers);
        System.out.println("主题模式（Topic）消费消息"+body);
    }


}
