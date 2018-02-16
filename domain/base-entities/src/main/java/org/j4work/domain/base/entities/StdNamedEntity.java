package org.j4work.domain.base.entities;

import java.io.Serializable;

/**
 * Standard named entity implementation with auto generated id.
 */
//@MappedSuperclass
abstract public class StdNamedEntity<ID extends Serializable> extends StdEntity<ID> implements Named
{
    protected String name;

    @Override
    public String getName()
    {
        return name;
    }

    public StdNamedEntity() {
    }

    public StdNamedEntity(String name) {
        this.name = name;
    }
}
