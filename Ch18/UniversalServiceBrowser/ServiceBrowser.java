import java.net.*;
import java.rmi.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ServiceBrowser {

    JPanel mainPanel;
    JComboBox<Object> serviceList;
    ServiceServer server;

    void loadService(Object serviceSelection) {

        try {
            Service svc = server.getService(serviceSelection);
            mainPanel.removeAll();
            mainPanel.add(svc.getGuiPanel());
            mainPanel.validate();
            mainPanel.repaint();

        } catch(RemoteException e) {
            e.printStackTrace();
        }
    }

    Object[] getServiceList() {
        Object obj = null;
        Object[] services = null;

        try {
            obj = Naming.lookup("rmi://127.0.0.1/ServiceServer");

        } catch(NotBoundException e) {
            e.printStackTrace();
        } catch(AccessException ex) {
            ex.printStackTrace();
        } catch(MalformedURLException exc) {
            exc.printStackTrace();
        } catch(RemoteException exce) {
            exce.printStackTrace();
        }

        server = (ServiceServer) obj;

        try {
            services = server.getServiceList();

        } catch(RemoteException e) {
            e.printStackTrace();
        }
        return services;
    }

    class MylistListener implements ActionListener {

        public void actionPerformed(ActionEvent ev) {
            Object selection = serviceList.getSelectedItem();
            loadService(selection);
        }
    }

    public void buildGUI() {
        JFrame frame = new JFrame("RMI Browser");
        mainPanel = new JPanel();
        Object[] services = getServiceList();
        serviceList = new JComboBox<Object>(services);

        serviceList.addActionListener(new MylistListener());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.getContentPane().add(BorderLayout.NORTH, serviceList);
        frame.setSize(500, 500);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new ServiceBrowser().buildGUI();
    }
}