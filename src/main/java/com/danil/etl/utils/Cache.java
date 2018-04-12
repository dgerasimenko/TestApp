package com.danil.etl.utils;

import java.util.*;

public class Cache<T> implements Iterable<T>{
    private final int size;
    private final Set<T> cache;

    public Cache(int size) {
        this.size = size;
        this.cache = new LinkedHashSet<>();
    }

    public void put(T value) {
        if (cache.size() >= size) {
            final Iterator iterator = cache.iterator();
            iterator.next();
            iterator.remove();
        }
        cache.add(value);
    }
    public Set<T> getValues() {
        return this.cache;
    }

    @Override
    public Iterator<T> iterator() {
        return cache.iterator();
    }

    public int size() {
        return this.cache.size();
    }
}
