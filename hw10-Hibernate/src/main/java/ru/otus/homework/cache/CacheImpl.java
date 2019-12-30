package ru.otus.homework.cache;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CacheImpl<K, V> implements Cache<K, V> {
    private final Map<K, SoftReference<V>> referenceSet;

    public CacheImpl() {
        referenceSet = new HashMap<>();
    }

    @Override
    public void put(K key, V value) {
        referenceSet.put(key, new SoftReference<>(value));
    }

    @Override
    public V get(K key) {
        SoftReference<V> t = referenceSet.get(key);
        if (t == null) {
            return null;
        }
        return t.get();
    }

    @Override
    public void remove(K key) {
        referenceSet.remove(key);
    }

    @Override
    public Set<V> getAll() {
        Set<V> set = new HashSet<>();
        for (SoftReference<V> reference : referenceSet.values()) {
            set.add(reference.get());
        }
        return set;
    }
}