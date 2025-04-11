import javax.swing.*;
import java.io.*;
import java.nio.file.Path;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        JFileChooser chooser = new JFileChooser();
        WordFrequency counter = new WordFrequency();

            File workingDirectory = new File(System.getProperty("user.dir") + "\\src\\resources");
            chooser.setCurrentDirectory(workingDirectory);
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                File selectedStopFile = chooser.getSelectedFile();
                Path stopFilePath = selectedStopFile.toPath();

                    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                        File selectedScanFile = chooser.getSelectedFile();
                        Path scanFilePath = selectedScanFile.toPath();

                        Map<String, Integer> test = counter.getWordFrequencies(scanFilePath, stopFilePath);
                        for (Map.Entry<String, Integer> entry : test.entrySet()) {
                            String word = entry.getKey();
                            int freq = entry.getValue();
                            System.out.println(word + ": " + freq);
                        }

                        //now we sort the entries by count descending. This requires a converting into a list
                        //List<Map.Entry<String, Integer>> list = new ArrayList<>(test.entrySet());
                        //list.sort(Map.Entry.comparingByValue());
                        //list.forEach(entry -> System.out.println(entry.getKey() + " : " + entry.getValue()));

                    }



            }

    }
}