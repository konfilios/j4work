package org.j4work.domain.base.entities;

import java.io.Serializable;

/**
 * Reference entity implementation with manually assigned id and lukey.
 */
abstract public class RefEntity<ID extends Serializable> extends AbstractEntity<ID>
    implements LukeyIdentifiable
{
    protected ID id;

    protected String lukey;

    @Override
    public ID getId()
    {
        return id;
    }

    @Override
    public String getLukey() {
        return lukey;
    }
}
