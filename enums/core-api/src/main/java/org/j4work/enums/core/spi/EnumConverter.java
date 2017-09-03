package org.j4work.enums.core.spi;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Converts between Enum constants and Numbers or String values.
 */
public interface EnumConverter<E extends Enum<E>> {
    /**
     * @return True if otherClass can be converted to/from enums.
     */
    boolean canConvert(@Nonnull Class otherClass);

    /**
     * Convert id to an enum constant.
     *
     * If conversion from the given id's type is not supported at all, null is returned.
     *
     * If conversion from the given id type is supported, either a non-null value will be
     * returned or an exception will be thrown.
     *
     * @param id Id object to be converted to an enum constant.
     * @return Converted enum constant.
     */
    @Nullable
    E valueOf(@Nonnull Object id);

    /**
     * Convert enum constant to object of given targetClass.
     *
     * If conversion to the given targetClass is not supported at all, null is returned.
     *
     * If conversion to the given targetClass is supported, either a non-null value will be
     * returned or an exception will be thrown.
     *
     * @param enumConstant Enum constant to be converted.
     * @param targetClass Target class to convert to.
     * @param <T> Type of target object.
     * @return Converted object or null if conversion is not supported for the given targetClass.
     */
    @Nullable
    <T> T toObject(@Nonnull E enumConstant, @Nonnull Class<T> targetClass);
}
