package org.j4work.classutils.core;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * .
 */
public class ClassMembers {

    /**
     * Collects all fields declared in the given class <u>and</u> inherited from
     * parents.
     *
     * @param clazz the class to parse
     * @return a list of all fields declared in the class or its  parents, in the
     * order determined by successive {@link Class#getDeclaredFields()}
     * calls
     * @see #getAllDeclaredFields(Class, Class)
     */
    public static List<Field> getAllDeclaredFields(Class<?> clazz) {
        return getAllDeclaredFields(clazz, Object.class);
    }

    /**
     * Collects all fields declared in the given class <u>and</u> inherited from
     * parents <strong>up to and including the given parent class</strong>.
     *
     * @param <T>        the type of the class to parse
     * @param clazz      the class to parse
     * @param superclass the superclass of the class to parse at which traversal should be
     *                   stopped
     * @return a list of all fields declared in the class or its parents up to and including
     * the given parent class, in the order determined by successive
     * {@link Class#getDeclaredFields()} calls
     * @see #getAllDeclaredFields(Class)
     */
    public static <T> List<Field> getAllDeclaredFields(
        Class<T> clazz,
        Class<? super T> superclass
    ) {
        final List<Field> fields = new ArrayList<Field>();

        for (Class<?> immediateSuperclass : TypeChains.getSuperclassChain(clazz, superclass)) {
            fields.addAll(Arrays.asList(immediateSuperclass.getDeclaredFields()));
        }

        return fields;
    }

    /**
     * Collects all fields declared in the given class <u>and</u> inherited from
     * parents that are annotated with an annotation of the given type.
     *
     * @param clazz          the class to parse
     * @param annotationType the non-{@code null} type (class) of the annotation required
     * @return a list of all fields declared in the class or its  parents, in the
     * order determined by successive {@link Class#getDeclaredFields()}
     * calls
     * @throws IllegalArgumentException if {@code clazz} or {@code annotationType} is {@code null}
     */
    @Nonnull
    public static List<Field> getAllAnnotatedDeclaredFields(
        @Nonnull Class<?> clazz,
        @Nonnull Class<? extends Annotation> annotationType
    ) {
        final List<Field> annotatedFields = new ArrayList<Field>();

        for (Field field : getAllDeclaredFields(clazz)) {
            if (field.isAnnotationPresent(annotationType)) {
                annotatedFields.add(field);
            }
        }

        return annotatedFields;
    }

    /**
     * Collects all methods of the given class (as returned by {@link Class#getMethods()} that
     * are annotated with the given annotation.
     *
     * @param clazz          the class whose methods should be returned
     * @param annotationType the annotation that the returned methods should be annotated with
     * @return the methods of the given class annotated with the given annotation
     * @throws IllegalArgumentException if {@code clazz} is {@code null}
     */
    @Nonnull
    public static Set<Method> getAnnotatedMethods(
        @Nonnull Class<?> clazz,
        @Nonnull Class<? extends Annotation> annotationType
    ) {
        final Set<Method> annotatedMethods = new HashSet<Method>();

        for (Method method : clazz.getMethods()) {
            if (method.isAnnotationPresent(annotationType)) {
                annotatedMethods.add(method);
            }
        }

        return annotatedMethods;
    }
}
