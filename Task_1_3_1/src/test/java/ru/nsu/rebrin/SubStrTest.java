package ru.nsu.rebrin;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;

public class SubStrTest {

    @Test
    public void testFindSubstringInNonEmptyFile() throws IOException {
        // Set up the file and expected result
        File testFile = Paths.get("test.txt").toFile();
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("abcde");
        }

        // Perform the test
        int[] result = SubStr.sub_string(testFile.getPath(), "bcd");
        assertArrayEquals(new int[]{1}, result);

        // Clean up
        testFile.delete();
    }

    @Test
    public void testFindRepeatedSubstring() throws IOException {
        // Set up the file and expected result
        File testFile = Paths.get("test1.txt").toFile();
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("aaaaaaa");
        }

        // Perform the test
        int[] result = SubStr.sub_string(testFile.getPath(), "aaa");
        assertArrayEquals(new int[]{0, 1, 2, 3, 4}, result);

        // Clean up
        testFile.delete();
    }

    @Test
    public void testEmptyFile() throws IOException {
        // Set up the empty file
        File testFile = Paths.get("test2.txt").toFile();
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("");  // Write nothing for an empty file
        }

        // Perform the test
        int[] result = SubStr.sub_string(testFile.getPath(), "anything");
        assertArrayEquals(new int[0], result);

        // Clean up
        testFile.delete();
    }

    @Test
    public void testEmptySubstring() throws IOException {
        // Set up the file
        File testFile = Paths.get("test.txt").toFile();
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("abcde");
        }

        // Perform the test with an empty substring
        int[] result = SubStr.sub_string(testFile.getPath(), "");
        assertArrayEquals(new int[0], result);

        // Clean up
        testFile.delete();
    }
}
