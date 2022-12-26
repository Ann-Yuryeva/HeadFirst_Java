import java.awt.*;
import javax.swing.*;
import java.rmi.*;
import java.awt.event.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
// Клиент. Создает простой GUI и выполняет поиск по реестру RMI чтобы получить заглушку ServiceServer.
public class ServiceBrowser {
    JPanel mainPanel;
    JComboBox serviceList;
    ServiceServer server;

    public void buildGUI() {
        JFrame frame = new JFrame("RMI Browser");
        mainPanel = new JPanel();
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);

        Object[] services = getServicesList();
        // Метод выполняет поиск по реестру RMI, получает "заглушку" и вызывает метод getServiceList.

        serviceList = new JComboBox<Object>(services);
        //Сервисы добвляются в JComboBox.

        frame.getContentPane().add(BorderLayout.NORTH, serviceList);
        serviceList.addActionListener(new MyListListener());

        frame.setSize(500, 500);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void loadService(Object serviceSelection) {
        try {
            Service svc = server.getService(serviceSelection);
            mainPanel.removeAll();
            mainPanel.add(svc.getGuiPanel());
            mainPanel.revalidate();
            mainPanel.repaint();
        } catch (Exception ex) {
            System.out.println("loadService error");
            ex.printStackTrace();
        }
    }

    Object[] getServicesList() {
        Object obj = null;
        Object[] services = null;
        try {
//            obj = Naming.lookup("rmi://127.0.0.1/ServiceServer"); - Выполняется поиск по реестру и получаем "заглушку"
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 8010);
            //Get the reference of exported object from RMI Registry
            obj = registry.lookup("ServiceServer");
        } catch (Exception ex) {
            System.out.println("getService error get obj");
            ex.printStackTrace();
        }
        server = (ServiceServer) obj;
        // Приводится тип "заглушки" к типу удаленного интерфейса,
        // чтоб в дальнейшем вызвать из нее метод getServiceList.
        try {
            services = server.getServiceList();
        } catch (Exception ex) {
            System.out.println("getService error get services");
            ex.printStackTrace();
        }
        return services;
    }

    class MyListListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {

            Object selection = serviceList.getSelectedItem();
            loadService(selection);
        }
    }

    public static void main(String[] args) {
        new ServiceBrowser().buildGUI();
    }
}