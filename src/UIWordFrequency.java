import javax.swing.*;
import java.awt.*;
import java.util.*;

public class UIWordFrequency extends JFrame {

    private WordFrequency wordFrequenciesObj;
    private Map<String, Integer> unsortedFrequencies;

    private JPanel topPanel;
    private JPanel bottomPanel;
    private JPanel buttonSubPanel;

    private JLabel headingLabel;

    private FilePicker scanFilePicker;
    private FilePicker stopWordsFilePicker;

    private JTextArea textArea;
    private JScrollPane scrollPane;
    private JButton submitButton;
    private JButton resetButton;

    public UIWordFrequency() {

        wordFrequenciesObj = new WordFrequency();

        //GUI CODE//
        setTitle("Word Frequency Counter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(5, 5));

        topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        bottomPanel = new JPanel();
        buttonSubPanel = new JPanel();

        headingLabel = new JLabel("Word Frequency Counter");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headingLabel.setBorder(BorderFactory.createEmptyBorder(20, 5, 10, 5));
        topPanel.add(headingLabel);

        scanFilePicker = new FilePicker("Text File to Scan: ", "Browse...");
        stopWordsFilePicker = new FilePicker(" Excluding Words:", "Browse...");
        topPanel.add(scanFilePicker);
        topPanel.add(stopWordsFilePicker);

        textArea = new JTextArea(18, 40);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 18));
        textArea.setMargin(new Insets(10, 30, 10, 10));
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        scrollPane = new JScrollPane(textArea);
        bottomPanel.add(scrollPane);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 20, 0));

        buttonSubPanel.setLayout(new BoxLayout(buttonSubPanel, BoxLayout.LINE_AXIS));
        buttonSubPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {submitButtonClicked();
        });

        resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> {resetButtonClicked();
        });

        submitButton.setHorizontalAlignment(SwingConstants.CENTER);
        resetButton.setHorizontalAlignment(SwingConstants.CENTER);

        buttonSubPanel.add(Box.createHorizontalGlue());
        buttonSubPanel.add(resetButton);
        buttonSubPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonSubPanel.add(submitButton);
        buttonSubPanel.add(Box.createHorizontalGlue());

        topPanel.add(buttonSubPanel);

        add(topPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }

    private void submitButtonClicked() {
        //check that the files were selected
        if (scanFilePicker.getIsFileSelected() == true && stopWordsFilePicker.getIsFileSelected() == true) {
            unsortedFrequencies = wordFrequenciesObj.getWordFrequencies(
                    scanFilePicker.getSelectedFilePath(), stopWordsFilePicker.getSelectedFilePath());

            Map<String, Integer> sortedFrequencies = wordFrequenciesObj.sortByValueDesc(unsortedFrequencies);
            for (Map.Entry<String, Integer> entry : sortedFrequencies.entrySet()) {
                textArea.append(entry.getKey() + ": " + entry.getValue() + "\n");
            }
            textArea.setCaretPosition(0);
        }
        else {
            System.out.println("Please select both files.");
        }
    }

    private void resetButtonClicked() {
        unsortedFrequencies.clear();
        textArea.setText("");
        scanFilePicker.reset();
        stopWordsFilePicker.reset();
    }
}
