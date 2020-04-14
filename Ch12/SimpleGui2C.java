import javax.swing.*;
import java.awt.*;

class MyDrawPanel extends JPanel {

    public void paintComponent(Graphics g) {
        int red = (int) (Math.random() * 256);
        int green = (int) (Math.random() * 256);
        int blue = (int) (Math.random() * 256);
        Color randomColor = new Color(red, green, blue);

        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(randomColor);
        g.fillOval(70, 70, 100, 100);

    }
}

public class SimpleGui2C {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        MyDrawPanel drawPanel = new MyDrawPanel();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(BorderLayout.CENTER, drawPanel);
        frame.setSize(300, 300);
        frame.setVisible(true);
    }
}