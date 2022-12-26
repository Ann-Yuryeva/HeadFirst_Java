import java.io.Serializable;
import javax.swing.*;
import java.io.*;
//Не удаленный интерфейс, описывающий единственный метод,
// которым должен обладать любой универсальный сервис.
public interface Service extends Serializable {
    public JPanel getGuiPanel();
}
