import javax.swing.*;
import java.awt.*;

public class SimpleGui1 {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        JButton button = new JButton("click me");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MyDrawPanel draw = new MyDrawPanel();
        frame.getContentPane().add(BorderLayout.NORTH, button);
        frame.getContentPane().add(draw);
//        frame.getContentPane().add(button);
//        frame.getContentPane().add(draw);
        frame.setSize(300,300);
        frame.setVisible(true);

    }
}
