package org.j4work.enums.core.api;

import org.j4work.enums.core.spi.EnumConverter;

import javax.annotation.Nonnull;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

class EnumConverterCache {

    private final ConcurrentMap<Class<? extends Enum>, EnumConverter> cachedConverterByEnumClass =
        new ConcurrentHashMap<Class<? extends Enum>, EnumConverter>(16);

    @SuppressWarnings("unchecked")
    <E extends Enum<E>> EnumConverter<E> readFromCache(@Nonnull Class<E> enumClass) {
        return (EnumConverter<E>) cachedConverterByEnumClass.get(enumClass);
    }

    /**
     * @param enumClass
     * @param converter
     * @param <E>
     * @return
     */
    @Nonnull
    <E extends Enum<E>>
    EnumConverter<E> writeToCache(@Nonnull Class<E> enumClass, @Nonnull EnumConverter<E> converter) {
        cachedConverterByEnumClass.put(enumClass, converter);

        return converter;
    }
}
