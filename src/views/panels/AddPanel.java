package views.panels;

import javax.swing.*;
import java.awt.*;

public class AddPanel extends JPanel {

    public JTextField tfSlang;
    public JTextField tfDefinition;

    public AddPanel() {
        setLayout(new GridLayout(2, 2, 10, 10));

        add(new JLabel("Slang:"));
        tfSlang = new JTextField();
        add(tfSlang);

        add(new JLabel("Definition:"));
        tfDefinition = new JTextField();
        add(tfDefinition);
    }
}
