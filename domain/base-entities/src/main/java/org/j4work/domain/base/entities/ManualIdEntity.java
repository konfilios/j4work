package org.j4work.domain.base.entities;

import java.io.Serializable;

/**
 * Entity with manually assigned id.
 *
 * @see <a href="/org/j4work/domain/base/jpa/orm.xml">orm.xml</a>
 */
abstract public class ManualIdEntity<ID extends Serializable>
    extends AbstractEntity<ID> {

    private ID id;

    /**
     * Hibernate no-arg constructor.
     */
    protected ManualIdEntity() {
    }

    /**
     * Use this constructor when is is known.
     */
    public ManualIdEntity(ID id) {
        this.id = id;
    }

    @Override
    public ID getId() {
        return id;
    }
}
