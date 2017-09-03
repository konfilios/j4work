package org.j4work.enums.core.api;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Global registry of enum converters and converter factories.
 * <p>
 * This is the central point of interaction between:
 * 1. Implementors/providers of enum converters (or factories) who must register
 * their converters (or factories)
 * <p>
 * 2. Users/consumers of the enum conversion functionality, such as adapter
 * implementors (see existing adapters for spring, jackson, hibernate, etc) or
 * even application developers who need an easy way to directly perform enum conversions.
 */
public abstract class Enums {

    public static final EnumsService SERVICE = new EnumsService();

    private Enums() {
    }

    @Nullable
    public static <E extends Enum<E>> E valueOf(@Nonnull Class<E> enumClass, @Nonnull Object id) {
        return SERVICE.valueOf(enumClass, id);
    }

    @Nullable
    public static <E extends Enum<E>, T> T toObject(E enumConstant, Class<T> targetClass) {
        return SERVICE.toObject(enumConstant, targetClass);
    }
}
