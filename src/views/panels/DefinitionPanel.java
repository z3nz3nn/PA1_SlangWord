package views.panels;

import javax.swing.*;
import java.awt.*;

public class DefinitionPanel extends JPanel {

    private JTextField slangField;
    private JTextArea definitionArea;

    public DefinitionPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Definition"));

        JPanel slangPanel = new JPanel(new BorderLayout());
        JLabel slangLabel = new JLabel("Slang:");
        slangField = new JTextField();
        slangField.setEditable(false);
        slangPanel.add(slangLabel, BorderLayout.WEST);
        slangPanel.add(slangField, BorderLayout.CENTER);

        JPanel defPanel = new JPanel(new BorderLayout());
        JLabel defLabel = new JLabel("Definition:");
        definitionArea = new JTextArea();
        definitionArea.setLineWrap(true);
        definitionArea.setWrapStyleWord(true);
        definitionArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(
                definitionArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        defPanel.add(defLabel, BorderLayout.NORTH);
        defPanel.add(scrollPane, BorderLayout.CENTER);

        add(slangPanel, BorderLayout.NORTH);
        add(defPanel, BorderLayout.CENTER);
    }

    public JTextField getSlangField() {
        return slangField;
    }

    public JTextArea getDefinitionArea() {
        return definitionArea;
    }
}
