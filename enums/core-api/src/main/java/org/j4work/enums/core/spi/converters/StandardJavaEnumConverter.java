package org.j4work.enums.core.spi.converters;

import org.j4work.enums.core.spi.EnumConverter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Converts enums exactly as java language does by default.
 * <p>
 * For each enum constant:
 * 1. Its ordinal is used as the numeric id
 * 2. Its name is used as the string id
 */
public class StandardJavaEnumConverter<E extends Enum<E>> implements EnumConverter<E> {

    private final Class<E> enumClass;

    private final E[] enumConstants;

    public StandardJavaEnumConverter(Class<E> enumClass) {
        this.enumClass = enumClass;
        this.enumConstants = enumClass.getEnumConstants();
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
            return enumConstants[((Number) id).intValue()];

        } else if (id instanceof String) {
            return Enum.valueOf(enumClass, (String) id);

        } else {
            return null;
        }
    }

    @Override
    @Nullable
    @SuppressWarnings("unchecked")
    public <T> T toObject(@Nonnull E enumConstant, @Nonnull Class<T> targetClass) {

        if (String.class == targetClass) {
            return (T) enumConstant.name();

        } else if (Number.class.isAssignableFrom(targetClass)) {
            return NumberUtils.convertNumber(enumConstant.ordinal(), targetClass);

        } else {
            return null;
        }
    }
}
