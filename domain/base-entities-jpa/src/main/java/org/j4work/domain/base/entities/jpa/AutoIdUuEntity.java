package org.j4work.domain.base.entities.jpa;

import org.j4work.domain.base.entities.IdUuidIdentifiable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.UUID;

/**
 * UUID identifiable entity with auto generated id.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
abstract public class AutoIdUuEntity<ID extends Serializable>
    extends AutoIdEntity<ID>
    implements IdUuidIdentifiable<ID> {

    @Column(nullable = false)
    private UUID uuid;

    /**
     * Hibernate no-arg constructor.
     */
    protected AutoIdUuEntity() {
    }

    /**
     * Special case for records with hand-picked ids.
     */
    protected AutoIdUuEntity(ID id, UUID uuid) {
        super(id);
        this.uuid = uuid;
    }

    public AutoIdUuEntity(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }
}
