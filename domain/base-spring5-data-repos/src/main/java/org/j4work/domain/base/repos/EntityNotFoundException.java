package org.j4work.domain.base.repos;

/**
 * Runtime exception thrown when a repo lookup method doesn't yield results.
 */
public class EntityNotFoundException extends RuntimeException {

    final private Class<?> entityClass;

    final private Object searchIdentifier;

    public EntityNotFoundException(
        String message, Class<?> entityClass, Object searchIdentifier
    ) {
        super(message);
        this.entityClass = entityClass;
        this.searchIdentifier = searchIdentifier;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }

    public Object getSearchIdentifier() {
        return searchIdentifier;
    }
}
