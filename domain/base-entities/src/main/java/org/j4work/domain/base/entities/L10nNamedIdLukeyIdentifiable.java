package org.j4work.domain.base.entities;

import java.io.Serializable;

/**
 * A lukey identifiable entity with localized human-readable names.
 */
public interface L10nNamedIdLukeyIdentifiable<ID extends Serializable>
    extends IdLukeyIdentifiable<ID>, L10nNamed {

}
