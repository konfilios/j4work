package org.j4work.domain.base.repos;

import org.j4work.domain.base.entities.jpa.RefEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;

/**
 * A {@link LukeyIdentifiableRepository} with {@link PagingAndSortingRepository} functionality.
 */
public interface RefEntityRepository
    <E extends RefEntity<ID>, ID extends Serializable>
    extends LukeyIdentifiableRepository<E, ID>, PagingAndSortingRepository<E, ID> {

}
