package lab9;

import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  @author Aranne
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    private static final int DEFAULT_SIZE = 16;
    private static final double MAX_LF = 0.75;

    private ArrayMap<K, V>[] buckets;
    private int size;

    private int loadFactor() {
        return size / buckets.length;
    }

    public MyHashMap() {
        buckets = new ArrayMap[DEFAULT_SIZE];
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        this.size = 0;
        for (int i = 0; i < this.buckets.length; i += 1) {
            this.buckets[i] = new ArrayMap<>();
        }
    }

    /** Computes the hash function of the given key. Consists of
     *  computing the hashcode, followed by modding by the number of buckets.
     *  To handle negative numbers properly, uses floorMod instead of %.
     */
    private int hash(K key) {
        if (key == null) {
            return 0;
        }

        int numBuckets = buckets.length;
        return Math.floorMod(key.hashCode(), numBuckets);
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Calls on get() with a null key.");
        }
        int hashIndex = hash(key);
        return buckets[hashIndex].get(key);
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Calls on put() with a null key.");
        }
        if (value == null) {
            remove(key);
            return;
        }
        int hashIndex = hash(key);
        if (!buckets[hashIndex].containsKey(key)) {
            size += 1;
            if (loadFactor() > MAX_LF) {
                resize(DEFAULT_SIZE * 2);
                hashIndex = hash(key);             // new hash index.
            }
        }
        buckets[hashIndex].put(key, value);   // If key is exists, we could update value.
    }

    /** Resize the Map buckets. */
    private void resize(int Capacity) {
        ArrayMap<K, V>[] newBuckets = new ArrayMap[Capacity];
        for (int i = 0; i < newBuckets.length; i += 1) {
            newBuckets[i] = new ArrayMap<>();
        }
        for (K key : keySet()) {
            int hashIndex = Math.floorMod(key.hashCode(), newBuckets.length);
            newBuckets[hashIndex].put(key, get(key));
        }
        buckets = newBuckets;
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();
        for (ArrayMap<K, V> am : buckets) {
            keySet.addAll(am.keySet());
        }
        return keySet;
    }

    /* Removes the mapping for the specified key from this map if exists.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Calls on remove() with a null key.");
        }
        int hashIndex = hash(key);
        if (buckets[hashIndex].keySet().contains(key)) {
            size -= 1;
            if (loadFactor() < 0.25 && buckets.length > DEFAULT_SIZE) {
                resize(buckets.length / 2);
                hashIndex = hash(key);
            }
        }
        return buckets[hashIndex].remove(key);
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for this lab. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Calls on remove() with a null key.");
        }
        int hashIndex = hash(key);
        if (buckets[hashIndex].get(key).equals(value)) {
            size -= 1;
            if (loadFactor() < 0.25 && buckets.length > DEFAULT_SIZE) {
                resize(buckets.length / 2);
                hashIndex = hash(key);
            }
            return buckets[hashIndex].remove(key);
        }
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }
}
