package org.j4work.enums.core.api;

import org.j4work.enums.core.spi.EnumConversionRegistry;
import org.j4work.enums.core.spi.EnumConversionService;
import org.j4work.enums.core.spi.EnumConverter;
import org.j4work.enums.core.spi.EnumConverterFactory;
import org.j4work.enums.core.spi.converters.StandardJavaEnumConverter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Registry of currently used EnumConverter instances organized
 * by their corresponding Enum class.
 */
public class EnumsService implements EnumConversionService, EnumConversionRegistry {

    private final Queue<EnumConverterFactory> delegatingConverterFactories =
        new ConcurrentLinkedQueue<EnumConverterFactory>();

    private final EnumConverterCache converterCache = new EnumConverterCache();

    @Nonnull
    private <E extends Enum<E>>
    EnumConverter<E> getConverter(@Nonnull Class<E> enumClass) {

        // Look up enum class in the cache
        final EnumConverter<E> cachedConverter = converterCache.readFromCache(enumClass);

        if (cachedConverter != null) {
            return cachedConverter;
        }

        // Ask register converter factories
        // No extra synchronization required than what is provided by the ConcurrentMap.
        // Worst case scenario is that multiple converters will be created and then go wasted.
        for (EnumConverterFactory converterFactory : delegatingConverterFactories) {
            final EnumConverter<E> converter = converterFactory.getConverter(enumClass);

            if (converter != null) {
                return converterCache.writeToCache(enumClass, converter);
            }
        }

        // Fall back to standard java enum converter
        return converterCache.writeToCache(enumClass, new StandardJavaEnumConverter<E>(enumClass));
    }

    /**
     * Register converter factory.
     *
     * @param converterFactory Converter factory to register.
     */
    @Override
    public void registerConverterFactory(@Nonnull EnumConverterFactory converterFactory) {
        delegatingConverterFactories.add(converterFactory);
    }

    @Override
    @Nullable
    public Object convert(@Nullable Object sourceObject, Class targetClass) {
        if (sourceObject == null) {
            // We don't convert null objects
            return null;
        }

        if (sourceObject instanceof Enum) {
            // Converting *from* Enum to Number/String
            return toObject((Enum) sourceObject, targetClass);

        } else if (Enum.class.isAssignableFrom(targetClass)) {
            // Converting from Number/String *to* Enum
            return valueOf(targetClass, sourceObject);

        } else {
            // We only convert from or to Enum constants.
            return null;
        }
    }

    @Override
    public boolean canConvert(Class sourceClass, Class targetClass) {
        if (Enum.class.isAssignableFrom(sourceClass)) {
            // Converting *from* Enum to Number/String
            return getConverter(sourceClass).canConvert(targetClass);

        } else if (Enum.class.isAssignableFrom(targetClass)) {
            // Converting from Number/String *to* Enum
            return getConverter(targetClass).canConvert(sourceClass);

        } else {
            return false;
        }
    }

    @Override
    @Nullable
    public <E extends Enum<E>>
    E valueOf(@Nonnull Class<E> enumClass, @Nonnull Object id) {
        return getConverter(enumClass).valueOf(id);
    }

    @Override
    @Nullable
    public <E extends Enum<E>, T>
    T toObject(E enumConstant, Class<T> targetClass) {
        return getConverter(enumConstant.getDeclaringClass()).toObject(enumConstant, targetClass);
    }

}
