import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class User1 {
    private final static String EXCHANGE_NAME = "p1";
    private final static String EXCHANGE_NAME2 = "priority";

    public static void main(String[] args) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        channel.exchangeDeclare(EXCHANGE_NAME2, "fanout");
        String queue_name = channel.queueDeclare().getQueue();
        channel.queueBind(queue_name, EXCHANGE_NAME, "");

        String queue_name2 = channel.queueDeclare().getQueue();
        channel.queueBind(queue_name2, EXCHANGE_NAME2, "");
        JLabel l1, l2;
        String msg = "";
        JFrame f = new JFrame();

        l2 = new JLabel();
        l2.setBounds(280, 220, 200, 30);

        f.setTitle("User1");
        l1 = new JLabel("ADD TEXT");
        l1.setForeground(Color.BLACK);
        l1.setBounds(100, 20, 200, 30);
        JTextArea text = new JTextArea(msg);
        text.setBounds(100, 70, 500, 100);

        JButton btn = new JButton("SEND TEXT");
        btn.setBackground(Color.white);
        btn.setBounds(100, 200, 200, 30);
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String message = text.getText();
                try {
                    channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                System.out.println("[user1] sent '" + message + "' ");
                String txt = "done";
                try {
                    channel.basicPublish(EXCHANGE_NAME2, "", null, txt.getBytes());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        text.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                String txt = "user1";
                try {
                    channel.basicPublish(EXCHANGE_NAME2, "", null, txt.getBytes());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        f.add(l1);
        f.add(l2);
        f.add(text);
        f.add(btn);
        f.setSize(700, 300);
        f.setLayout(null);
        f.setVisible(true);
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            text.setText(message);
        };
        channel.basicConsume(queue_name, true, "", deliverCallback, consumerTag -> {
        });

        DeliverCallback deliverCallback2 = (consumerTag, delivery) -> {
            String message_priority = new String(delivery.getBody(), "UTF-8");
            System.out.println(message_priority);
            if (message_priority.equals("user2") || message_priority.equals("user3")) {
                text.setEditable(false);
                btn.setEnabled(false);
                l2.setText("wait! "+message_priority + " is typing");
            } else if (message_priority.equals("done")) {
                text.setEditable(true);
                btn.setEnabled(true);
                l2.setText("");
            }

        };
        channel.basicConsume(queue_name2, true, "", deliverCallback2, consumerTag -> {
        });
    }

}
