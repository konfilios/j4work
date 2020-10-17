package org.j4work.enums.core.spi;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Factory for EnumConverters.
 */
public interface EnumConverterFactory {

    /**
     * Create a converter for given enumClass.
     *
     * @param enumClass Enumeration class for which a converter will be looked up.
     * @param <E>       Enumeration type.
     * @return Converter for given enumClass or null if no applicable converter could be created/found.
     */
    @Nullable
    <E extends Enum<E>> EnumConverter<E> getConverter(@Nonnull Class<E> enumClass);
}
