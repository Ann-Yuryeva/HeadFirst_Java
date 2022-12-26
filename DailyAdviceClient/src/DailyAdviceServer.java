import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class DailyAdviceServer {
    String[] adviceList = {"Ешьте меньшими порциями.",
            "Купите облегающие джинсы. Нет, они не делают вас полнее.",
            "Два слова: не годится.","Будьте честны хотя бы сегодня.",
            "Скажите своему productowner'у всё, что вы *на самом деле* о нем думаете.",
            "Возможно, вам стоит подобрать другую прическу.",
            "Прежде чем выходить из дома, взгляни на себя, ты - красавичик.",
            "У тебя всё получится, а если нет - постарайся.",
            "Кажется стоит сегодня позаниматься спортом.","Ты точно хорошо себя чувствуешь?",
            "Стоит подумать ещё.","Выйди на улицу, освежись."};
    public void go(){
        try{
            ServerSocket serverSocket = new ServerSocket(4242);

            while (true){
                Socket sock = serverSocket.accept();

                PrintWriter writer = new PrintWriter(sock.getOutputStream());
                String advice = getAdvice();
                writer.println(advice);
                writer.close();
                System.out.println(advice);
            }

        }catch (IOException ex){
            ex.printStackTrace();
        }
    }
    private String getAdvice(){
        int random = (int) (Math.random() * adviceList.length);
        return adviceList[random];
    }

    public static void main(String[] args) {
        DailyAdviceServer server = new DailyAdviceServer();
        server.go();
    }
}
