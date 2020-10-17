package org.j4work.domain.base.entities;

import java.io.Serializable;

/**
 * Named and identifiable by ID.
 */
public interface NamedIdentifiable<ID extends Serializable>
    extends Identifiable<ID>, Named {

}
