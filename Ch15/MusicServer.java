import java.io.*;
import java.net.*;
import java.util.*;

public class MusicServer {

    ArrayList<ObjectOutputStream> clientOutputStreams;

    public void tellEveryone(Object one, Object two) {
        Iterator<ObjectOutputStream> it = clientOutputStreams.iterator();

        while (it.hasNext()) {

            try {
                ObjectOutputStream out = it.next();
                out.writeObject(one);
                out.writeObject(two);

            } catch(InvalidClassException e) {
                e.printStackTrace();

            } catch(NotSerializableException ex) {
                ex.printStackTrace();

            } catch(IOException exc) {
                exc.printStackTrace();
            }
        }
    }

    public class ClientHandler implements Runnable {

        ObjectInputStream in;
        Socket clientSocket;

        public ClientHandler(Socket socket) {

            try {
                clientSocket = socket;
                in = new ObjectInputStream(clientSocket.getInputStream());

            } catch(StreamCorruptedException e) {
                e.printStackTrace();

            } catch(IOException ex) {
                ex.printStackTrace();
            }
        }

        public void run() {
            Object o1 = null;
            Object o2 = null;

            try {

                while ((o1 = in.readObject()) != null) {
                    o2 = in.readObject();

                    System.out.println("Reading two objects.");
                    tellEveryone(o1, o2);
                }

            } catch(ClassNotFoundException e) {
                e.printStackTrace();

            } catch(InvalidClassException ex) {
                ex.printStackTrace();

            } catch(StreamCorruptedException exc) {
                exc.printStackTrace();

            } catch(OptionalDataException exce) {
                exce.printStackTrace();

            } catch(IOException excep) {
                excep.printStackTrace();
            }
        }
    }

    public void go() {
        clientOutputStreams = new ArrayList<ObjectOutputStream>();

        try {
            ServerSocket serverSock = new ServerSocket(4242);

            while (true) {
                Socket clientSocket = serverSock.accept();
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                
                clientOutputStreams.add(out);
                Thread t = new Thread(new ClientHandler(clientSocket));
                
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
        new MusicServer().go();
    }
}