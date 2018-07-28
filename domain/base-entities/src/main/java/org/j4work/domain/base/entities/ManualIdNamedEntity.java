package org.j4work.domain.base.entities;

import java.io.Serializable;

/**
 * Named entity with manually assigned id.
 *
 * @see <a href="/org/j4work/domain/base/jpa/orm.xml">orm.xml</a>
 */
abstract public class ManualIdNamedEntity<ID extends Serializable>
    extends ManualIdEntity<ID>
    implements Named {

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
}
