package org.j4work.domain.base.repos;

import org.j4work.domain.base.entities.Identifiable;
import org.j4work.domain.base.entities.UuIdentifiable;
import org.springframework.data.repository.Repository;

import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

/**
 * Adds {@link #findByUuid} query method for {@link UuIdentifiable} entities.
 */
public interface UuIdentifiableRepository
    <E extends UuIdentifiable & Identifiable<ID>, ID extends Serializable>
    extends Repository<E, ID> {

    /**
     * Find by uuid.
     */
    Optional<E> findByUuid(UUID uuid);

    /**
     * Lookup by uuid. Return result or throw exception.
     */
    default E requireByUuid(UUID uuid) {
        return findByUuid(uuid)
            .orElseThrow(() -> EntityNotFoundException.create(this, "UUID", uuid));
    }

    /**
     * Fail with exception if uuid already exists.
     */
    default void requireUuidDoesNotExist(UUID uuid) {
        if (findByUuid(uuid).isPresent()) {
            throw EntityAlreadyExistsException.create(this, "UUID", uuid);
        }
    }
}
