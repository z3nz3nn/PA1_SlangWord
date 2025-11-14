package views.panels;

import javax.swing.*;
import java.awt.*;

public class QuizSlangPanel extends JPanel {

    public JLabel lbSlang;
    public JRadioButton[] options;
    public JButton btnSubmit;
    public ButtonGroup group;

    public QuizSlangPanel() {
        setLayout(new BorderLayout());

        lbSlang = new JLabel("Slang word: ???");
        lbSlang.setFont(new Font("Arial", Font.BOLD, 18));

        JPanel center = new JPanel(new GridLayout(4, 1));
        options = new JRadioButton[4];
        group = new ButtonGroup();

        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton("Option " + (i + 1));
            group.add(options[i]);
            center.add(options[i]);
        }

        btnSubmit = new JButton("Submit");

        add(lbSlang, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        add(btnSubmit, BorderLayout.SOUTH);
    }
}
