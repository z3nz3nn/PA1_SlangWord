package views;

import views.components.MenuBar;
import views.components.ConfirmDialog;
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
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        MenuBar mb = new MenuBar();
        setJMenuBar(mb);

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

        add(left, BorderLayout.WEST);
        add(center, BorderLayout.CENTER);

        wireActions(mb);
        setVisible(true);
    }

    private void wireActions(MenuBar menuBar) {
        SlangManager sm = SlangManager.getInstance();

        // Menu: Search -> Search in dictionary (focus search field)
        menuBar.miSearchDictionary.addActionListener(e -> {
            searchPanel.getSearchField().requestFocusInWindow();
        });
        // Menu: Search -> History (show history area)
        menuBar.miHistory.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, new JScrollPane(historyPanel.taHistory), "History", JOptionPane.PLAIN_MESSAGE);
        });

        // Menu: Modify -> Add Slang
        menuBar.miAdd.addActionListener(e -> {
            Object[] addOptions = {"Add", "Cancel"};
            int result = JOptionPane.showOptionDialog(
                    this,
                    addPanel,
                    "Add Slang",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    addOptions,
                    addOptions[0]
            );
            if (result == 0) { // "Add"
                String slang = addPanel.tfSlang.getText();
                String def = addPanel.tfDefinition.getText();
                if (slang == null || slang.trim().isEmpty() || def == null || def.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter both slang and definition.");
                    return;
                }
                boolean overwrite = true;
                if (sm.contains(slang.trim())) {
                    boolean confirm = ConfirmDialog.show("Duplicate Slang", "Slang exists. Overwrite? Click No to append as duplicate.");
                    overwrite = confirm; // yes -> overwrite, no -> duplicate
                }
                sm.addOrUpdate(slang.trim(), def.trim(), overwrite);
                System.out.println("Added/Updated: " + slang.trim());
                addPanel.tfSlang.setText("");
                addPanel.tfDefinition.setText("");
            }
        });

        // Menu: Modify -> Edit Slang
        menuBar.miEdit.addActionListener(e -> {
            Object[] editOptions = {"Update", "Cancel"};
            int result = JOptionPane.showOptionDialog(
                    this,
                    editPanel,
                    "Edit Slang",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    editOptions,
                    editOptions[0]
            );
            if (result == 0) { // "Update"
                String slang = editPanel.tfSlang.getText();
                String newDef = editPanel.tfNewDefinition.getText();
                if (slang == null || slang.trim().isEmpty() || newDef == null || newDef.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter slang and new definition.");
                    return;
                }
                if (!sm.contains(slang.trim())) {
                    JOptionPane.showMessageDialog(this, "Slang not found: " + slang.trim());
                    return;
                }
                sm.edit(slang.trim(), newDef.trim());
                System.out.println("Edited: " + slang.trim());
                editPanel.tfSlang.setText("");
                editPanel.tfNewDefinition.setText("");
            }
        });

        // Menu: Modify -> Delete Slang
        menuBar.miDelete.addActionListener(e -> {
            Object[] deleteOptions = {"Delete", "Cancel"};
            int result = JOptionPane.showOptionDialog(
                    this,
                    deletePanel,
                    "Delete Slang",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null,
                    deleteOptions,
                    deleteOptions[0]
            );
            if (result == 0) { // "Delete"
                String slang = deletePanel.tfSlang.getText();
                if (slang == null || slang.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter slang to delete.");
                    return;
                }
                if (!sm.contains(slang.trim())) {
                    JOptionPane.showMessageDialog(this, "Slang not found: " + slang.trim());
                    return;
                }
                boolean confirm = ConfirmDialog.show("Confirm Delete", "Are you sure you want to delete '" + slang.trim() + "'?");
                if (confirm) {
                    sm.delete(slang.trim());
                    System.out.println("Deleted: " + slang.trim());
                    deletePanel.tfSlang.setText("");
                }
            }
        });

        // Menu: Modify -> Reset Original
        menuBar.miReset.addActionListener(e -> {
            boolean confirm = ConfirmDialog.show("Reset", "Reset to original slang list? This cannot be undone.");
            if (confirm) {
                sm.resetOriginal();
                System.out.println("Reset to original list");
            }
        });

        // Menu: Tools -> Random Slang Word (show Word of the Day)
        menuBar.miRandom.addActionListener(e -> {
            SlangWord sw = sm.getRandomOfTheDay();
            if (sw != null) {
                randomPanel.taResult.setText(sw.toString());
                JOptionPane.showMessageDialog(this, new JScrollPane(new JTextArea(sw.toString())), "Random (Word of the Day)", JOptionPane.PLAIN_MESSAGE);
            }
        });

        controller.QuizManager qm = new controller.QuizManager();
        // Menu: Quiz -> Guess Definition
        menuBar.miQuizSlang.addActionListener(e -> {
            controller.QuizManager.Question q = qm.generateSlangQuiz();
            if (q == null) {
                JOptionPane.showMessageDialog(this, "Not enough data for quiz.");
                return;
            }
            quizSlangPanel.lbSlang.setText("Slang: " + q.prompt);
            for (int i = 0; i < 4; i++) {
                quizSlangPanel.options[i].setText(q.options[i]);
                quizSlangPanel.options[i].setActionCommand(String.valueOf(i));
                quizSlangPanel.group.clearSelection();
            }
            quizSlangPanel.btnSubmit.addActionListener(ev -> {
                ButtonModel sel = quizSlangPanel.group.getSelection();
                if (sel == null) {
                    JOptionPane.showMessageDialog(this, "Please choose an option.");
                    return;
                }
                int idx = Integer.parseInt(sel.getActionCommand());
                JOptionPane.showMessageDialog(this, idx == q.correctIndex ? "Correct!" : "Wrong! Correct is option " + (q.correctIndex + 1));
            });
            JOptionPane.showMessageDialog(this, quizSlangPanel, "Quiz: Guess Definition", JOptionPane.PLAIN_MESSAGE);
            for (java.awt.event.ActionListener al : quizSlangPanel.btnSubmit.getActionListeners()) {
                quizSlangPanel.btnSubmit.removeActionListener(al);
            }
        });

        // Menu: Quiz -> Guess Slang
        menuBar.miQuizDefinition.addActionListener(e -> {
            controller.QuizManager.Question q = qm.generateDefinitionQuiz();
            if (q == null) {
                JOptionPane.showMessageDialog(this, "Not enough data for quiz.");
                return;
            }
            quizDefinitionPanel.lbDefinition.setText("Definition: " + q.prompt);
            for (int i = 0; i < 4; i++) {
                quizDefinitionPanel.options[i].setText(q.options[i]);
                quizDefinitionPanel.options[i].setActionCommand(String.valueOf(i));
                quizDefinitionPanel.group.clearSelection();
            }
            quizDefinitionPanel.btnSubmit.addActionListener(ev -> {
                ButtonModel sel = quizDefinitionPanel.group.getSelection();
                if (sel == null) {
                    JOptionPane.showMessageDialog(this, "Please choose an option.");
                    return;
                }
                int idx = Integer.parseInt(sel.getActionCommand());
                JOptionPane.showMessageDialog(this, idx == q.correctIndex ? "Correct!" : "Wrong! Correct is option " + (q.correctIndex + 1));
            });
            JOptionPane.showMessageDialog(this, quizDefinitionPanel, "Quiz: Guess Slang", JOptionPane.PLAIN_MESSAGE);
            for (java.awt.event.ActionListener al : quizDefinitionPanel.btnSubmit.getActionListeners()) {
                quizDefinitionPanel.btnSubmit.removeActionListener(al);
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
                    definitionPanel.getDefinitionArea().setText(sw.getMeaning());
                    historyPanel.taHistory.append("Searched Slang: " + sw.getSlang() + System.lineSeparator());
                } else {
                    definitionPanel.getSlangField().setText(query.trim());
                    definitionPanel.getDefinitionArea().setText("Not found");
                    historyPanel.taHistory.append("Searched Slang: " + query.trim() + " (not found)" + System.lineSeparator());
                }
            } else { // Definition mode
                java.util.List<SlangWord> list = sm.findByDefinitionContains(query.trim());
                if (!list.isEmpty()) {
                    // Switch panel into Search Results mode and show all matches as cards
                    definitionPanel.setTitle("Search Results");
                    definitionPanel.showResults(list);
                    historyPanel.taHistory.append("Search Definition contains: '" + query.trim() + "' -> " + list.size() + " result(s)" + System.lineSeparator());
                } else {
                    definitionPanel.setTitle("Search Results");
                    definitionPanel.showResults(java.util.Collections.emptyList());
                    historyPanel.taHistory.append("Search Definition contains: '" + query.trim() + "' (no matches)" + System.lineSeparator());
                }
            }
        });

        // Switch panel view when user changes mode
        ActionListener modeListener = e -> {
            String mode = (String) searchPanel.getModeComboBox().getSelectedItem();
            if (mode == null || mode.equals("Slang")) {
                definitionPanel.setTitle("Search Result");
                definitionPanel.showSingle();
            } else {
                definitionPanel.setTitle("Search Results");
                definitionPanel.showResults(java.util.Collections.emptyList());
            }
        };
        searchPanel.getModeComboBox().addActionListener(modeListener);
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
