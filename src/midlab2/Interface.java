package midlab2;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.List;

public class Interface {
    private final int DEFAULT_COLUMNS = 80;
    private final int DEFAULT_ROWS = 10;
    private final String AUTHORS;
    List<Token<String>> tokenList;
    Tree<String> forest;

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

    private JPanel huffmanToTextTableValPanel = new JPanel();
    private JPanel huffmanGetCodePanel = new JPanel();
    private JPanel huffmanToTextOperationsPanel = new JPanel();
    private JPanel huffmanOutputPanel = new JPanel();



    // Buttons
    private JButton submit = new JButton("Submit");
    private JButton clear = new JButton("Clear");
    private JButton submitHuffmanToText = new JButton("Submit");

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

    private JTextArea huffmanToTextTableArea = new JTextArea(DEFAULT_ROWS, DEFAULT_COLUMNS);
    private JScrollPane huffmanToTextTablePane = new JScrollPane(huffmanToTextTableArea);

    private JTextArea huffmanGetInputField = new JTextArea(DEFAULT_ROWS, DEFAULT_COLUMNS);
    private JScrollPane huffmanGetInputFieldPane = new JScrollPane(huffmanGetInputField);

    private JTextArea huffmanToTextOutputField = new JTextArea(DEFAULT_ROWS, DEFAULT_COLUMNS);
    private JScrollPane huffmanToTextOutputPane = new JScrollPane(huffmanToTextOutputField);


    // Authors JTextArea
    private JTextArea authorsField = new JTextArea(15, DEFAULT_COLUMNS);
    private JScrollPane authorsScrollPane = new JScrollPane(authorsField);

    // JComponent Arrays for adding JComponents sequentially

    private JComponent[] inputPanelComponents = { new JLabel("Input: "), userInputScrollPane };
    private JComponent[] inputTypePanelComponents = { new JLabel("Operation: "), operationType };
    private JComponent[] charInputPanelComponents = { new JLabel("Characters: "), charInputScrollPane };
    private JComponent[] binInputPanelComponents = { new JLabel("Number of Bits: "), binInputScrollPane };
    private JComponent[] buttonPanelComponents = { submit, clear };
    private JComponent[] authorsPanelComponents = { new JLabel("Authors: "), authorsScrollPane };

    // JComponent Arrays for nesting JPanels within JPanels
    private JComponent[] textToHuffmanPanelComponents = { authorsPanel, outputPanel };
    private JComponent[] huffmanToTextPanelComponents = { huffmanToTextTableValPanel, huffmanGetCodePanel,
                        huffmanToTextOperationsPanel, huffmanOutputPanel};

    // NEW JCOMPONENT ARRAYS
    private JComponent[] huffmanToTextTableValComponents = { new JLabel("Table Values: "), huffmanToTextTablePane };
    private JComponent[] huffmanGetCodePanelComponents = { new JLabel("Huffman Code: "), huffmanGetInputFieldPane };
    private JComponent[] huffmanToTextOperationsPanelComponents = { submitHuffmanToText };
    private JComponent[] huffmanOutputPanelComponents = { new JLabel("Output: "), huffmanToTextOutputPane };


    // JComponent Arrays for clearing TextFields
    private JTextArea[] clearableTextFields = { userInputText, charInputText, binInputText, outputField };

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
        Utility utility = new Utility();
        redirectSystemStreams();

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
            // for(var text : clearableTextFields)text.setText("");
            cardLayout.show(mainCardPanel, (String) e.getItem());
        });
        operationType.setSelectedIndex(0);

        // Character Input Panel
        for (var component : charInputPanelComponents) charPanel.add(component);

        // Binary Input Panel
        for (var component : binInputPanelComponents) binaryPanel.add(component);

        // Huffman Table Text
        for (var component : huffmanToTextTableValComponents) huffmanToTextTableValPanel.add(component);

        // Huffman get expression
        for (var component : huffmanGetCodePanelComponents) huffmanGetCodePanel.add(component);

        // Huffman buttons
        for (var component : huffmanToTextOperationsPanelComponents) huffmanToTextOperationsPanel.add(component);

        // Huffman Output
        for (var component : huffmanOutputPanelComponents) huffmanOutputPanel.add(component);

        // Button Panel Components
        for (var component : buttonPanelComponents) buttonPanel.add(component);
        submit.addActionListener((ActionEvent e) -> {
            try {
                if (operationType.getSelectedIndex() == 0) {
                    tokenList = utility.determineFrequency(userInputText.getText());
                    forest = utility.forestBuilder(tokenList);
                    utility.setHuffmanCode(forest, tokenList);
                    utility.showHuffmanTable(tokenList);
                    huffmanToTextTableArea.setDocument(outputField.getDocument());
                } else {
                    // tokenList = utility.parseTokenList(charInputText.getText(), binInputText.getText());
                    utility.huffmanToText(userInputText.getText(), tokenList);
                    huffmanToTextOutputField.setDocument(outputField.getDocument());
                }
            } catch (ArgumentMismatchException arg) {
                JOptionPane.showMessageDialog(null,
                        arg.getMessage());
            } catch (InvalidInputException inputException) {
                JOptionPane.showMessageDialog(null,
                        inputException.getMessage());
            } catch (NumberFormatException | NullPointerException num) {
                JOptionPane.showMessageDialog(null,
                        num.getMessage());
            }
        });

        clear.addActionListener((ActionEvent e) -> {
            for (var text : clearableTextFields) text.setText("");
        });

        submitHuffmanToText.addActionListener((ActionEvent e) -> {
            outputField.setText("");
            utility.huffmanToText(huffmanGetInputField.getText(),tokenList);
            huffmanToTextOutputField.setText(outputField.getText());
        });


        // Authors Panel
        AUTHORS = """
                
                DATA STRUCTURES 9421
                    TEAM THROWABLE()
                            ANCHETA, CHARLES JR.
                            BUSTARDE, JEROME
                            CASTRO, ENRICO
                            GARRIDO, LUPIN
                            NUDO, KURT
                
                PROGRAM INFORMATION
                    ENCODE TEXT: Converts Regular Text to a Huffman Expression
                    DECODE: Decodes a Huffman Expression given a Cipher            
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
        outputField.setEditable(false);

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
        // frame.getContentPane().add(gui.getOutputPanel());

        // Display the Window
        frame.pack();
        frame.setResizable(false);
        frame.setTitle("Text-Huffman Utility");
        frame.setVisible(true);
    }


    /**
     * Helper method for redirectSystemStreams()
     * @param text
     */
    private void updateTextPane(final String text) {
        SwingUtilities.invokeLater(() -> {
            Document doc = outputField.getDocument();
            try {
                doc.insertString(doc.getLength(), text, null);
            } catch (BadLocationException e) {

            }
            outputField.setCaretPosition(doc.getLength() - 1);
        });
    }

    /**
     * Redirects CLI output to the JTextArea tableText
     */
    private void redirectSystemStreams() {
        OutputStream out = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                updateTextPane(String.valueOf((char) b));
            }

            @Override
            public void write(byte[] b, int off, int len) throws IOException {
                updateTextPane(new String(b, off, len));
            }

            @Override
            public void write(byte[] b) throws IOException {
                write(b, 0, b.length);
            }
        };
        System.setOut(new PrintStream(out, true));
        // System.setErr(new PrintStream(out, true));
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
