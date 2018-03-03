package org.j4work.domain.base.repos;

import org.j4work.domain.base.entities.RefEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;
import java.util.Optional;

/**
 *
 */
public interface RefEntityRepo<E extends RefEntity<ID>, ID extends Serializable>
    extends PagingAndSortingRepository<E, ID>
{
    Optional<E> findByLukey(String lukey);
}
