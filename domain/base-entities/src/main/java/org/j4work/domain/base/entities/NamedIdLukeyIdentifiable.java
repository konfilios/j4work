package org.j4work.domain.base.entities;

import java.io.Serializable;

/**
 * Named and identifiable by Lukey and by ID.
 */
public interface NamedIdLukeyIdentifiable<ID extends Serializable>
    extends NamedIdentifiable<ID>, IdLukeyIdentifiable<ID> {

}
