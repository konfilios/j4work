package org.j4work.domain.base.entities;

import java.io.Serializable;
import java.util.UUID;

/**
 * Named and UUID identifiable implementation with manually assigned id.
 *
 * @see <a href="/org/j4work/domain/base/jpa/orm.xml">orm.xml</a>
 */
abstract public class ManualIdUuNamedEntity<ID extends Serializable>
    extends ManualIdUuEntity<ID>
    implements Named {

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
