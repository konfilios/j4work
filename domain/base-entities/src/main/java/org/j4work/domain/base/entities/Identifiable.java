package org.j4work.domain.base.entities;

import java.io.Serializable;

/**
 * Uniquely identifiable using an ID, surrogate or natural.
 *
 * @param <ID>
 */
public interface Identifiable<ID extends Serializable> extends Serializable {

    /**
     * Unique identifier.
     */
    ID getId();
}
