package views.components;

import javax.swing.*;

public class MenuBar extends JMenuBar {

    public JMenu menuSearch;
    public JMenu menuModify;
    public JMenu menuTools;
    public JMenu menuQuiz;

    public JMenuItem miSearchDictionary;
    public JMenuItem miHistory;

    public JMenuItem miAdd;
    public JMenuItem miEdit;
    public JMenuItem miDelete;
    public JMenuItem miReset;

    public JMenuItem miRandom;

    public JMenuItem miQuizSlang;
    public JMenuItem miQuizDefinition;

    public MenuBar() {
        // Search Menu
        menuSearch = new JMenu("Search");
        miSearchDictionary = new JMenuItem("Search in dictionary");
        miHistory = new JMenuItem("Search History");

        menuSearch.add(miSearchDictionary);
        menuSearch.addSeparator();
        menuSearch.add(miHistory);

        // Modify Menu
        menuModify = new JMenu("Modify");
        miAdd = new JMenuItem("Add Slang");
        miEdit = new JMenuItem("Edit Slang");
        miDelete = new JMenuItem("Delete Slang");
        miReset = new JMenuItem("Reset Original");

        menuModify.add(miAdd);
        menuModify.add(miEdit);
        menuModify.add(miDelete);
        menuModify.addSeparator();
        menuModify.add(miReset);

        // Tools Menu
        menuTools = new JMenu("Tools");
        miRandom = new JMenuItem("Random Slang Word");
        menuTools.add(miRandom);

        // Quiz Menu
        menuQuiz = new JMenu("Quiz");
        miQuizSlang = new JMenuItem("Quiz: Guess Definition");
        miQuizDefinition = new JMenuItem("Quiz: Guess Slang");
        menuQuiz.add(miQuizSlang);
        menuQuiz.add(miQuizDefinition);

        // Add menus
        add(menuSearch);
        add(menuModify);
        add(menuTools);
        add(menuQuiz);
    }
}
