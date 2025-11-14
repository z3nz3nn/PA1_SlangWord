package views;

import views.components.MenuBar;
import views.panels.*;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private SearchPanel searchPanel;
    private DefinitionPanel definitionPanel;
    private HistoryPanel historyPanel;
    private RandomPanel randomPanel;

    private AddPanel addPanel;
    private EditPanel editPanel;
    private DeletePanel deletePanel;

    private QuizSlangPanel quizSlangPanel;
    private QuizDefinitionPanel quizDefinitionPanel;

    public MainFrame() {
        setTitle("Slang Dictionary");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        setJMenuBar(new MenuBar());

        searchPanel = new SearchPanel();
        definitionPanel = new DefinitionPanel();
        historyPanel = new HistoryPanel();
        randomPanel = new RandomPanel();

        addPanel = new AddPanel();
        editPanel = new EditPanel();
        deletePanel = new DeletePanel();

        quizSlangPanel = new QuizSlangPanel();
        quizDefinitionPanel = new QuizDefinitionPanel();

        JPanel left = new JPanel(new BorderLayout());
        left.add(searchPanel, BorderLayout.NORTH);
        left.add(historyPanel, BorderLayout.CENTER);

        JPanel center = new JPanel(new BorderLayout());
        center.add(definitionPanel, BorderLayout.CENTER);
        center.add(randomPanel, BorderLayout.SOUTH);

        add(left, BorderLayout.WEST);
        add(center, BorderLayout.CENTER);

        setVisible(true);
    }

    public SearchPanel getSearchPanel() {
        return searchPanel;
    }

    public DefinitionPanel getDefinitionPanel() {
        return definitionPanel;
    }

    public HistoryPanel getHistoryPanel() {
        return historyPanel;
    }

    public RandomPanel getRandomPanel() {
        return randomPanel;
    }

    public AddPanel getAddPanel() {
        return addPanel;
    }

    public EditPanel getEditPanel() {
        return editPanel;
    }

    public DeletePanel getDeletePanel() {
        return deletePanel;
    }

    public QuizSlangPanel getQuizSlangPanel() {
        return quizSlangPanel;
    }

    public QuizDefinitionPanel getQuizDefinitionPanel() {
        return quizDefinitionPanel;
    }
}
