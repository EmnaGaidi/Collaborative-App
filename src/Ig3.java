import javax.swing.*;
import java.awt.*;

public class Ig3 extends JPanel{
    private JLabel l1;
    private JTextArea ta1;
    private  JLabel l2;
    private JTextArea ta2;

    public Ig3(){
        ta1 = new JTextArea (5, 5);
        ta2 = new JTextArea (5, 5);
        ta1.setDisabledTextColor(Color.black);
        ta1.setBackground(Color.white);
        ta2.setDisabledTextColor(Color.black);
        ta2.setBackground(Color.white);
        ta1.setEnabled(false);
        ta2.setEnabled(false);
        ta1.setLineWrap(true);
        ta1.setWrapStyleWord(true);
        ta2.setLineWrap(true);
        ta2.setWrapStyleWord(true);

        l1 = new JLabel ("Text 1");
        l2 = new JLabel ("Text 2");
        l1.setForeground(new Color(70, 130, 180));
        l2.setForeground(new Color(70, 130, 180));
        setPreferredSize (new Dimension (750, 450));
        setLayout (null);
        add (ta1);
        add (ta2);
        add (l1);
        add (l2);

        ta1.setBounds (150, 50, 450, 150);
        ta2.setBounds (150, 250, 450, 150);
        l2.setBounds (100, 215, 100, 25);
        l1.setBounds (100, 15, 100, 25);
    }
    public void setTextArea1(String msg) {
        ta1.setText(msg);
    }

    public void setTextArea2Text(String message) {
        ta2.setText(message);
    }
    public static void main (String[] args) throws Exception {
        Ig3 i=new Ig3();
        task3.Connection1(i);
        task3.Connection2(i);
        JFrame frame = new JFrame ("Text Editor");
        frame.setResizable(false);
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add (i);
        frame.pack();
        frame.setVisible (true);

    }
}
