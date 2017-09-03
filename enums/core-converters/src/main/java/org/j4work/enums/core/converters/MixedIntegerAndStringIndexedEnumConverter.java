package org.j4work.enums.core.converters;

import org.j4work.datastractures.twowayindex.api.TwoWayIndex;
import org.j4work.enums.core.spi.EnumConverter;
import org.j4work.enums.core.spi.converters.NumberUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Convert between Enums and Strings using a String TwoWayIndex
 * and between Enums and Integers using an Integer TwoWayIndex.
 * <p>
 * Conversions to/from both Numbers and Strings are supported:
 * 1. Numbers are treated as integers and use the integer index.
 * 2. Strings use the integer index.
 */
public class MixedIntegerAndStringIndexedEnumConverter<E extends Enum<E>> implements EnumConverter<E> {

    private final TwoWayIndex<E, Integer> integerIndex;

    private final TwoWayIndex<E, String> stringIndex;

    public MixedIntegerAndStringIndexedEnumConverter(
        TwoWayIndex<E, Integer> integerIndex, TwoWayIndex<E, String> stringIndex
    ) {
        this.integerIndex = integerIndex;
        this.stringIndex = stringIndex;
    }

    @Override
    public boolean canConvert(@Nonnull Class otherClass) {
        return String.class == otherClass
            ||  Number.class.isAssignableFrom(otherClass);
    }

    @Nullable
    @Override
    public E valueOf(@Nonnull Object id) {
        if (id instanceof Number) {
            return integerIndex.getKeyOfValue(((Number)id).intValue());

        } else if (id instanceof String) {
            return stringIndex.getKeyOfValue((String)id);

        } else {
            return null;
        }
    }

    @Override
    @Nullable
    @SuppressWarnings("unchecked")
    public <T> T toObject(@Nonnull E enumConstant, @Nonnull Class<T> targetClass) {

        if (String.class == targetClass) {
            return (T) integerIndex.getValueOfKey(enumConstant);

        } else if (Number.class.isAssignableFrom(targetClass)) {
            return NumberUtils.convertNumber(integerIndex.getValueOfKey(enumConstant), targetClass);

        } else {
            return null;
        }
    }
}
