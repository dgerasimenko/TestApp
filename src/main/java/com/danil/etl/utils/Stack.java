package com.danil.etl.utils;

import org.apache.commons.lang.StringUtils;

import java.util.*;

public class Stack<T> implements Iterable<T> {

    private final Deque<T> stack;
    private final Set<T> set;

    public Stack() {
        this.stack = new LinkedList();
        this.set = new HashSet<>();
    }

    public T pull() {
        return stack.getLast();
    }

    public void push(T value) {
        if (value != null) {
            if (!set.contains(value)) {
                stack.push(value);
                set.add(value);
            }
        }
    }
    public int size() {
        return stack.size();
    }

    @Override
    public Iterator<T> iterator() {
        return stack.iterator();
    }

    @Override
    public String toString() {
        return StringUtils.join(stack, ",");
    }
}
