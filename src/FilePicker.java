import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Path;

/**
 * Simple component that combines a JButton, JFileChooser, JTextField, and JLabel to create a standard-looking
 * Filepicker component. Currently used exclusively for loading files without an extension filter,
 * but could easily have methods created to add that functionality.
 */

public class FilePicker extends JPanel{
    private String textFieldLabel;
    private String buttonLabel;
    private boolean isFileSelected;
    private Path selectedFilePath;

    private Path filePath;
    private JLabel label;
    private JTextField textField;
    private JButton button;

    private JFileChooser fileChooser;

    public FilePicker(String textFieldLabel, String buttonLabel) {
        this.textFieldLabel = textFieldLabel;
        this.buttonLabel = buttonLabel;
        isFileSelected = false;

        fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        label = new JLabel(textFieldLabel);
        textField = new JTextField(30);
        button = new JButton(buttonLabel);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonActionPerformed(e);
            }
        });

        add(label);
        add(textField);
        add(button);
    }

    private void buttonActionPerformed(ActionEvent e) {
        //We'll assume the component will only be used for opening files for now, not saving
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            textField.setText(fileChooser.getSelectedFile().getAbsolutePath());
            selectedFilePath = fileChooser.getSelectedFile().toPath();
            isFileSelected = true;
        }
    }

    public void reset() {
        textField.setText("");
        isFileSelected = false;
        Path selectedFilePath = null;
    }

    public String getTextFieldLabel() {
        return this.textFieldLabel;
    }

    public void setTextFieldLabel(String textFieldLabel) {
        this.textFieldLabel = textFieldLabel;
    }

    public String getTextField() {
        return textField.getText();
    }

    public String getButtonLabel() {
        return this.buttonLabel;
    }

    public void setButtonLabel(String buttonLabel) {
        this.buttonLabel = buttonLabel;
    }

    public boolean getIsFileSelected() {
        return isFileSelected;
    }

    public Path getSelectedFilePath() {
        return selectedFilePath;
    }

    public void setSelectedFilePath(Path selectedFilePath) {
        this.selectedFilePath = selectedFilePath;
        this.textField.setText(selectedFilePath.toString());
        this.isFileSelected = true;
    }

    public String getSelectedFilePathString() {
        if (isFileSelected) {
            return textField.getText();
        }
        return null;
    }

    public JFileChooser getFileChooser() {
        return fileChooser;
    }


}
