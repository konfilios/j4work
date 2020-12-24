package org.j4work.domain.base.repos;

/**
 * Runtime exception thrown when a repo lookup method doesn't yield results.
 */
public class EntityNotFoundException extends RuntimeException {

    final private Class<?> repoClass;

    final private Object searchIdentifier;

    public EntityNotFoundException(
        String message, Class<?> repoClass, Object searchIdentifier
    ) {
        super(message);
        this.repoClass = repoClass;
        this.searchIdentifier = searchIdentifier;
    }

    public Class<?> getRepoClass() {
        return repoClass;
    }

    public Object getSearchIdentifier() {
        return searchIdentifier;
    }
}
