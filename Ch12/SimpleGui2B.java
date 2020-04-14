import javax.swing.*;
import java.awt.*;

class MyDrawPanel extends JPanel {

    public void paintComponent(Graphics g) {
        Image image = new ImageIcon("zophie.jpg").getImage();
        g.drawImage(image, 3, 4, this);
    }
}

public class SimpleGui2B {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        MyDrawPanel drawPanel = new MyDrawPanel();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(BorderLayout.CENTER, drawPanel);
        frame.setSize(300, 300);
        frame.setVisible(true);
    }
}