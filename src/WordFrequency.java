import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.nio.file.Path;
import java.util.stream.Collectors;

/**
 * A collection of static methods for counting word frequency of a given file,
 * excluding stop words provided in another file. Intended to be paired with a GUI,
 * or otherwise used as a callable static functions for other programs.
 * Storing maps / dictionaries of large text files can eat a lot of RAM, so creating maps on
 * demand from static methods makes sense.
 */

public class WordFrequency {

    /**
     * Finds word frequencies of given text file, excluding words in stop file
     * Stop words file should be one word per line
     * Words will only be from the Latin alphabet, other languages or alternative characters will be excluded
     * @param filePath Path of text file to scan
     * @param stopWordsFilePath Path of stop words to exclude
     * @return Map of Word Frequency
     * @throws IOException
     */
    public static Map<String, Integer> getWordFrequencies(Path filePath, Path stopWordsFilePath) {
        Map<String, Integer> wordFrequencies = new HashMap<>();
        HashSet<String> stopWords = loadStopWords(stopWordsFilePath);

        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.toLowerCase().split("\\s+");

                for (String word : words) {
                    word = word.replaceAll("[^a-zA-Z]", "");
                    if (!word.isEmpty() && !stopWords.contains(word)) {
                        wordFrequencies.put(word, wordFrequencies.getOrDefault(word, 0) + 1);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            e.printStackTrace();
        }
        return wordFrequencies;
    }

    private static HashSet<String> loadStopWords(Path path) {
        HashSet<String> stopWords = new HashSet<>();
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
                stopWords.add(line.trim().toLowerCase());
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            e.printStackTrace();
        }
        return stopWords;
    }

    public static Map<String, Integer> sortByValueDesc(Map<String, Integer> unsortedMap) {

        Map<String, Integer> sortedMap = unsortedMap.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));

        return sortedMap;
    }

    public static Map<String, Integer> sortByValueAsc(Map<String, Integer> unsortedMap) {

        Map<String, Integer> sortedMap = unsortedMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));

        return sortedMap;
    }

    /**
     * Saves csv formatted text file of the Map of word Frequencies at the given Path.
     * @param filePath Path object where the file will be saved
     * @param wordFrequencies Map object that will be written to a csv text file
     */
    public static void saveWordFrequencies(Path filePath, Map<String, Integer> wordFrequencies) {
        if (filePath == null) {
            return;
        }

        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            for (Map.Entry<String, Integer> entry : wordFrequencies.entrySet()) {
                String line = String.format("%s,%s", entry.getKey(), entry.getValue());
                writer.write(line);
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * Alternative method that uses JFileChooser to select save destination
     * if a path isn't specified
     * @param wordFrequencies Map of word frequencies
     */
    public static void saveWordFrequencies(Map<String, Integer> wordFrequencies) {

        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

        int result = chooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {

            Path savePath = chooser.getSelectedFile().toPath();
            try (BufferedWriter writer = Files.newBufferedWriter(savePath)) {
                for (Map.Entry<String, Integer> entry : wordFrequencies.entrySet()) {
                    String line = String.format("%s,%s", entry.getKey(), entry.getValue());
                    writer.write(line);
                    writer.newLine();
                }

            } catch (IOException e) {
                System.out.println("Error writing file: " + e.getMessage());
                e.printStackTrace();
            }

        }
    }
}
