package org.j4work.domain.base.repos;

/**
 * Runtime exception thrown when a repo lookup method doesn't yield results.
 */
public class EntityAlreadyExistsException extends RuntimeException {

    final private String entityType;

    final private Object searchIdentifier;

    public EntityAlreadyExistsException(
        String message, String entityClass, Object searchIdentifier
    ) {
        super(message);
        this.entityType = entityClass;
        this.searchIdentifier = searchIdentifier;
    }

    public String getEntityType() {
        return entityType;
    }

    public Object getSearchIdentifier() {
        return searchIdentifier;
    }

    static public EntityAlreadyExistsException create(
        Object repo,
        String identifierType,
        Object identifierValue
    ) {
        final String entityType = EntityTypeResolver.resolveEntityType(repo);

        return new EntityAlreadyExistsException(
            entityType + " with " + identifierType + " '" + identifierValue + "' already exists",
            entityType, identifierValue
        );
    }

}
