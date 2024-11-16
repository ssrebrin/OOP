package ru.nsu.rebrin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

public class HashTableTest {

    @Test
    public void testPutAndGet() {
        HashTable<String, Integer> hashTable = new HashTable<>();
        hashTable.put("one", 1);
        hashTable.put("two", 2);
        hashTable.put("three", 3);

        assertEquals(1, hashTable.get("one"));
        assertEquals(2, hashTable.get("two"));
        assertEquals(3, hashTable.get("three"));
        try {
            hashTable.get("four");
        } catch (NoSuchElementException e) {
            assertEquals("Key not found", e.getMessage());
        }
    }

    @Test
    public void testUpdate() {
        HashTable<String, Integer> hashTable = new HashTable<>();
        hashTable.put("one", 1);
        hashTable.update("one", 11);

        assertEquals(11, hashTable.get("one"));

        // Проверяем, что update выбрасывает исключение для отсутствующего ключа "two"
        try {
            hashTable.update("two", 22);
            fail("Expected NoSuchElementException to be thrown");
        } catch (NoSuchElementException e) {
            assertEquals("Key not found", e.getMessage());
        }
    }

    @Test
    public void testRemove() {
        HashTable<String, Integer> hashTable = new HashTable<>();
        hashTable.put("one", 1);
        hashTable.put("two", 2);

        hashTable.remove("one");
        try {
            assertNull(hashTable.get("one"));
        } catch (NoSuchElementException e) {
            assertEquals("Key not found", e.getMessage());
        }
        assertEquals(2, hashTable.get("two"));

        hashTable.remove("two");
        try {
            assertNull(hashTable.get("two"));
        } catch (NoSuchElementException e) {
            assertEquals("Key not found", e.getMessage());
        }
    }

    @Test
    public void testContainsKey() {
        HashTable<String, Integer> hashTable = new HashTable<>();
        hashTable.put("one", 1);

        assertTrue(hashTable.containsKey("one"));
        assertFalse(hashTable.containsKey("two"));
    }

    @Test
    public void testEquals() {
        HashTable<String, Integer> hashTable1 = new HashTable<>();
        hashTable1.put("one", 1);
        hashTable1.put("two", 2);

        HashTable<String, Integer> hashTable2 = new HashTable<>();
        hashTable2.put("one", 1);
        hashTable2.put("two", 2);

        assertTrue(hashTable1.equals(hashTable2));

        hashTable2.put("three", 3);
        assertFalse(hashTable1.equals(hashTable2));
    }

    @Test
    public void testToString() {
        HashTable<String, Integer> hashTable = new HashTable<>();
        hashTable.put("one", 1);
        hashTable.put("two", 2);
        hashTable.put("three", 3);

        String result = hashTable.toString();
        assertTrue(result.contains("one=1"));
        assertTrue(result.contains("two=2"));
        assertTrue(result.contains("three=3"));
    }

    @Test
    public void testIterator() {
        HashTable<String, Integer> hashTable = new HashTable<>();
        hashTable.put("one", 1);
        hashTable.put("two", 2);

        HashTable<String, Integer>.HashTableIterator iterator = hashTable.iterator();

        assertTrue(iterator.hasNext());
        HashTable.Entry<String, Integer> entry1 = iterator.next();
        assertNotNull(entry1);

        assertTrue(iterator.hasNext());
        HashTable.Entry<String, Integer> entry2 = iterator.next();
        assertNotNull(entry2);

        assertFalse(iterator.hasNext());
    }

    @Test
    public void testConcurrentModification() {
        HashTable<String, Integer> hashTable = new HashTable<>();
        hashTable.put("one", 1);
        HashTable<String, Integer>.HashTableIterator iterator = hashTable.iterator();

        hashTable.put("two", 2);

        assertThrows(ConcurrentModificationException.class, iterator::hasNext);
        assertThrows(ConcurrentModificationException.class, iterator::next);
    }

    @Test
    void testResizeIncreasesCapacity() {
        HashTable<Integer, String> hashTable = new HashTable<>();

        for (int i = 0; i < 25; i++) {
            hashTable.put(i, "Value " + i);
        }

        for (int i = 0; i < 25; i++) {
            assertEquals("Value " + i, hashTable.get(i));
        }
    }

    @Test
    void testResizeMaintainsCorrectHashes() {
        HashTable<String, Integer> hashTable = new HashTable<>();

        // Добавляем элементы с коллизиями (те же хэши для начальной ёмкости)
        hashTable.put("one", 1);
        hashTable.put("two", 2);
        hashTable.put("three", 3);
        hashTable.put("four", 4);
        hashTable.put("five", 5);
        hashTable.put("six", 6);
        hashTable.put("seven", 7);
        hashTable.put("eight", 8);
        hashTable.put("nine", 9);
        hashTable.put("ten", 10);
        hashTable.put("eleven", 11); // Должно вызвать resize()

        // Проверяем, что все элементы доступны после resize()
        assertEquals(1, hashTable.get("one"));
        assertEquals(2, hashTable.get("two"));
        assertEquals(3, hashTable.get("three"));
        assertEquals(4, hashTable.get("four"));
        assertEquals(5, hashTable.get("five"));
        assertEquals(6, hashTable.get("six"));
        assertEquals(7, hashTable.get("seven"));
        assertEquals(8, hashTable.get("eight"));
        assertEquals(9, hashTable.get("nine"));
        assertEquals(10, hashTable.get("ten"));
        assertEquals(11, hashTable.get("eleven"));
    }
}
