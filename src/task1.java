import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

public class task1 {
    public static void sendMsg(String msg) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try(Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()){
            channel.queueDeclare("task1",false,false,false,null);
            channel.basicPublish("","task1",null,msg.getBytes(StandardCharsets.UTF_8));
        }
    }
}
