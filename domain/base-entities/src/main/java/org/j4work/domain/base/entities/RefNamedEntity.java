package org.j4work.domain.base.entities;

import java.io.Serializable;

/**
 * Named reference entity implementation with manually assigned id.
 */
abstract public class RefNamedEntity<ID extends Serializable> extends RefEntity<ID> implements Named
{
    protected String name;

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public String toString() {
        return this.getClass() + "{" +
            "name=" + getName() +
            '}';
    }
}
