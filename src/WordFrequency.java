import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.nio.file.Path;
import java.util.stream.Collectors;

/**
 * A class that contains a method for counting the word frequency of a given file,
 * excluding the stop words provided in another file. Intended to be paired with a GUI,
 * or otherwise used as a function in another program.
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
    public Map<String, Integer> getWordFrequencies(Path filePath, Path stopWordsFilePath) {
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

    private HashSet<String> loadStopWords(Path path) {
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

    public Map<String, Integer> sortByValueDesc(Map<String, Integer> unsortedMap) {

        Map<String, Integer> sortedMap = unsortedMap.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));

        return sortedMap;
    }

    public Map<String, Integer> sortByValueAsc(Map<String, Integer> unsortedMap) {

        Map<String, Integer> sortedMap = unsortedMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));

        return sortedMap;
    }
}
