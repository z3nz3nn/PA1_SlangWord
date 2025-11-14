package views.panels;

import javax.swing.*;
import java.awt.*;

public class HistoryPanel extends JPanel {

    public JTextArea taHistory;
    public JButton btnClear;

    public HistoryPanel() {
        setLayout(new BorderLayout());

        taHistory = new JTextArea();
        taHistory.setEditable(false);

        btnClear = new JButton("Clear History");

        add(new JScrollPane(taHistory), BorderLayout.CENTER);
        add(btnClear, BorderLayout.SOUTH);
    }
}
