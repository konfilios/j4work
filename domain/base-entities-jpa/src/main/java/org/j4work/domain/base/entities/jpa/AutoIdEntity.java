package org.j4work.domain.base.entities.jpa;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Identifiable entity with auto generated id.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
abstract public class AutoIdEntity<ID extends Serializable>
    extends AbstractEntity<ID> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ID id;

    /**
     * Hibernate no-arg constructor.
     */
    protected AutoIdEntity() {
    }

    /**
     * Special case for records with hand-picked ids.
     */
    protected AutoIdEntity(ID id) {
        this.id = id;
    }

    @Override
    public ID getId() {
        return id;
    }
}
