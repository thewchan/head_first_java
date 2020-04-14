import javax.swing.*;
import java.awt.*;

class MyDrawPanel extends JPanel {

    public void paintComponent(Graphics g) {
        g.setColor(Color.orange);
        g.fillRect(20, 50, 100, 100);
    }
}

public class SimpleGui2 {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        MyDrawPanel drawPanel = new MyDrawPanel();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(BorderLayout.CENTER, drawPanel);
        frame.setSize(300, 300);
        frame.setVisible(true);
    }
}