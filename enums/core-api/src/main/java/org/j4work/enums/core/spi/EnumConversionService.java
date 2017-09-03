package org.j4work.enums.core.spi;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * .
 */
public interface EnumConversionService {

    @Nullable
    Object convert(@Nullable Object sourceObject, Class targetClass);

    boolean canConvert(Class sourceClass, Class targetClass);

    /**
     * Convert id to an enum constant of given enumClass.
     *
     * If a converter is not found for the given enumClass or conversion
     * from the given id's type is not supported by the found converter, null is returned.
     *
     * Otherwise, either a non-null value will be returned or an exception will be thrown.
     *
     * @param id Id object to be converted to an enum constant.
     * @return Converted enum constant or null if conversion is not supported.
     */
    @Nullable
    <E extends Enum<E>> E valueOf(@Nonnull Class<E> enumClass, @Nonnull Object id);

    /**
     * Convert enum constant to object of given targetClass.
     *
     * If a converter is not found for the class of the given enum constant or conversion
     * to the given targetClass is not supported by the found converter, null is returned.
     *
     * Otherwise, either a non-null value will be returned or an exception will be thrown.
     *
     * @param enumConstant Enum constant to be converted.
     * @param targetClass Target class to convert to.
     * @param <T> Type of target object.
     * @return Converted object or null if conversion is not supported for the given targetClass.
     */
    @Nullable
    <E extends Enum<E>, T> T toObject(E enumConstant, Class<T> targetClass);
}
