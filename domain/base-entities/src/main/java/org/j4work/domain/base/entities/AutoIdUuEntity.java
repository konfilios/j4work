package org.j4work.domain.base.entities;

import java.io.Serializable;
import java.util.UUID;

/**
 * UUID identifiable entity with auto generated id.
 *
 * @see <a href="/org/j4work/domain/base/jpa/orm.xml">orm.xml</a>
 */
abstract public class AutoIdUuEntity<ID extends Serializable>
    extends AutoIdEntity<ID>
    implements UuIdentifiable {

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
