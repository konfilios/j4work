package org.j4work.domain.base.entities;

import java.io.Serializable;
import java.util.UUID;

/**
 * Named and UUID identifiable implementation with auto generated id.
 *
 * @see <a href="/org/j4work/domain/base/jpa/orm.xml">orm.xml</a>
 */
abstract public class AutoIdUuNamedEntity<ID extends Serializable>
    extends AutoIdUuEntity<ID>
    implements Named {

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
