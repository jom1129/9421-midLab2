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
    List<Token<String>> tokenList;
    Tree<String> forest;
    List<String> huffman;

    // Panel that will contain the cards
    private final JPanel mainCardPanel = new JPanel(new CardLayout());

    // Cards
    private JPanel textToHuffmanPanel = new JPanel();
    private JPanel huffmanToTextPanel = new JPanel();

    // Secondary panels to be stored in cards
    private JPanel titlePanel = new JPanel();
    private JPanel inputPanel = new JPanel();
    private JPanel inputTypePanel = new JPanel();
    private JPanel buttonPanel = new JPanel();
    private JPanel outputPanel = new JPanel();

    private JPanel huffmanToTextTableValPanel = new JPanel();
    private JPanel huffmanGetCodePanel = new JPanel();
    private JPanel huffmanToTextOperationsPanel = new JPanel();
    private JPanel huffmanOutputPanel = new JPanel();
    private JPanel outputHuffmanCodePanel = new JPanel();
    private JPanel inputTextToHuffmanFieldPanel = new JPanel();
    private JPanel inputTextToHuffmanFieldPanelButtons = new JPanel();

    // Buttons
    private JButton submit = new JButton("Submit Cipher");
    private JButton clear = new JButton("Clear");
    private JButton submitHuffmanToText = new JButton("Submit Huffman Code");
    private JButton submitTextToHuffman = new JButton("Submit Text");
    private JButton percentSavings = new JButton("Show Percent Savings");

    // JCombobox for Selecting Options for Encode / Decode
    String[] options = { "Encode Text", "Decode Huffman Code" };
    private JComboBox<String> operationType = new JComboBox<>(options);

    // Text Fields
    // Scrollable pane for the Input Field
    private JTextArea userInputText = new JTextArea(DEFAULT_ROWS, DEFAULT_COLUMNS);
    private JScrollPane userInputScrollPane = new JScrollPane(userInputText);

    // Output Fields
    private JTextArea outputField = new JTextArea(DEFAULT_ROWS, DEFAULT_COLUMNS);
    private JScrollPane outputScrollPane = new JScrollPane(outputField);

    private JTextArea huffmanToTextTableArea = new JTextArea(DEFAULT_ROWS, DEFAULT_COLUMNS);
    private JScrollPane huffmanToTextTablePane = new JScrollPane(huffmanToTextTableArea);

    private JTextArea huffmanGetInputField = new JTextArea(DEFAULT_ROWS, DEFAULT_COLUMNS);
    private JScrollPane huffmanGetInputFieldPane = new JScrollPane(huffmanGetInputField);

    private JTextArea huffmanToTextOutputField = new JTextArea(DEFAULT_ROWS, DEFAULT_COLUMNS);
    private JScrollPane huffmanToTextOutputPane = new JScrollPane(huffmanToTextOutputField);

    private JTextArea inputTextToHuffmanField = new JTextArea(DEFAULT_ROWS, DEFAULT_COLUMNS);
    private JScrollPane inputTextToHuffmanFieldPane = new JScrollPane(inputTextToHuffmanField);

    private JTextArea outputHuffmanCodeArea = new JTextArea(DEFAULT_ROWS, DEFAULT_COLUMNS);
    private JScrollPane outputHuffmanCodePane = new JScrollPane(outputHuffmanCodeArea);


    // JComponent Arrays for adding JComponents sequentially

    private JComponent[] inputPanelComponents = { new JLabel("Input Cipher: "), userInputScrollPane };
    private JComponent[] inputTypePanelComponents = { new JLabel("Operation: "), operationType };
    private JComponent[] buttonPanelComponents = { submit, clear, percentSavings };
    private JComponent[] inputTextToHuffmanFieldPaneComponents = { new JLabel("Input Text: "),
            inputTextToHuffmanFieldPane };
    private JComponent[] inputTextToHuffmanFieldPanelButtonsComponents = { submitTextToHuffman };

    // JComponent Arrays for nesting JPanels within JPanels
    // MASTER JPANELS
    private JComponent[] textToHuffmanPanelComponents = { outputPanel, inputTextToHuffmanFieldPanel,
            inputTextToHuffmanFieldPanelButtons, outputHuffmanCodePanel };
    private JComponent[] huffmanToTextPanelComponents = { huffmanToTextTableValPanel, huffmanGetCodePanel,
            huffmanToTextOperationsPanel, huffmanOutputPanel};

    // NEW JCOMPONENT ARRAYS
    private JComponent[] huffmanToTextTableValComponents = { new JLabel("Table Values: "), huffmanToTextTablePane };
    private JComponent[] huffmanGetCodePanelComponents = { new JLabel("Huffman Code: "), huffmanGetInputFieldPane };
    private JComponent[] huffmanToTextOperationsPanelComponents = { submitHuffmanToText };
    private JComponent[] huffmanOutputPanelComponents = { new JLabel("Output: "), huffmanToTextOutputPane };
    private JComponent[] outputHuffmanCodePanelComponents = { new JLabel("Huffman Code: "), outputHuffmanCodePane };

    // Set every scrollpane as Scrollable
    private JScrollPane[] scrollables = { inputTextToHuffmanFieldPane, outputHuffmanCodePane,
    inputTextToHuffmanFieldPane, outputHuffmanCodePane, userInputScrollPane,
    huffmanGetInputFieldPane, outputScrollPane, huffmanToTextTablePane };

    // JComponent Arrays for clearing TextFields
    private JTextArea[] clearableTextFields = { userInputText, outputField, huffmanToTextTableArea, huffmanGetInputField,
    huffmanToTextOutputField, outputHuffmanCodeArea, huffmanToTextTableArea, huffmanToTextOutputField };

    // JComponent Arrays for locking
    private JTextArea[] lockFields = { outputHuffmanCodeArea, huffmanToTextTableArea, huffmanToTextOutputField };

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

        // Primary Huffman Code Output panel
        for (var component : outputHuffmanCodePanelComponents) outputHuffmanCodePanel.add(component);

        // Buttons
        for (var component : inputTextToHuffmanFieldPanelButtonsComponents)
            inputTextToHuffmanFieldPanelButtons.add(component);

        // Secondary Text Input, after Cipher
        for (var component : inputTextToHuffmanFieldPaneComponents) inputTextToHuffmanFieldPanel.add(component);

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
                    tokenList = utility.determineFrequency(userInputText.getText());
                    forest = utility.forestBuilder(tokenList);
                    utility.setHuffmanCode(forest, tokenList);
                    utility.showHuffmanTable(tokenList);
                    huffmanToTextTableArea.setDocument(outputField.getDocument());
                    // huffmanToTextOutputField.setDocument(outputField.getDocument());
                }
            } catch (ArgumentMismatchException | IllegalArgumentException |
                    NullPointerException | StringIndexOutOfBoundsException arg) {
                JOptionPane.showMessageDialog(null, "Invalid Input.");
            }
        });

        clear.addActionListener((ActionEvent e) -> {
            for (var text : clearableTextFields) text.setText("");
        });

        percentSavings.addActionListener((ActionEvent e) -> {
            try {
                JOptionPane.showMessageDialog(null, utility.computePercentageSavings(tokenList));
            } catch (ArgumentMismatchException | IllegalArgumentException |
                    NullPointerException | StringIndexOutOfBoundsException arg) {
                JOptionPane.showMessageDialog(null, "Invalid Input.");
            }
        });

        submitHuffmanToText.addActionListener((ActionEvent e) -> {
            try {
                huffman = utility.huffmanToText(huffmanGetInputField.getText(), tokenList);
                huffmanToTextOutputField.setText(utility.showHuffmanToTextOutput(huffman));
            } catch (IllegalArgumentException |
                    NullPointerException | StringIndexOutOfBoundsException arg) {
                JOptionPane.showMessageDialog(null, "Invalid Input.");
            } catch (ArgumentMismatchException exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage());
            }
        });

        submitTextToHuffman.addActionListener((ActionEvent e) -> {
            StringBuilder temp = new StringBuilder();
            try {
                huffman = utility.textToHuffman(inputTextToHuffmanField.getText(), tokenList);

                for(var x : huffman) {
                    temp.append(x);
                }

                outputHuffmanCodeArea.setText(temp.toString());
            } catch (IllegalArgumentException | NullPointerException | StringIndexOutOfBoundsException arg) {
                JOptionPane.showMessageDialog(null, "Invalid Input.");
            } catch (ArgumentMismatchException exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage());
            }
        });

        for (var component : scrollables) {
            component.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            component.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        }

        // Construct the text to huffman panel
        textToHuffmanPanel.setLayout(new BoxLayout(textToHuffmanPanel, BoxLayout.Y_AXIS));
        for (var component : textToHuffmanPanelComponents) textToHuffmanPanel.add(component);
        // Construct the huffman to text panel
        huffmanToTextPanel.setLayout(new BoxLayout(huffmanToTextPanel, BoxLayout.Y_AXIS));
        for (var component : huffmanToTextPanelComponents) huffmanToTextPanel.add(component);

        // Output Panel
        outputPanel.add(new JLabel("Table Values: "));
        outputPanel.add(outputScrollPane);
        outputField.setEditable(false);

        for (var component : lockFields) component.setEditable(false);

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
