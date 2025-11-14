package views.panels;

import javax.swing.*;
import java.awt.*;

public class SearchPanel extends JPanel {

    private JComboBox<String> modeComboBox;
    private JTextField searchField;
    private JButton searchButton;

    public SearchPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Search"));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel modeLabel = new JLabel("Search Mode:");
        modeComboBox = new JComboBox<>(new String[]{"Slang", "Definition"});

        topPanel.add(modeLabel);
        topPanel.add(modeComboBox);

        JPanel midPanel = new JPanel(new BorderLayout());
        searchField = new JTextField();
        midPanel.add(searchField, BorderLayout.CENTER);

        searchButton = new JButton("Search");
        midPanel.add(searchButton, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
        add(midPanel, BorderLayout.CENTER);
    }

    public JComboBox<String> getModeComboBox() {
        return modeComboBox;
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public JButton getSearchButton() {
        return searchButton;
    }
}
