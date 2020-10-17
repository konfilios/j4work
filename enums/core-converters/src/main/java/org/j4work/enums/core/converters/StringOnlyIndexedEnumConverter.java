package org.j4work.enums.core.converters;

import org.j4work.datastractures.twowayindex.api.TwoWayIndex;
import org.j4work.enums.core.spi.EnumConverter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Convert between Enums and Strings using a String TwoWayIndex.
 * <p>
 * Conversions to/from numbers are not supported.
 */
public class StringOnlyIndexedEnumConverter<E extends Enum<E>> implements EnumConverter<E> {

    private final TwoWayIndex<E, String> index;

    public StringOnlyIndexedEnumConverter(TwoWayIndex<E, String> index) {
        this.index = index;
    }


    @Nullable
    @Override
    public E valueOf(@Nonnull Object id) {
        if (id instanceof String) {
            return index.getKeyOfValue((String) id);

        } else {
            return null;
        }
    }

    @Override
    public boolean canConvert(@Nonnull Class otherClass) {
        return String.class == otherClass;
    }

    @Override
    @Nullable
    @SuppressWarnings("unchecked")
    public <T> T toObject(@Nonnull E enumConstant, @Nonnull Class<T> targetClass) {
        if (String.class == targetClass) {
            return (T) index.getValueOfKey(enumConstant);

        } else {
            return null;
        }
    }
}
