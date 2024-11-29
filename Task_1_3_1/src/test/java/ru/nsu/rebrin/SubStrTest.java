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
        Long[] result = SubStr.sub_string(testFile.getPath(), "bcd");
        assertArrayEquals(new Long[]{(long) 1}, result);
    }

    @Test
    public void testFindRepeatedSubstring() throws IOException, URISyntaxException {
        File testFile = Paths.get(getClass().getClassLoader().getResource("test1.txt").toURI()).toFile();

        // Perform the test
        Long[] result = SubStr.sub_string(testFile.getPath(), "aaa");
        assertArrayEquals(new Long[]{(long) 0, (long) 1, (long) 2, (long) 3, (long) 4}, result);
    }

    @Test
    public void testEmptyFile() throws IOException, URISyntaxException {
        File testFile = Paths.get(getClass().getClassLoader().getResource("test2.txt").toURI()).toFile();

        // Perform the test
        Long[] result = SubStr.sub_string(testFile.getPath(), "anything");
        assertArrayEquals(new Long[0], result);
    }

    @Test
    public void testEmptySubstring() throws IOException, URISyntaxException {
        File testFile = Paths.get(getClass().getClassLoader().getResource("test.txt").toURI()).toFile();

        // Perform the test with an empty substring
        Long[] result = SubStr.sub_string(testFile.getPath(), "");
        assertArrayEquals(new Long[0], result);
    }

    @Test
    public void testFindLongRusSubstring() throws IOException, URISyntaxException {
        File testFile = Paths.get(getClass().getClassLoader().getResource("longRusText.txt").toURI()).toFile();

        String substring = "мир";

        Long[] expectedIndices = new Long[1000];
        for (int i = 0; i < 1000; i++) {
            expectedIndices[i] = (long) (i * 10 + 7);
        }

        Long[] result = SubStr.sub_string(testFile.getPath(), substring);
        assertArrayEquals(expectedIndices, result);
    }

    @Test
    public void testFindRusSubstring1() throws IOException, URISyntaxException {
        File testFile = Paths.get(getClass().getClassLoader().getResource("testR2.txt").toURI()).toFile();

        Long[] result = SubStr.sub_string(testFile.getPath(), "аб");
        System.out.println(Arrays.toString(result));
        assertArrayEquals(new Long[]{(long) 0, (long) 6}, result);
    }
}
