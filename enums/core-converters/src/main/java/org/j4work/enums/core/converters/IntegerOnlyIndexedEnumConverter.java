package org.j4work.enums.core.converters;

import org.j4work.datastractures.twowayindex.api.TwoWayIndex;
import org.j4work.enums.core.spi.EnumConverter;
import org.j4work.enums.core.spi.converters.NumberUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Convert between Enums and Strings using an Integer TwoWayIndex.
 * <p>
 * Conversions to/from both Numbers and Strings are supported:
 * 1. Numbers are treated as integers.
 * 2. Strings are treated as string representations of Integers and are parsed using Integer.parseInt().
 */
public class IntegerOnlyIndexedEnumConverter<E extends Enum<E>> implements EnumConverter<E> {

    private final TwoWayIndex<E, Integer> index;

    public IntegerOnlyIndexedEnumConverter(TwoWayIndex<E, Integer> index) {
        this.index = index;
    }

    @Override
    public boolean canConvert(@Nonnull Class otherClass) {
        return String.class == otherClass
            || Number.class.isAssignableFrom(otherClass);
    }

    @Nullable
    @Override
    public E valueOf(@Nonnull Object id) {
        if (id instanceof Number) {
            return index.getKeyOfValue(((Number) id).intValue());

        } else if (id instanceof String) {
            return index.getKeyOfValue(Integer.parseInt((String) id));

        } else {
            return null;
        }
    }

    @Override
    @Nullable
    @SuppressWarnings("unchecked")
    public <T> T toObject(@Nonnull E enumConstant, @Nonnull Class<T> targetClass) {

        if (String.class == targetClass) {
            return (T) index.getValueOfKey(enumConstant).toString();

        } else if (Number.class.isAssignableFrom(targetClass)) {
            return NumberUtils.convertNumber(index.getValueOfKey(enumConstant), targetClass);

        } else {
            return null;
        }
    }
}
