package org.j4work.domain.base.entities;

import java.io.Serializable;

/**
 * Identifiable by UUID and by ID.
 */
public interface NamedIdUuidIdentifiable<ID extends Serializable>
    extends IdUuidIdentifiable<ID>, NamedIdentifiable<ID> {

}
