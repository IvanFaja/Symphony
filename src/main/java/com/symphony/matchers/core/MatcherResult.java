package com.symphony.matchers.core;

import java.util.stream.Stream;

public class MatcherResult <T,V> {
    private T tag;
    private V values;

    public MatcherResult(T tag, V values) {
        this.tag = tag;
        this.values = values;
    }


    public T getTag() {
        return tag;
    }

    public void setTag(T tag) {
        this.tag = tag;
    }

    public V getValues() {
        return values;
    }

    public void setValues(V values) {
        this.values = values;
    }
}
