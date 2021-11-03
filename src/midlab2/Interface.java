package midlab2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

public class Interface {
    private final int DEFAULT_COLUMNS = 50;
    private final int DEFAULT_ROWS = 5;
    private final String AUTHORS;

    // Panel that will contain the cards
    private JPanel mainCardPanel = new JPanel(new CardLayout());

    // Cards
    private JPanel textToHuffmanPanel = new JPanel();
    private JPanel huffmanToTextPanel = new JPanel();

    // Secondary panels to be stored in cards
    private JPanel titlePanel = new JPanel();
    private JPanel inputPanel = new JPanel();
    private JPanel inputTypePanel = new JPanel();
    private JPanel charPanel = new JPanel();
    private JPanel binaryPanel = new JPanel();
    private JPanel buttonPanel = new JPanel();
    private JPanel outputPanel = new JPanel();
    private JPanel authorsPanel = new JPanel();


    // Buttons
    private JButton submit = new JButton("Submit");
    private JButton clear = new JButton("Clear");

    // JCombobox for Selecting Options for Encode / Decode
    String[] options = { "Encode Text", "Decode Huffman Code" };
    private JComboBox<String> operationType = new JComboBox<>(options);

    // Text Fields
    // Scrollable pane for the Input Field
    private JTextArea userInputText = new JTextArea(DEFAULT_ROWS, DEFAULT_COLUMNS);
    private JScrollPane userInputScrollPane = new JScrollPane(userInputText);

    // CharPanel TextArea
    private JTextArea charInputText = new JTextArea(DEFAULT_ROWS, DEFAULT_COLUMNS);
    private JScrollPane charInputScrollPane = new JScrollPane(charInputText);
    // BinPanel TextArea
    private JTextArea binInputText = new JTextArea(DEFAULT_ROWS, DEFAULT_COLUMNS);
    private JScrollPane binInputScrollPane = new JScrollPane(binInputText);

    // Output Fields
    private JTextArea outputField = new JTextArea(DEFAULT_ROWS, DEFAULT_COLUMNS);
    private JScrollPane outputScrollPane = new JScrollPane(outputField);

    // Authors JTextArea
    private JTextArea authorsField = new JTextArea(DEFAULT_ROWS * 2, DEFAULT_COLUMNS);
    private JScrollPane authorsScrollPane = new JScrollPane(authorsField);

    // JComponent Arrays for adding JComponents sequentially

    private JComponent[] inputPanelComponents = { new JLabel("Input: "), userInputScrollPane };
    private JComponent[] inputTypePanelComponents = { new JLabel("Operation: "), operationType };
    private JComponent[] charInputPanelComponents = { new JLabel("Characters: "), charInputScrollPane };
    private JComponent[] binInputPanelComponents = { new JLabel("Binary Form: "), binInputScrollPane };
    private JComponent[] buttonPanelComponents = { submit, clear };
    private JComponent[] authorsPanelComponents = { new JLabel("Authors: "), authorsScrollPane };

    // JComponent Arrays for nesting JPanels within JPanels
    private JComponent[] textToHuffmanPanelComponents = { authorsPanel };
    private JComponent[] huffmanToTextPanelComponents = { charPanel, binaryPanel };

    // Accessor Methods
    public JPanel getMainCardPanel() {
        return mainCardPanel;
    }

    public JPanel getInputTypePanel() {
        return inputTypePanel;
    }

    public JPanel getTitlePanel() {
        return titlePanel;
    }

    public JPanel getInputPanel() {
        return inputPanel;
    }

    public JPanel getButtonPanel() {
        return buttonPanel;
    }

    public JPanel getOutputPanel() {
        return outputPanel;
    }

    Interface() {
        // Title Panel
        titlePanel.add(new JLabel("Text-Huffman Utility"));

        // Input Panel
        // Contains a label and a JTextField
        for (var component : inputPanelComponents) inputPanel.add(component);
        userInputScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        userInputScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Input Type Panel
        // Contains the label and the JComboBox
        for (var component : inputTypePanelComponents) inputTypePanel.add(component);
        operationType.addItemListener((ItemEvent e) -> {
            CardLayout cardLayout = (CardLayout) mainCardPanel.getLayout();
            cardLayout.show(mainCardPanel, (String) e.getItem());
        });
        operationType.setSelectedIndex(0);

        // Character Input Panel
        for (var component : charInputPanelComponents) charPanel.add(component);

        // Binary Input Panel
        for (var component : binInputPanelComponents) binaryPanel.add(component);

        // Button Panel Components
        for (var component : buttonPanelComponents) buttonPanel.add(component);

        // Authors Panel
        AUTHORS = """
                
                DATA STRUCTURES 9421
                    TEAM THROWABLE()
                            ANCHETA, CHARLES JR.
                            BUSTARDE, JEROME
                            CASTRO, ENRICO
                            GARRIDO, LUPIN
                            NUDO, KURT
                """;
        authorsField.setText(AUTHORS);
        authorsField.setEditable(false);
        for (var component : authorsPanelComponents) authorsPanel.add(component);


        // Construct the text to huffman panel
        textToHuffmanPanel.setLayout(new BoxLayout(textToHuffmanPanel, BoxLayout.Y_AXIS));
        for (var component : textToHuffmanPanelComponents) textToHuffmanPanel.add(component);
        // Construct the huffman to text panel
        huffmanToTextPanel.setLayout(new BoxLayout(huffmanToTextPanel, BoxLayout.Y_AXIS));
        for (var component : huffmanToTextPanelComponents) huffmanToTextPanel.add(component);

        // Output Panel
        outputPanel.add(new JLabel("Output: "));
        outputPanel.add(outputScrollPane);

        mainCardPanel.add(textToHuffmanPanel, options[0]);
        mainCardPanel.add(huffmanToTextPanel, options[1]);

    }

    private static void createAndShowGUI() {
        Interface gui = new Interface();
        JFrame frame = new JFrame();

        // Use the Box Layout for the Content Pane
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Add the panels
        frame.getContentPane().add(gui.getTitlePanel());
        frame.getContentPane().add(gui.getInputPanel());
        frame.getContentPane().add(gui.getInputTypePanel());
        frame.getContentPane().add(gui.getButtonPanel());
        frame.getContentPane().add(gui.getMainCardPanel());
        frame.getContentPane().add(gui.getOutputPanel());

        // Display the Window
        frame.pack();
        frame.setTitle("Text-Huffman Utility");
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {

            }
            createAndShowGUI();
        });
    }
}
