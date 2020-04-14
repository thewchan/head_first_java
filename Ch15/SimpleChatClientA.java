import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SimpleChatClientA {

    JTextField outgoing;
    PrintWriter writer;
    Socket sock;

    public class SendButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent ev) {
            writer.println(outgoing.getText());
            writer.flush();
            outgoing.setText("");
            outgoing.requestFocus();
        }
    }

    private void setUpNetworking() {

        try {
            sock = new Socket("127.0.0.1", 5000);
            writer = new PrintWriter(sock.getOutputStream());

            System.out.println("Networking established.");

        } catch(UnknownHostException e) {
            e.printStackTrace();
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    public void go() {
        JFrame frame = new JFrame("Simple Chat Client");
        JPanel mainPanel = new JPanel();
        outgoing = new JTextField(20);
        JButton sendButton = new JButton("Send");

        sendButton.addActionListener(new SendButtonListener());
        mainPanel.add(outgoing);
        mainPanel.add(sendButton);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        setUpNetworking();
        frame.setSize(400, 500);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new SimpleChatClientA().go();
    }
}