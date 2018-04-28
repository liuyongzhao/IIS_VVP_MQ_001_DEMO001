package Provider;

import Utils.ConnectionUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ProviderC {
    public static void main(String[] argv) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConn();
        Channel channel = connection.createChannel();

        //设置属性
        final String AppId = "ConsumerC";
        AMQP.BasicProperties props = new AMQP.BasicProperties.Builder()
                .appId(AppId).build();
        //声明队列
        channel.exchangeDeclare("demoEx", "direct");
        String msg = "messageC";
        channel.basicPublish("demoEx", "c", props, msg.getBytes());
        channel.close();
        connection.close();
    }
}
