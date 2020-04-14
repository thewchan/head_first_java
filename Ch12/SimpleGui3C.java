import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


class MyDrawPanel extends JPanel {

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        int red = (int) (Math.random() * 256);
        int green = (int) (Math.random() * 256);
        int blue = (int) (Math.random() * 256);
        
        Color startColor = new Color(red, green, blue);

        red = (int) (Math.random() * 256);
        green = (int) (Math.random() * 256);
        blue = (int) (Math.random() * 256);
        
        Color endColor = new Color(red, green, blue);

        GradientPaint gradient = new GradientPaint(70, 70, startColor, 150, 150, endColor);
        
        g2d.setPaint(gradient);
        g2d.fillOval(70, 70, 100, 100);
    }
}

public class SimpleGui3C implements ActionListener {

    JFrame frame;

    public void go() {
        frame = new JFrame();
        JButton button = new JButton("Change colors");
        MyDrawPanel drawPanel = new MyDrawPanel();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(BorderLayout.SOUTH, button);
        frame.getContentPane().add(BorderLayout.CENTER, drawPanel);
        frame.setSize(300, 300);
        frame.setVisible(true);

        button.addActionListener(this);
    }

    public void actionPerformed(ActionEvent event) {
        frame.repaint();
    }

    public static void main(String[] args) {
        SimpleGui3C gui = new SimpleGui3C();
        gui.go();
    }
}