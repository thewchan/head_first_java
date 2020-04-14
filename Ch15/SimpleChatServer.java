import java.io.*;
import java.net.*;
import java.util.*;

public class SimpleChatServer {

    ArrayList<PrintWriter> clientOutputStreams;

    public void tellEveryone(String message) {
        Iterator<PrintWriter> it = clientOutputStreams.iterator();

        while(it.hasNext()) {
            PrintWriter writer = it.next();
            writer.println(message);
            writer.flush();
        }
    }

    public class ClientHandler implements Runnable {

        BufferedReader reader;
        Socket sock;

        public ClientHandler(Socket clientSocket) {

            try {
                sock = clientSocket;
                InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(isReader);

            } catch(IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            String message;

            try {

                while ((message = reader.readLine()) != null) {
                    System.out.println("read " + message);
                    tellEveryone(message);
                }

            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void go() {
        clientOutputStreams = new ArrayList<PrintWriter>();

        try {
            ServerSocket serverSock = new ServerSocket(5000);

            while (true) {
                Socket clientSocket = serverSock.accept();
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                Thread t = new Thread(new ClientHandler(clientSocket));

                clientOutputStreams.add(writer);
                t.start();
                System.out.println("Connection successful.");
            }

        } catch(SocketTimeoutException e) {
            e.printStackTrace();

        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new SimpleChatServer().go();
    }
}