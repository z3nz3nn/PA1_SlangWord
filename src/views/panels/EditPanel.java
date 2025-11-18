package views.panels;

import javax.swing.*;
import java.awt.*;

public class EditPanel extends JPanel {

    public JTextField tfSlang;
    public JTextField tfNewDefinition;

    public EditPanel() {
        setLayout(new GridLayout(2, 2, 10, 10));

        add(new JLabel("Slang to edit:"));
        tfSlang = new JTextField();
        add(tfSlang);

        add(new JLabel("New Definition:"));
        tfNewDefinition = new JTextField();
        add(tfNewDefinition);
    }
}
