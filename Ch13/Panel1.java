import javax.swing.*;
import java.awt.*;

public class Panel1 {

    public void go() {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        JButton button = new JButton("Click me");
        JButton buttonTwo = new JButton("Click again");

        panel.setBackground(Color.darkGray);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(button);
        panel.add(buttonTwo);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(BorderLayout.EAST, panel);
        frame.setSize(200, 200);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Panel1 gui = new Panel1();
        gui.go();
    }
}