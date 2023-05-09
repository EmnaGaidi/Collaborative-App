import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class task3 {
    public static void Connection1(Ig3 i) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("task1",false,false,false,null);
        DeliverCallback deliverCallback = (consumerTag, deliverCallback1)->{
            String msg = new String(deliverCallback1.getBody(),"UTF-8");
            i.setTextArea1(msg);
        };
        channel.basicConsume("task1",true,deliverCallback,consumerTag -> {});
    }
    public static void Connection2(Ig3 i) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("task2",false,false,false,null);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            i.setTextArea2Text(message);
        };

        channel.basicConsume("task2", true, deliverCallback, consumerTag -> {});
    }
}
