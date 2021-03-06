package org.j4work.domain.base.repos;

/**
 * Runtime exception thrown when a repo lookup method doesn't yield results.
 */
public class EntityNotFoundException extends RuntimeException {

    final private String entityType;

    final private Object searchIdentifier;

    public EntityNotFoundException(
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

    static public EntityNotFoundException create(
        Object repo,
        String identifierType,
        Object identifierValue
    ) {
        final String entityType = EntityTypeResolver.resolveEntityType(repo);

        return new EntityNotFoundException(
            "Could not find " + entityType + " with " + identifierType + " '" + identifierValue + "'",
            entityType, identifierValue
        );
    }

}
