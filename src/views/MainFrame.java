package views;

import views.components.MenuBar;
import views.panels.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import controller.SlangManager;
import model.SlangWord;

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

        wireActions();
        setVisible(true);
    }

    private void wireActions() {
        SlangManager sm = SlangManager.getInstance();

        // Random button
        randomPanel.btnRandom.addActionListener(e -> {
            SlangWord sw = sm.getRandom();
            if (sw != null) {
                randomPanel.taResult.setText(sw.toString());
            } else {
                randomPanel.taResult.setText("No data loaded.");
            }
        });

        // Search button
        searchPanel.getSearchButton().addActionListener(e -> {
            String query = searchPanel.getSearchField().getText();
            String mode = (String) searchPanel.getModeComboBox().getSelectedItem();
            if (mode == null) mode = "Slang";
            if (query == null || query.trim().isEmpty()) return;

            if (mode.equals("Slang")) {
                java.util.Optional<SlangWord> res = sm.findBySlang(query.trim());
                if (res.isPresent()) {
                    SlangWord sw = res.get();
                    definitionPanel.getSlangField().setText(sw.getSlang());
                    definitionPanel.getDefinitionArea().setText(String.join(" | ", sw.getMeanings()));
                    historyPanel.taHistory.append("Searched Slang: " + sw.getSlang() + System.lineSeparator());
                } else {
                    definitionPanel.getSlangField().setText(query.trim());
                    definitionPanel.getDefinitionArea().setText("Not found");
                    historyPanel.taHistory.append("Searched Slang: " + query.trim() + " (not found)" + System.lineSeparator());
                }
            } else { // Definition mode
                java.util.List<SlangWord> list = sm.findByDefinitionContains(query.trim());
                if (!list.isEmpty()) {
                    // Show first in definition panel, and list all in history
                    SlangWord first = list.get(0);
                    definitionPanel.getSlangField().setText(first.getSlang());
                    definitionPanel.getDefinitionArea().setText(String.join(" | ", first.getMeanings()));
                    historyPanel.taHistory.append("Search Definition contains: '" + query.trim() + "' -> " + list.size() + " result(s)" + System.lineSeparator());
                } else {
                    definitionPanel.getSlangField().setText("");
                    definitionPanel.getDefinitionArea().setText("No matches");
                    historyPanel.taHistory.append("Search Definition contains: '" + query.trim() + "' (no matches)" + System.lineSeparator());
                }
            }
        });
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
