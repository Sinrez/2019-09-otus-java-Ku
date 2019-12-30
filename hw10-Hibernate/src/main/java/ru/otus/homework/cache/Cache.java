package ru.otus.homework.cache;

import java.util.Set;

public interface Cache<K,V> {

    void put(K key,V value);

    V get(K key);

    void remove(K key);

    Set<V> getAll();
}
