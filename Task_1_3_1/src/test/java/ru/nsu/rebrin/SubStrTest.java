package ru.nsu.rebrin;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class SubStrTest {

    @Test
    public void testFindSubstringInNonEmptyFile() throws IOException, URISyntaxException {
        File testFile = Paths.get(getClass().getClassLoader().getResource("test.txt").toURI()).toFile();

        // Perform the test
        int[] result = SubStr.sub_string(testFile.getPath(), "bcd");
        assertArrayEquals(new int[]{1}, result);
    }

    @Test
    public void testFindRepeatedSubstring() throws IOException, URISyntaxException {
        File testFile = Paths.get(getClass().getClassLoader().getResource("test1.txt").toURI()).toFile();

        // Perform the test
        int[] result = SubStr.sub_string(testFile.getPath(), "aaa");
        assertArrayEquals(new int[]{0, 1, 2, 3, 4}, result);
    }

    @Test
    public void testEmptyFile() throws IOException, URISyntaxException {
        File testFile = Paths.get(getClass().getClassLoader().getResource("test2.txt").toURI()).toFile();

        // Perform the test
        int[] result = SubStr.sub_string(testFile.getPath(), "anything");
        assertArrayEquals(new int[0], result);
    }

    @Test
    public void testEmptySubstring() throws IOException, URISyntaxException {
        File testFile = Paths.get(getClass().getClassLoader().getResource("test.txt").toURI()).toFile();

        // Perform the test with an empty substring
        int[] result = SubStr.sub_string(testFile.getPath(), "");
        assertArrayEquals(new int[0], result);
    }

    @Test
    public void testFindLongRusSubstring() throws IOException, URISyntaxException {
        File testFile = Paths.get(getClass().getClassLoader().getResource("longRusText.txt").toURI()).toFile();

        String substring = "мир";

        int[] expectedIndices = new int[1000];
        for (int i = 0; i < 1000; i++) {
            expectedIndices[i] = i * 10 + 7;
        }

        int[] result = SubStr.sub_string(testFile.getPath(), substring);
        assertArrayEquals(expectedIndices, result);
    }

    @Test
    public void testFindRusSubstring1() throws IOException, URISyntaxException {
        File testFile = Paths.get(getClass().getClassLoader().getResource("testR2.txt").toURI()).toFile();

        int[] result = SubStr.sub_string(testFile.getPath(), "аб");
        System.out.println(Arrays.toString(result));
        assertArrayEquals(new int[]{0, 6}, result);
    }
}
