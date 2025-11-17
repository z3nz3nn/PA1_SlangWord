package views.panels;

import model.SlangWord;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;

public class DefinitionPanel extends JPanel {

    private static final String CARD_SINGLE = "SINGLE";
    private static final String CARD_RESULTS = "RESULTS";

    private final CardLayout cardLayout;
    private final JPanel cardHolder;

    private JTextField slangField;
    private JTextArea definitionArea;

    // Results view components
    private JPanel resultsContainer;

    private TitledBorder titledBorder;

    public DefinitionPanel() {
        setLayout(new BorderLayout());
        titledBorder = BorderFactory.createTitledBorder("Search Result");
        setBorder(titledBorder);

        cardLayout = new CardLayout();
        cardHolder = new JPanel(cardLayout);

        JPanel singleCard = buildSingleCard();
        JPanel resultsCard = buildResultsCard();

        cardHolder.add(singleCard, CARD_SINGLE);
        cardHolder.add(resultsCard, CARD_RESULTS);

        add(cardHolder, BorderLayout.CENTER);
        showSingle();
    }

    private JPanel buildSingleCard() {
        JPanel root = new JPanel(new BorderLayout());

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

        root.add(slangPanel, BorderLayout.NORTH);
        root.add(defPanel, BorderLayout.CENTER);
        return root;
    }

    private JPanel buildResultsCard() {
        resultsContainer = new JPanel();
        resultsContainer.setLayout(new BoxLayout(resultsContainer, BoxLayout.Y_AXIS));
        resultsContainer.setBorder(new EmptyBorder(4, 4, 4, 4));

        JScrollPane scroll = new JScrollPane(resultsContainer,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(15);
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(scroll, BorderLayout.CENTER);
        return wrapper;
    }

    public JTextField getSlangField() {
        return slangField;
    }

    public JTextArea getDefinitionArea() {
        return definitionArea;
    }

    public void setTitle(String title) {
        if (titledBorder != null) {
            titledBorder.setTitle(title);
            repaint();
            revalidate();
        }
    }

    public void showSingle() {
        cardLayout.show(cardHolder, CARD_SINGLE);
    }

    public void showResults(List<SlangWord> results) {
        // Switch to results card and populate
        clearResults();
        if (results != null && !results.isEmpty()) {
            for (SlangWord sw : results) {
                resultsContainer.add(createResultCard(sw));
            }
        } else {
            JLabel empty = new JLabel("No matches");
            empty.setBorder(new EmptyBorder(4, 4, 4, 4));
            resultsContainer.add(empty);
        }
        resultsContainer.revalidate();
        resultsContainer.repaint();
        cardLayout.show(cardHolder, CARD_RESULTS);
    }

    public void clearResults() {
        resultsContainer.removeAll();
    }

    private JComponent createResultCard(SlangWord sw) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)),
                new EmptyBorder(8, 8, 8, 8)));

        // Slang label in bold
        JLabel title = new JLabel("<html><b>" + escapeHtml(sw.getSlang()) + "</b></html>");
        title.setBorder(new EmptyBorder(0, 0, 4, 0));
        card.add(title, BorderLayout.NORTH);

        // Definition text area (wrapped, read-only)
        JTextArea def = new JTextArea(sw.getMeaning());
        def.setLineWrap(true);
        def.setWrapStyleWord(true);
        def.setEditable(false);
        def.setOpaque(false);
        def.setBorder(null);

        card.add(def, BorderLayout.CENTER);
        return card;
    }

    private static String escapeHtml(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }
}
