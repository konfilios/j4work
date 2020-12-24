package org.j4work.domain.base.repos;

import org.j4work.domain.base.entities.Identifiable;
import org.j4work.domain.base.entities.UuIdentifiable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;

/**
 * A {@link UuIdentifiableRepository} with {@link PagingAndSortingRepository} functionality.
 */
public interface UuIdentifiablePagingAndSortingRepository
    <E extends UuIdentifiable & Identifiable<ID>, ID extends Serializable>
    extends UuIdentifiableRepository<E, ID>,
    PagingAndSortingRepository<E, ID> {

//    Optional<E> findByUuid(UUID uuid);
}
