import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class FilePickerTest {

    Path stopWordsPath, christmasCarolPath, draculaPath, mobyDickPath;
    FilePicker picker1, picker2, picker3, picker4;

    @BeforeEach
    void setUp() {
        stopWordsPath = Paths.get("test", "testResources", "English Stop Words.txt");
        christmasCarolPath = Paths.get("test", "testResources", "ChristmasCarol.txt");
        draculaPath = Paths.get("test", "testResources", "Dracula.txt");
        mobyDickPath = Paths.get("test", "testResources", "MobyDick.txt");

        picker1 = new FilePicker("christmas", "gift");
        picker2 = new FilePicker("dracula", "suck");
        picker3 = new FilePicker("whale", "stab");
        picker4 = new FilePicker("stopWords", "Browse...");

        picker1.setSelectedFilePath(christmasCarolPath);
        picker2.setSelectedFilePath(draculaPath);
        picker3.setSelectedFilePath(mobyDickPath);
        picker4.setSelectedFilePath(stopWordsPath);

    }

    /**
     * Difficult to directly test JFileChooser, so instead we'll see if the paths manually set
     * can successfully load expected lines from the text files in testResources
     */
    @Test
    void testLoadFile() throws IOException {
        List<String> lines;

        lines = Files.readAllLines(christmasCarolPath);
        //check whether lines are empty
        assertFalse(lines.isEmpty(), "Christmas carol file should not be empty");

        //now test some lines
        assertEquals("This ebook is for the use of anyone anywhere in the United States and",
                lines.get(2), "Third line should match");
        assertEquals("A CHRISTMAS CAROL", lines.get(29), "30th line should match");
        assertEquals("I HAVE endeavoured in this Ghostly little book,", lines.get(41),
                "42th line should match");

        //repeat for a couple of other files
        lines = Files.readAllLines(draculaPath);
        assertFalse(lines.isEmpty(), "Dracula file should not be empty");
        assertEquals("Here I stopped for the night at the Hotel Royale. I had for dinner, or",
                lines.get(130), "131st line should match");
        assertEquals("*** START OF THE PROJECT GUTENBERG EBOOK DRACULA ***", lines.get(22),
                "23rd line should match");
        assertEquals("Vienna early next morning; should have arrived at 6:46, but train was an",
                lines.get(119), "120th line should match");
    }


    @Test
    void reset() {
        picker1.reset();
        picker2.reset();
        picker3.reset();
        picker4.reset();

        assertEquals(picker1.getIsFileSelected(), false);
        assertEquals(picker2.getIsFileSelected(), false);
        assertEquals(picker3.getIsFileSelected(), false);
        assertEquals(picker4.getIsFileSelected(), false);

        assertEquals(picker1.getTextField(), "");
        assertEquals(picker2.getTextField(), "");
        assertEquals(picker3.getTextField(), "");
        assertEquals(picker4.getTextField(), "");

        assertEquals(picker1.getButtonLabel(), "gift");
        assertEquals(picker2.getButtonLabel(), "suck");
        assertEquals(picker3.getButtonLabel(), "stab");
        assertEquals(picker4.getButtonLabel(), "Browse...");

        assertEquals(picker1.getTextFieldLabel(), "christmas");
        assertEquals(picker2.getTextFieldLabel(), "dracula");
        assertEquals(picker3.getTextFieldLabel(), "whale");
        assertEquals(picker4.getTextFieldLabel(), "stopWords");
    }

    @Test
    void getIsFileSelected() {
        assertEquals(picker1.getIsFileSelected(), true);
        assertEquals(picker2.getIsFileSelected(), true);
        assertEquals(picker3.getIsFileSelected(), true);
        assertEquals(picker4.getIsFileSelected(), true);
    }

    @Test
    void getTextField() {
        assertEquals(picker1.getTextField(), christmasCarolPath.toString());
        assertEquals(picker2.getTextField(), draculaPath.toString());
        assertEquals(picker3.getTextField(), mobyDickPath.toString());
        assertEquals(picker4.getTextField(), stopWordsPath.toString());
    }

    @Test
    void getButtonLabel() {
        assertEquals(picker1.getButtonLabel(), "gift");
        assertEquals(picker2.getButtonLabel(), "suck");
        assertEquals(picker3.getButtonLabel(), "stab");
        assertEquals(picker4.getButtonLabel(), "Browse...");
    }

    @Test
    void setButtonLabel() {
        picker1.setButtonLabel("test");
        assertEquals(picker1.getButtonLabel(), "test");
        picker2.setButtonLabel("anothertest");
        assertEquals(picker2.getButtonLabel(), "anothertest");
    }

    @Test
    void getSelectedFilePath() {
        assertEquals(picker1.getSelectedFilePath(), christmasCarolPath);
        assertEquals(picker2.getSelectedFilePath(), draculaPath);
        assertEquals(picker3.getSelectedFilePath(), mobyDickPath);
        assertEquals(picker4.getSelectedFilePath(), stopWordsPath);
    }

    @Test
    void getSelectedFilePathString() {
        assertEquals(picker1.getSelectedFilePathString(), christmasCarolPath.toString());
        assertEquals(picker2.getSelectedFilePathString(), draculaPath.toString());
        assertEquals(picker3.getSelectedFilePathString(), mobyDickPath.toString());
        assertEquals(picker4.getSelectedFilePathString(), stopWordsPath.toString());
    }
}