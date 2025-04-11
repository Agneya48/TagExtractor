import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class WordFrequencyTest {

    Path stopWordsPath, christmasCarolPath, draculaPath, mobyDickPath;
    WordFrequency test1, test2, test3;
    Map<String, Integer> map1, map2, map3;
    Map<String, Integer> sort1, sort2, sort3;

    @BeforeEach
    void setUp() {
        stopWordsPath = Paths.get("test", "testResources", "English Stop Words.txt");
        christmasCarolPath = Paths.get("test", "testResources", "ChristmasCarol.txt");
        draculaPath = Paths.get("test", "testResources", "Dracula.txt");
        mobyDickPath = Paths.get("test", "testResources", "MobyDick.txt");

        test1 = new WordFrequency();
        test2 = new WordFrequency();
        test3 = new WordFrequency();
    }

    @Test
    void getWordFrequencies() {
        map1 = test1.getWordFrequencies(christmasCarolPath, stopWordsPath);
        map2 = test2.getWordFrequencies(draculaPath, stopWordsPath);
        map3 = test3.getWordFrequencies(mobyDickPath, stopWordsPath);

        //for easier testing, just test the length and the existence of some keys
        assertFalse(map1.isEmpty());
        assertEquals(map1.size(), 4474);
        assertTrue(map1.containsKey("scrooge"));

        assertFalse(map2.isEmpty());
        assertEquals(map2.size(), 10340);
        assertTrue(map2.containsKey("helsing"));

        assertFalse(map3.isEmpty());
        assertEquals(map3.size(), 19584);
        assertTrue(map3.containsKey("whale"));

    }

    @Test
    void sortByValueDesc() {
        map1 = test1.getWordFrequencies(christmasCarolPath, stopWordsPath);
        map2 = test2.getWordFrequencies(draculaPath, stopWordsPath);
        map3 = test3.getWordFrequencies(mobyDickPath, stopWordsPath);

        sort1 = test1.sortByValueDesc(map1);
        sort2 = test2.sortByValueDesc(map2);
        sort3 = test3.sortByValueDesc(map3);

        assertFalse(sort1.isEmpty());
        assertFalse(sort2.isEmpty());
        assertFalse(sort3.isEmpty());

        assertTrue(sort1.containsKey("scrooge"));
        assertTrue(sort2.containsKey("helsing"));
        assertTrue(sort3.containsKey("whale"));

        //check if the first 10 highest values are as expected
        List<Integer> actualValues = new ArrayList<>();
        List<Integer> expectedValues = Arrays.asList(314, 96, 94, 88, 87, 84, 69, 49, 48, 34);
        for (Map.Entry<String, Integer> entry : sort1.entrySet()) {
            actualValues.add(entry.getValue());
        }
        for (int i = 0; i < expectedValues.size(); i++) {
            assertEquals(expectedValues.get(i), actualValues.get(i));
        }

        //now repeat for other two tests
        expectedValues = Arrays.asList(300, 223, 220, 206, 191, 188, 187, 187, 182);
        actualValues.clear();
        for (Map.Entry<String, Integer> entry : sort2.entrySet()) {
            actualValues.add(entry.getValue());
        }
        for (int i = 0; i < expectedValues.size(); i++) {
            assertEquals(expectedValues.get(i), actualValues.get(i));
        }

        expectedValues = Arrays.asList(966, 442, 421, 397, 384, 379, 294, 249, 246);
        actualValues.clear();
        for (Map.Entry<String, Integer> entry : sort3.entrySet()) {
            actualValues.add(entry.getValue());
        }
        for (int i = 0; i < expectedValues.size(); i++) {
            assertEquals(expectedValues.get(i), actualValues.get(i));
        }

    }

    @Test
    void sortByValueAsc() {

        //test is virtually identical to DESC, with a minor adjustment
        //so the last 10 values are being tested instead

        map1 = test1.getWordFrequencies(christmasCarolPath, stopWordsPath);
        map2 = test2.getWordFrequencies(draculaPath, stopWordsPath);
        map3 = test3.getWordFrequencies(mobyDickPath, stopWordsPath);

        sort1 = test1.sortByValueAsc(map1);
        sort2 = test2.sortByValueAsc(map2);
        sort3 = test3.sortByValueAsc(map3);

        assertFalse(sort1.isEmpty());
        assertFalse(sort2.isEmpty());
        assertFalse(sort3.isEmpty());

        assertTrue(sort1.containsKey("scrooge"));
        assertTrue(sort2.containsKey("helsing"));
        assertTrue(sort3.containsKey("whale"));

        List<Integer> actualValues = new ArrayList<>();
        List<Integer> lastTenActualValues = new ArrayList<>();
        List<Integer> expectedValues = Arrays.asList(314, 96, 94, 88, 87, 84, 69, 49, 48, 34);
        for (Map.Entry<String, Integer> entry : sort1.entrySet()) {
            actualValues.add(entry.getValue());
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
        for (int i = 0; i < expectedValues.size(); i++) {
            assertEquals(expectedValues.get(i), actualValues.get(actualValues.size() - i - 1));
        }

        //repeat for other two sets
        expectedValues = Arrays.asList(300, 223, 220, 206, 191, 188, 187, 187, 182);
        actualValues.clear();
        for (Map.Entry<String, Integer> entry : sort2.entrySet()) {
            actualValues.add(entry.getValue());
        }
        for (int i = 0; i < expectedValues.size(); i++) {
            assertEquals(expectedValues.get(i), actualValues.get(actualValues.size() - i - 1));
        }

        expectedValues = Arrays.asList(966, 442, 421, 397, 384, 379, 294, 249, 246);
        actualValues.clear();
        for (Map.Entry<String, Integer> entry : sort3.entrySet()) {
            actualValues.add(entry.getValue());
        }
        for (int i = 0; i < expectedValues.size(); i++) {
            assertEquals(expectedValues.get(i), actualValues.get(actualValues.size() - i - 1));
        }

    }
}