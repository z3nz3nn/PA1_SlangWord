package views.panels;

import javax.swing.*;
import java.awt.*;

public class EditPanel extends JPanel {

    public JTextField tfSlang;
    public JTextField tfNewDefinition;
    public JButton btnEdit;

    public EditPanel() {
        setLayout(new GridLayout(3, 2, 10, 10));

        add(new JLabel("Slang to edit:"));
        tfSlang = new JTextField();
        add(tfSlang);

        add(new JLabel("New Definition:"));
        tfNewDefinition = new JTextField();
        add(tfNewDefinition);

        add(new JLabel());
    }
}
