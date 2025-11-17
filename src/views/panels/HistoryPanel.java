package views.panels;

import javax.swing.*;
import java.awt.*;

public class HistoryPanel extends JPanel {

    public JTextArea taHistory;
    public JButton btnClear;

    public HistoryPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Search History"));

        taHistory = new JTextArea();
        taHistory.setEditable(false);

        add(new JScrollPane(taHistory), BorderLayout.CENTER);
    }
}
