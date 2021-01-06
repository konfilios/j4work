package org.j4work.domain.base.repos;

import javax.persistence.Entity;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Resolve the entity type of a repo.
 */
public class EntityTypeResolver {

    static String resolveEntityType(Object repo) {
        final Class<?> repoClass = repo.getClass();
        final String annotation = findEntityTypeByAnnotation(repoClass);

        if (annotation != null) {
            return annotation;
        }

        final String msg = findEntityTypeByGenericParam(repoClass);
        if (msg != null) {
            return msg;
        }

        return repoClass.getSimpleName();
    }

    private static String findEntityTypeByGenericParam(Class<?> repoClass) {
        final Type[] interfaces = repoClass.getGenericInterfaces();
        for (Type anInterface : interfaces) {
            for (Type genericInterface : ((Class) anInterface).getGenericInterfaces()) {
                if (genericInterface instanceof ParameterizedType)  {
                    ParameterizedType aParameterized = (ParameterizedType)genericInterface;
                    final Type[] actualTypeArguments = aParameterized.getActualTypeArguments();
                    for (Type actualTypeArgument : actualTypeArguments) {
                        if (actualTypeArgument instanceof Class) {
                            final Class actualClass = (Class) actualTypeArgument;
                            final Annotation annotation = actualClass.getAnnotation(
                                Entity.class);

                            if (annotation != null) {
                                return actualClass.getSimpleName();
                            }
                        }
                    }
                }
            }
        }

        return null;
    }

    private static String findEntityTypeByAnnotation(Class<?> repoClass) {

        for (Class<?> anInterface : repoClass.getInterfaces()) {
            final EntityType entityType = anInterface.getAnnotation(EntityType.class);
            if (entityType != null) {
                return entityType.value();
            }
        }
        return null;
    }
}
