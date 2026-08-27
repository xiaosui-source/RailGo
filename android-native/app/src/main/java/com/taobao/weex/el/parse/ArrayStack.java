package com.taobao.weex.el.parse;

import java.util.ArrayList;
import java.util.List;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class ArrayStack<T> {
    private ArrayList<T> stack = new ArrayList<>(4);

    public void add(int i, T t) {
        this.stack.add(i, t);
    }

    public T get(int i) {
        return this.stack.get(i);
    }

    public List<T> getList() {
        return this.stack;
    }

    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    public T peek() {
        return this.stack.get(r0.size() - 1);
    }

    public T pop() {
        return this.stack.remove(r0.size() - 1);
    }

    public void push(T t) {
        this.stack.add(t);
    }

    public T remove(int i) {
        return this.stack.remove(i);
    }

    public int size() {
        return this.stack.size();
    }
}
