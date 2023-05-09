import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

public class task2 {
    public static void sendMsg(String msg) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try(Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()){
            channel.queueDeclare("task2", false,false,false,null);
            channel.basicPublish("","task2",null,msg.getBytes(StandardCharsets.UTF_8));
        }
    }
}
