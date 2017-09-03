package org.j4work.domain.base.entities;

import java.io.Serializable;

/**
 * Reference entity implementation with manually assigned id.
 */
abstract public class RefEntity<ID extends Serializable> extends AbstractEntity<ID>
{
    protected ID id;

    @Override
    public ID getId()
    {
        return id;
    }
}
