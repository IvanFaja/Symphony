package com.symphony.matchers.core;

import java.util.stream.Stream;


public interface SimpleMatcher<T, V> {
    Stream<V> evaluate(T value);

    default SimpleMatcher<T, V> aggregate(SimpleMatcher<? super T, V> other) {
        if (other == null)
            return this;
        return (a) -> Stream.concat(evaluate(a), other.evaluate(a));

    }
}
