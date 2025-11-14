package views.panels;

import javax.swing.*;
import java.awt.*;

public class RandomPanel extends JPanel {

    public JTextArea taResult;
    public JButton btnRandom;

    public RandomPanel() {
        setLayout(new BorderLayout());

        btnRandom = new JButton("Random Slang");
        taResult = new JTextArea();
        taResult.setEditable(false);

        add(btnRandom, BorderLayout.NORTH);
        add(new JScrollPane(taResult), BorderLayout.CENTER);
    }
}
