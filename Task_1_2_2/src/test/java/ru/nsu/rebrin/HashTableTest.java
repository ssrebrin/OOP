package ru.nsu.rebrin;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        assertNull(hashTable.get("four"));
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
        assertNull(hashTable.get("one"));
        assertEquals(2, hashTable.get("two"));

        hashTable.remove("two");
        assertNull(hashTable.get("two"));
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
}
