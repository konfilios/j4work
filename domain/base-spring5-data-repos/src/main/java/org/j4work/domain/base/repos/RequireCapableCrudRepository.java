package org.j4work.domain.base.repos;

import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;

/**
 * Add requireById functionality.
 */
public interface RequireCapableCrudRepository<E, ID extends Serializable>
    extends CrudRepository<E, ID> {
    /**
     * Lookup by ID. Return result or throw exception.
     */
    default E requireById(ID id) {
        return findById(id)
            .orElseThrow(() -> EntityNotFoundException.create(this, "id", id));
    }
}
