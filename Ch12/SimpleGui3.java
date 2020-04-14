import javax.swing.*;
import java.awt.*;

class MyDrawPanel extends JPanel {

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        GradientPaint gradient = new GradientPaint(70, 70, Color.blue, 150, 150, Color.orange);

        g2d.setPaint(gradient);
        g2d.fillOval(70, 70, 100, 100);
    }
}

public class SimpleGui3 {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        MyDrawPanel drawPanel = new MyDrawPanel();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(BorderLayout.CENTER, drawPanel);
        frame.setSize(300, 300);
        frame.setVisible(true);
    }
}