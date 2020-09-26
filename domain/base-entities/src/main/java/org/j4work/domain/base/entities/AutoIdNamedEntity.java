package org.j4work.domain.base.entities;

import java.io.Serializable;

/**
 * Named entity with auto generated id.
 *
 * @see <a href="/org/j4work/domain/base/jpa/orm.xml">orm.xml</a>
 */
abstract public class AutoIdNamedEntity<ID extends Serializable>
    extends AutoIdEntity<ID>
    implements Named {

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
