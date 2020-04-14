import java.util.*;
import java.net.*;
import java.rmi.*;
import java.rmi.server.*;

public class ServiceServerImpl extends UnicastRemoteObject implements ServiceServer {

    HashMap<String, Service> serviceList;

    private void setUpServices() {
        serviceList = new HashMap<String, Service>();

        serviceList.put("Dice Rolling Service", new DiceService());
        serviceList.put("Day of the Week Service", new DayOfTheWeekService());
        serviceList.put("Visual Music Service", new MiniMusicService());
    }

    public ServiceServerImpl() throws RemoteException {
        setUpServices();
    }

    public Object[] getServiceList() {
        System.out.println("In remote");
        return serviceList.keySet().toArray();
    }

    public Service getService(Object serviceKey) throws RemoteException {
        Service theService = (Service) serviceList.get(serviceKey);
        return theService;
    }

    public static void main(String[] args) {
        
        try {
            Naming.rebind("ServiceServer", new ServiceServerImpl());

        } catch(MalformedURLException e) {
            e.printStackTrace();

        } catch(AccessException ex) {
            ex.printStackTrace();

        } catch(RemoteException exc) {
            exc.printStackTrace();
        }
    System.out.println("Remote service is running");
    }
}