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

public class TwoButtons {

    JFrame frame;
    JLabel label;

    class LabelListener implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            label.setText("Ouch!");
        }
    }

    class ColorListener implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            frame.repaint();
        }
    }

    public void go() {
        frame = new JFrame();
        label = new JLabel("I'm a label");
        MyDrawPanel drawPanel = new MyDrawPanel();
        JButton labelButton = new JButton("Change Label");
        JButton colorButton = new JButton("Change Circle");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(BorderLayout.SOUTH, colorButton);
        frame.getContentPane().add(BorderLayout.CENTER, drawPanel);
        frame.getContentPane().add(BorderLayout.EAST, labelButton);
        frame.getContentPane().add(BorderLayout.WEST, label);
        frame.setSize(600, 600);
        frame.setVisible(true);
        labelButton.addActionListener(new LabelListener());
        colorButton.addActionListener(new ColorListener());
    }

    public static void main(String[] args) {
        TwoButtons gui = new TwoButtons();
        gui.go();
    }
}