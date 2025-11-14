package views.panels;

import javax.swing.*;
import java.awt.*;

public class DeletePanel extends JPanel {

    public JTextField tfSlang;
    public JButton btnDelete;

    public DeletePanel() {
        setLayout(new GridLayout(2, 2, 10, 10));

        add(new JLabel("Slang to delete:"));
        tfSlang = new JTextField();
        add(tfSlang);

        btnDelete = new JButton("Delete");
        add(new JLabel());
        add(btnDelete);
    }
}
