package org.j4work.domain.base.repos;

import org.j4work.domain.base.entities.LukeyIdentifiable;
import org.springframework.data.repository.Repository;

import java.io.Serializable;
import java.util.Optional;

/**
 * Adds {@link #findByLukey} query method for {@link LukeyIdentifiable} entities.
 */
public interface LukeyIdentifiableRepository
    <E extends LukeyIdentifiable, ID extends Serializable>
    extends Repository<E, ID> {

    /**
     * Find entity by lookup key.
     */
    Optional<E> findByLukey(String lukey);

    /**
     * Lookup by lukey. Return result or throw exception.
     */
    default E requireByLukey(String lukey) {
        return findByLukey(lukey)
            .orElseThrow(() -> new EntityNotFoundException(
                "Could not find " + getClass().getSimpleName() + " with lookup-key '" + lukey + "'",
                getClass(),
                lukey
            ));
    }
}
