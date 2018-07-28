package org.j4work.domain.base.entities;

import java.io.Serializable;

/**
 * Entity with auto generated id.
 *
 * @see <a href="/org/j4work/domain/base/jpa/orm.xml">orm.xml</a>
 */
abstract public class AutoIdEntity<ID extends Serializable>
    extends AbstractEntity<ID> {

    private ID id;

    /**
     * Hibernate no-arg constructor.
     */
    protected AutoIdEntity() {
    }

    @Override
    public ID getId() {
        return id;
    }
}
