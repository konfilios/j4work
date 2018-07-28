package org.j4work.domain.base.entities;

import java.io.Serializable;
import java.util.UUID;

/**
 * UUID identifiable entity with manually assigned id.
 *
 * @see <a href="/org/j4work/domain/base/jpa/orm.xml">orm.xml</a>
 */
abstract public class ManualIdUuEntity<ID extends Serializable>
    extends ManualIdEntity<ID>
    implements UuIdentifiable {

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
