
import java.rmi.*;

//Удаленный RMI-интерфейс для удаленного сервиса.

public interface ServiceServer extends Remote {
    Object[] getServiceList() throws RemoteException;
    Service getService(Object serviceKey) throws RemoteException;
}

