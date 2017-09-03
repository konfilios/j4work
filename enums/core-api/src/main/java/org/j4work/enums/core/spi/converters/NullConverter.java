package org.j4work.enums.core.spi.converters;

import org.j4work.enums.core.spi.EnumConverter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A dummy converter incapable of doing any conversions at all.
 */
public class NullConverter<E extends Enum<E>> implements EnumConverter<E> {

    @Override
    public boolean canConvert(@Nonnull Class otherClass) {
        return false;
    }

    @Nullable
    @Override
    public E valueOf(@Nonnull Object id) {
        return null;
    }

    @Nullable
    @Override
    public <T> T toObject(@Nonnull E enumConstant, @Nonnull Class<T> targetClass) {
        return null;
    }
}