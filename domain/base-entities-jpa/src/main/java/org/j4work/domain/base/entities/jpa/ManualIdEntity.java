package org.j4work.domain.base.entities.jpa;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Identifiable entity with manually assigned id.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
abstract public class ManualIdEntity<ID extends Serializable>
    extends AbstractEntity<ID> {

    @Id
    private ID id;

    /**
     * Hibernate no-arg constructor.
     */
    protected ManualIdEntity() {
    }

    /**
     * Use this constructor when is is known.
     */
    public ManualIdEntity(ID id) {
        this.id = id;
    }

    @Override
    public ID getId() {
        return id;
    }
}
