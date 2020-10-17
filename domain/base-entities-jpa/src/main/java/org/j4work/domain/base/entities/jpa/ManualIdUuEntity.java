package org.j4work.domain.base.entities.jpa;

import org.j4work.domain.base.entities.IdUuidIdentifiable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.UUID;

/**
 * UUID identifiable entity with manually assigned id.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
abstract public class ManualIdUuEntity<ID extends Serializable>
    extends ManualIdEntity<ID>
    implements IdUuidIdentifiable<ID> {

    @Column(nullable = false)
    private UUID uuid;

    /**
     * Hibernate no-arg constructor.
     */
    protected ManualIdUuEntity() {
    }

    public ManualIdUuEntity(ID id, UUID uuid) {
        super(id);
        this.uuid = uuid;
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }
}
