package ru.nsu.rebrin;

import java.util.ConcurrentModificationException;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * HashTable.
 *
 * @param <K> - key
 * @param <V> - value
 */
public class HashTable<K, V> {
    final int capacity = 16;
    List<Entry<K, V>>[] table;
    int size = 0;
    int modCount = 0;

    /**
     * HashTable.
     */
    public HashTable() {
        table = new List[capacity];
        for (int i = 0; i < table.length; i++) {
            table[i] = new LinkedList<>();
        }
    }

    /**
     * HashTableIterator.
     */
    public class HashTableIterator {
        private int expectedModCount = modCount;
        private int currentBucket = 0;
        private int currentInd = -1;

        /**
         * Next.
         *
         * @return - next
         */
        public boolean hasNext() {
            if (expectedModCount != modCount) {
                throw new ConcurrentModificationException();
            }

            while (currentBucket < table.length) {
                if (currentInd + 1 < table[currentBucket].size()) {
                    return true;
                }
                currentBucket++;
                currentInd = -1;
            }
            return false;
        }

        /**
         * Next.
         *
         * @return - next
         */
        public Entry<K, V> next() {
            if (expectedModCount != modCount) {
                throw new ConcurrentModificationException();
            }

            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            currentInd++;
            return table[currentBucket].get(currentInd);
        }
    }

    /**
     * hash.
     *
     * @param key - key
     * @return - hash
     */
    int hash(K key) {
        return Math.abs(key.hashCode() % table.length);
    }

    /**
     * add.
     *
     * @param key   - key
     * @param value - val
     */
    public void put(K key, V value) {
        int index = hash(key);
        for (Entry<K, V> entry : table[index]) {
            if (entry.key.equals(key)) {
                entry.value = value;
                modCount++;
                return;
            }
        }
        table[index].add(new Entry<>(key, value));
        size++;
        modCount++;
    }

    /**
     * Get val.
     *
     * @param key - key
     * @return - val
     */
    public V get(K key) {
        int index = hash(key);
        for (Entry<K, V> entry : table[index]) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }
        return null;
    }

    /**
     * remove.
     *
     * @param key - key
     */
    public void remove(K key) {
        int index = hash(key);
        List<Entry<K, V>> bucket = table[index];
        for (int i = 0; i < bucket.size(); i++) {
            if (bucket.get(i).key.equals(key)) {
                bucket.remove(i);
                size--;
                modCount++;
                return;
            }
        }
    }

    /**
     * Update val.
     *
     * @param key    - key
     * @param newVal - newVal
     */
    public void update(K key, V newVal) {
        int index = hash(key);
        for (Entry<K, V> entry : table[index]) {
            if (entry.key.equals(key)) {
                entry.value = newVal;
                modCount++;
                return;
            }
        }
        throw new NoSuchElementException("Key not found");
    }

    /**
     * Contains.
     *
     * @param key - key
     * @return - true or false
     */
    public boolean containsKey(K key) {
        int index = hash(key);
        for (Entry<K, V> entry : table[index]) {
            if (entry.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * iteration.
     *
     * @return - HashTableIterator
     */
    public HashTableIterator iterator() {
        return new HashTableIterator();
    }

    /**
     * Equals.
     *
     * @param other - other
     * @return - true or false
     */
    public boolean equals(HashTable<K, V> other) {
        if (this.size != other.size) {
            return false;
        }
        for (List<Entry<K, V>> bucket : table) {
            for (Entry<K, V> entry : bucket) {
                if (!Objects.equals(entry.value, other.get(entry.key))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * To string.
     *
     * @return - string
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        for (List<Entry<K, V>> bucket : table) {
            for (Entry<K, V> entry : bucket) {
                sb.append(entry.key).append("=").append(entry.value).append(", ");
            }
        }
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 2);
        }
        sb.append("}");
        return sb.toString();
    }

    /**
     * Single pair.
     *
     * @param <K> - key
     * @param <V> - val
     */
    static class Entry<K, V> {
        K key;
        V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
