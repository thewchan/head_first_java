import java.rmi.*;
import java.net.*;

public class MyRemoteClient {

    public void go() {

        try {
            MyRemote service = (MyRemote) Naming.lookup("rmi://127.0.0.1/Remote Hello");
            String s = service.sayHello();
        
            System.out.println(s);

        } catch(MalformedURLException e) {
            e.printStackTrace();
        } catch(AccessException ex) {
            ex.printStackTrace();
        } catch(RemoteException exc) {
            exc.printStackTrace();
        } catch(NotBoundException exce) {
            exce.printStackTrace();
        }
    }
}