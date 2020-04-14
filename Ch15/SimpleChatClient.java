import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SimpleChatClient {

    JTextArea incoming;
    JTextField outgoing;
    BufferedReader reader;
    PrintWriter writer;
    Socket sock;

    public class SendButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent ev)  {
            writer.println(outgoing.getText());
            writer.flush();
            outgoing.setText("");
            outgoing.requestFocus();
        }
    }

    public class IncomingReader implements Runnable {

        public void run() {
            String message;

            try {
                while ((message = reader.readLine()) != null);
                    System.out.println("read " + message);
                    incoming.append(message + "\n");

                } catch(IOException ex) {
                    ex.printStackTrace();
            }
        }
    }

    private void setUpNetworking() {

        try {
            sock = new Socket("127.0.0.1", 5000);
            InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());
            reader = new BufferedReader(streamReader);
            writer = new PrintWriter(sock.getOutputStream());
            
            System.out.println("Networking established.");

        } catch(UnknownHostException ex) {
            ex.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void go() {
        JFrame frame = new JFrame("Simple Chat Client");
        JPanel mainPanel = new JPanel();
        incoming = new JTextArea(15, 50);
        JScrollPane qScroller = new JScrollPane(incoming);
        outgoing = new JTextField(20);
        JButton sendButton = new JButton("Send");
        Thread readerThread = new Thread(new IncomingReader());

        setUpNetworking();
        incoming.setLineWrap(true);
        incoming.setWrapStyleWord(true);
        incoming.setEditable(false);
        qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        sendButton.addActionListener(new SendButtonListener());
        mainPanel.add(qScroller);
        mainPanel.add(outgoing);
        mainPanel.add(sendButton);
        readerThread.start();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setSize(800, 500);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SimpleChatClient client = new SimpleChatClient();
        client.go();
    }
}