package org.j4work.domain.base.entities.jpa;

import org.j4work.domain.base.entities.NamedIdUuidIdentifiable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.UUID;

/**
 * Named and UUID identifiable implementation with auto generated id.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
abstract public class AutoIdUuNamedEntity<ID extends Serializable>
    extends AutoIdUuEntity<ID>
    implements NamedIdUuidIdentifiable<ID> {

    @Column(nullable = false)
    private String name;

    /**
     * Hibernate no-arg constructor.
     */
    protected AutoIdUuNamedEntity() {
    }

    /**
     * Special case for records with hand-picked ids.
     */
    protected AutoIdUuNamedEntity(ID id, UUID uuid, String name) {
        super(id, uuid);
        this.name = name;
    }

    public AutoIdUuNamedEntity(UUID uuid, String name) {
        super(uuid);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
