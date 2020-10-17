package org.j4work.domain.base.entities;

import java.io.Serializable;

/**
 * Identifiable by Lukey and by ID.
 */
public interface IdLukeyIdentifiable<ID extends Serializable>
    extends Identifiable<ID>, LukeyIdentifiable {

}
