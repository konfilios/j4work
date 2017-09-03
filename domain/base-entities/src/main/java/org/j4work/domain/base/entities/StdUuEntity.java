package org.j4work.domain.base.entities;

import java.io.Serializable;
import java.util.UUID;

/**
 * Standard entity which is also UuIdentifiable
 */
abstract public class StdUuEntity<ID extends Serializable> extends StdEntity<ID> implements UuIdentifiable
{
    protected UUID uuid;

    @Override
    public UUID getUuid()
    {
        return uuid;
    }
}
