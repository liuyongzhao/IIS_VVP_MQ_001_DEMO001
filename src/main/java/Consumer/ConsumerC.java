package Consumer;

import Utils.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConsumerC {
    static String appId = "ConsumerB";
    public static void main(String[] argv) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConn();
        final Channel channel = connection.createChannel();

    //声明队列并绑定到交换器上
    channel.queueDeclare("consumerC",false,false,true,null);
    channel.exchangeDeclare("demoEx", "direct");
    channel.queueBind("consumerC","demoEx","c");
    channel.basicQos(1);

    channel.basicConsume("consumerC",false, new DefaultConsumer(channel){
        @Override
        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
            if (properties.getAppId().equals(appId)) {
                String message = new String(body, "UTF-8");
                System.out.println("三号消费者接收到的消息是： '" + message + "'");
            }
            channel.basicAck(envelope.getDeliveryTag(),false);
        }
    });

}

}
