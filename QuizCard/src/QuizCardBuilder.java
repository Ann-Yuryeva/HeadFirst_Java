import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class QuizCardBuilder {
    private JTextArea question;
    private JTextArea answer;
    private ArrayList <QuizCard> cardList;
    private JFrame frame;

    public static void main(String[] args) {
        QuizCardBuilder builder = new QuizCardBuilder();
        builder.go();
    }
    public void go(){
        frame = new JFrame();
        JPanel mainPanel = new JPanel();
        Font bigFont = new Font("sanserif", Font.BOLD, 24);
        question = new JTextArea(6,20);
        question.setLineWrap(true);
        question.setWrapStyleWord(true);
        question.setFont(bigFont);

        JScrollPane qScroller = new JScrollPane(question);
        qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        answer = new JTextArea(6,20);
        answer.setLineWrap(true);
        answer.setWrapStyleWord(true);
        answer.setFont(bigFont);

        JScrollPane aScroller = new JScrollPane(answer);
        aScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        aScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JButton nextButton = new JButton("Next Card");

        cardList = new ArrayList<QuizCard>();

        JLabel qLabel = new JLabel("Question:");
        JLabel aLabel = new JLabel("Answer:");

        mainPanel.add(qLabel);
        mainPanel.add(qScroller);
        mainPanel.add(aLabel);
        mainPanel.add(aScroller);
        mainPanel.add(nextButton);
        nextButton.addActionListener(new NextCardListener());
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem newMenuItem = new JMenuItem("New");

        JMenuItem saveMenuItem = new JMenuItem("Save");
        newMenuItem.addActionListener(new NewMenuListener());

        saveMenuItem.addActionListener(new SaveMenuListener());
        fileMenu.add(newMenuItem);
        fileMenu.add(saveMenuItem);
        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);
        frame.getContentPane().add(BorderLayout.CENTER,mainPanel);
        frame.setSize(500,600);
        frame.setVisible(true);
    }
//    внутр. класс срабатывает при нажатии пользователем кнопки Next Card
    private class NextCardListener implements ActionListener{
        public void actionPerformed(ActionEvent ev){
//            Добавляем текущую карточку в список и очищаем текстовые области
            QuizCard card = new QuizCard(question.getText(), answer.getText());
            cardList.add(card);
            clearCard();
        }
    }
//    Запускается при выборе команды Save(Сохранить) из меню File.
//     Означает, что пользователь хочет сохранить все карточки в текущем списке в виде набора
//    (напр. набор карточек о фактах, правилах).
    private class SaveMenuListener implements ActionListener{
        public void actionPerformed(ActionEvent cv){
//        Очищаем список карточек и текстовые области
            QuizCard card = new QuizCard(question.getText(), answer.getText());
            cardList.add(card);
//      Вызывается диалоговое окно, и программа останавливается на этой строке, пока пользователь
//      не выберет меню Save. Всю навигацию, выбор файла и тд за вас выполняет класс JFileChooser!
            JFileChooser fileSave = new JFileChooser();
            fileSave.showSaveDialog(frame);
            saveFile(fileSave.getSelectedFile());

        }
    }

//    Запускается при выборе команды New из меню File.
//     Означает, что пользователь хочет создать новый набор
//     ( очистив список карточек и текстовые области )
    private class NewMenuListener implements ActionListener{
        public void actionPerformed(ActionEvent ev){
//            Очищаем список карточек и текстовые области
            cardList.clear();
            clearCard();
        }
    }

    private void clearCard(){
        question.setText("");
        answer.setText("");
        question.requestFocus();
    }

//    Вызывается классов SaveMenuListener.
//    Непосредственно записывает данные в файл. (вызывается обработчиком событий класса Save-MenuListener)
//    Аргумент - это объект File, который сохраняется пользователем.
    private void saveFile(File file){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            for(QuizCard card: cardList) {
                writer.write(card.getQuestion() + "/");
                writer.write(card.getAnswer() + "\n");
            }
            writer.close();
        } catch (IOException ex) {
            System.out.println("couldn't write the cardList out");
            ex.printStackTrace();
        }
//   Проходим по списку карточек и записываем
//        каждый элемент в текстовый файл, который потом
//        можно будет прочитать
//        (то еcть с чистыми разделителями между частями)
    }
}

