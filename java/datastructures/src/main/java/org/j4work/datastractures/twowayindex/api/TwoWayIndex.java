package org.j4work.datastractures.twowayindex.api;

import javax.annotation.Nonnull;

/**
 * .
 */
public interface TwoWayIndex<K, V> {

    @Nonnull
    V getValueOfKey(@Nonnull K key);

    @Nonnull
    K getKeyOfValue(@Nonnull V value);

    int size();
}
