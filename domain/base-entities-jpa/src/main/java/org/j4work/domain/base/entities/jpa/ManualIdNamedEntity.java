package org.j4work.domain.base.entities.jpa;

import org.j4work.domain.base.entities.NamedIdentifiable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Named identifable entity with manually assigned id.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
abstract public class ManualIdNamedEntity<ID extends Serializable>
    extends ManualIdEntity<ID>
    implements NamedIdentifiable<ID> {

    @Column(nullable = false)
    private String name;

    /**
     * Hibernate no-arg constructor.
     */
    protected ManualIdNamedEntity() {
    }

    public ManualIdNamedEntity(ID id, String name) {
        super(id);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + " [" + getId() + ']';
    }
}
