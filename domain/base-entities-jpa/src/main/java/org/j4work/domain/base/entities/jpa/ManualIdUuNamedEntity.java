package org.j4work.domain.base.entities.jpa;

import org.j4work.domain.base.entities.NamedIdUuidIdentifiable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.UUID;

/**
 * Named and UUID identifiable implementation with manually assigned id.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
abstract public class ManualIdUuNamedEntity<ID extends Serializable>
    extends ManualIdUuEntity<ID>
    implements NamedIdUuidIdentifiable<ID> {

    @Column(nullable = false)
    private String name;

    /**
     * Hibernate no-arg constructor.
     */
    protected ManualIdUuNamedEntity() {
    }

    public ManualIdUuNamedEntity(ID id, UUID uuid, String name) {
        super(id, uuid);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
