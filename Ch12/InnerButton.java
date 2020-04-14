import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class InnerButton {
    
    JFrame frame;
    JButton b;

    class BListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (b.getText().equals("A")) {
                b.setText("B");       
            } else {
                b.setText("A");
            }
        }
    }

    public void go() {
        frame = new JFrame();
        b = new JButton("A");

        b.addActionListener(new BListener());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(BorderLayout.SOUTH, b);
        frame.setSize(200, 100);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        InnerButton gui = new InnerButton();
        gui.go();
    }
}