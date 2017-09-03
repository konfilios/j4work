package org.j4work.domain.base.entities;

import java.io.Serializable;

/**
 * Standard entity implementation with auto generated id.
 */
abstract public class StdEntity<ID extends Serializable> extends AbstractEntity<ID>
{
    protected ID id;

    @Override
    public ID getId()
    {
        return id;
    }
}
