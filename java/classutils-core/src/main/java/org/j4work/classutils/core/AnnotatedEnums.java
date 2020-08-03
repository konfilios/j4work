package org.j4work.classutils.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.EnumMap;
import java.util.Map;

/**
 * Reflection utilities for annotated enum classes.
 */
public class AnnotatedEnums {

    /**
     * Retrieve enum constant of given class from field or throw exception.
     */
    private static <E extends Enum<E>> E requireEnumConstant(Field field, Class<E> enumClass) {
        try {
            return enumClass.cast(field.get(null));
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Could not retrieve enum constant " + field, e);
        }
    }

    /**
     * Retrieve annotation of given class from field or throw exception.
     */
    private static <A extends Annotation> A requireAnnotation(Field field, Class<A> annotationClass) {
        final A annotation = field.getAnnotation(annotationClass);

        if (annotation == null) {
            throw new IllegalStateException("Field " + field + " must be annotated with a " + annotationClass);
        }

        return annotation;
    }

    /**
     * Create a map between all constants of given enumClass and their corresponding annotation
     * of given annotationClass.
     *
     * If any enum constant of the enumClass is not annotated with the given annotationClass,
     * an exception will be thrown.
     *
     * @param enumClass Enum class whose constants will be used as keys in the map.
     * @param annotationClass Class of annotations which are required to decorate each
     * @param <E> Enum class type.
     * @param <A> Annotation class type.
     * @return Map between enum constants and their corresponding annotations of annotationClass type.
     * @throws IllegalStateException If an enum constant does not have an annotation of the given class.
     */
    public static <
        E extends Enum<E>,
        A extends Annotation,
        V
        >
    Map<E, V> createMap(
        Class<E> enumClass, Class<A> annotationClass, BiFunction<E, A, V> annotatedEnumValueMapper
    ) {
        final EnumMap<E, V> map = new EnumMap<E, V>(enumClass);

        for (Field field : enumClass.getDeclaredFields()) {
            if (field.isEnumConstant()) {
                final E enumConstant = requireEnumConstant(field, enumClass);
                final A annotation = requireAnnotation(field, annotationClass);
                final V value = annotatedEnumValueMapper.apply(enumConstant, annotation);

                map.put(
                    enumConstant,
                    value
                );
            }
        }

        return map;
    }

}
