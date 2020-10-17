package org.j4work.domain.base.entities;

import java.io.Serializable;

/**
 * Identifiable by UUID and by ID.
 */
public interface IdUuidIdentifiable<ID extends Serializable>
    extends Identifiable<ID>, UuIdentifiable {

}
