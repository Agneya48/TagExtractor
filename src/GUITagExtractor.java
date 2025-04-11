import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Path;
import java.util.*;

/**
 * GUI for lab 08 tag extractor for the University of Cincinnnati. Calls methods and components from
 * two custom Classes which handle most of the computation, but much of the top-level program logic
 * is still located in this class.
 *
 * Very much  unoptimized; can eat 300+ MB of RAM when reading large books. Could be heavily mitigated
 * by making exclusive use of static methods to generate and read/write the memory-hungry maps on demand,
 * but didn't reach the point where I thought a refactor was truly necessary.
 *
 * @author Josh Hampton hamptojt@mail.uc.edu
 */

public class GUITagExtractor extends JFrame {

    private WordFrequency wordFrequenciesObj;
    private Map<String, Integer> unsortedFrequencies;
    private Map<String, Integer> sortedFrequencies;

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
    private JButton saveButton;

    private JFileChooser saveFileChooser;

    public GUITagExtractor() {

        wordFrequenciesObj = new WordFrequency();
        saveFileChooser= new JFileChooser();
        saveFileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

        //GUI CODE//
        setTitle("Word Frequency Counter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(5, 5));

        topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        bottomPanel = new JPanel();
        buttonSubPanel = new JPanel();

        headingLabel = new JLabel("Tag Extractor");
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
        submitButton.addActionListener(e -> {
            submitButtonClicked();
        });

        saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            saveButtonClicked();
        });

        resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> {
            resetButtonClicked();
        });

        submitButton.setHorizontalAlignment(SwingConstants.CENTER);
        resetButton.setHorizontalAlignment(SwingConstants.CENTER);

        buttonSubPanel.add(Box.createHorizontalGlue());
        buttonSubPanel.add(resetButton);
        buttonSubPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonSubPanel.add(saveButton);
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

            sortedFrequencies = wordFrequenciesObj.sortByValueDesc(unsortedFrequencies);
            for (Map.Entry<String, Integer> entry : sortedFrequencies.entrySet()) {
                textArea.append(entry.getKey() + ": " + entry.getValue() + "\n");
            }
            textArea.setCaretPosition(0);
        } else {
            warningWindow("Please select both files");
        }
    }

    private void resetButtonClicked() {
        if (unsortedFrequencies != null) {
            unsortedFrequencies.clear();
        }
        if (sortedFrequencies != null) {
            sortedFrequencies.clear();
        }
        textArea.setText("");
        scanFilePicker.reset();
        stopWordsFilePicker.reset();
    }

    private void warningWindow(String message) {
        JOptionPane.showMessageDialog(null, message, "", JOptionPane.WARNING_MESSAGE);
    }

    private void saveButtonClicked() {

        saveFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        saveFileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        int result = saveFileChooser.showSaveDialog(null);
        if (result == saveFileChooser.APPROVE_OPTION) {
            Path savePath = saveFileChooser.getSelectedFile().toPath();

            if (sortedFrequencies != null) {
                WordFrequency.saveWordFrequencies(savePath, sortedFrequencies);
            }
            else{
                System.out.println("File destination not selected");
            }
        }
    }
}