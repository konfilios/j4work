package org.j4work.domain.base.entities;

import java.io.Serializable;

/**
 * Standard named entity implementation with auto generated id.
 */
abstract public class StdUuNamedEntity<ID extends Serializable> extends StdUuEntity<ID> implements Named
{
    protected String name;

    @Override
    public String getName()
    {
        return name;
    }
}
