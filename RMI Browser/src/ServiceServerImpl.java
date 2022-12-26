import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
//Удаленный RMI-сервис.
// Создает экземпляры всех сервисов(сущностей, которые будут передаваться клиенту) и хранить их.
// Также вносит себя в реестр RMI.

public class ServiceServerImpl extends UnicastRemoteObject implements ServiceServer {
    // Реализация RMI.

    HashMap serviceList;

    // Сервисы храняться в коллекции HashMap.
    public ServiceServerImpl() throws RemoteException {
        setUpServices();
    }

    private void setUpServices() {
        serviceList = new HashMap();
        serviceList.put("Dice Rolling Service", new DiceService());
        serviceList.put("Day of the Week Service", new DayOfTheWeekService());
        serviceList.put("Visual Music Service", new MiniMusicService());
    }

    public Object[] getServiceList() {
        //Клиент вызывает этот метод чтобы получить список сервисов и отобразить их в обозревателе.
        // Отправляем массив типа Object, который состоит только из ключей, хранящихся внутри HashMap.
        // Сам сервис не отправляется пока клиент об этом не попросит.
        System.out.println("in remote");
        return serviceList.keySet().toArray();
    }

    public Service getService(Object serviceKey) throws RemoteException {
        // Клиент вызывает этот метод после того,
        // как пользователь выберет сервис в раскрывающемся списке.
        Service theService = (Service) serviceList.get(serviceKey);
        return theService;
    }

//    public static void main(String[] args) {
//        try {
//            Naming.rebind("ServiceServer", new ServiceServerImpl());
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        System.out.println("Remote service is running");
//    }

    public static void main(String[] args) {
        final String UNIQUE_BINDING_NAME = "ServiceServer";
        try {
            Registry registry = LocateRegistry.createRegistry(8010);
            registry.rebind(UNIQUE_BINDING_NAME, new ServiceServerImpl());
            System.out.println("export and binding of objects has been completed");
        } catch (Exception ex) {
            System.out.println("ServiceServer Error...");
            ex.printStackTrace();
        }
    }
}
