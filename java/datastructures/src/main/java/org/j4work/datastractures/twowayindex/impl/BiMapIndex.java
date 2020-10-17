package org.j4work.datastractures.twowayindex.impl;

import org.j4work.datastractures.twowayindex.api.TwoWayIndex;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * A Two-Way index backed by two maps.
 */
public class BiMapIndex<K, V> implements TwoWayIndex<K, V> {

    private final Map<K, V> values;

    private final Map<V, K> keys;

    public BiMapIndex(Map<K, V> values) {
        this.values = values;
        this.keys = invert(values);
    }

    private Map<V, K> invert(Map<K, V> map) {
        final Map<V, K> inverse = new HashMap<V, K>(map.size());

        for (Map.Entry<K, V> entry : map.entrySet()) {
            final V inverseKey = normalizeSearchValue(entry.getValue());
            final K inverseValue = entry.getKey();

            final K existingInverseValue = inverse.put(inverseKey, inverseValue);
            if (existingInverseValue != null) {
                throw new IllegalStateException(
                    "Cannot inverse map: " + inverseKey + " maps back to both "
                        + inverseValue + " and " + existingInverseValue);
            }
        }

        return inverse;
    }

    @Nonnull
    protected V normalizeSearchValue(@Nonnull V searchValue) {
        return searchValue;
    }

    @Nonnull
    @Override
    public V getValueOfKey(@Nonnull K searchKey) {
        return values.get(searchKey);
    }

    @Nonnull
    @Override
    public K getKeyOfValue(@Nonnull V searchValue) {
        return keys.get(normalizeSearchValue(searchValue));
    }

    @Override
    public int size() {
        return values.size();
    }
}
