package org.j4work.domain.base.entities.jpa;

import org.j4work.domain.base.entities.NamedIdentifiable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Identifable named entity with auto generated id.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
abstract public class AutoIdNamedEntity<ID extends Serializable>
    extends AutoIdEntity<ID>
    implements NamedIdentifiable<ID> {

    @Column(nullable = false)
    private String name;

    /**
     * Hibernate no-arg constructor.
     */
    protected AutoIdNamedEntity() {
    }

    /**
     * Special case for records with hand-picked ids.
     */
    protected AutoIdNamedEntity(ID id, String name) {
        super(id);
        this.name = name;
    }

    public AutoIdNamedEntity(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
