import java.net.*;
import java.rmi.*;
import java.rmi.server.*;

public class MyRemoteImpl extends UnicastRemoteObject implements MyRemote {

    public MyRemoteImpl() throws RemoteException {}

    public String sayHello() {
        return "Server say, 'Hey'";
    }

    public static void main(String[] args) {
        
        try {
            MyRemote service = new MyRemoteImpl();
            Naming.rebind("Remote Hello", service);
        
        } catch(MalformedURLException e) {
            e.printStackTrace();
        } catch(AccessException ex) {
            ex.printStackTrace();
        } catch(RemoteException exc) {
            exc.printStackTrace();
        }
    }

}