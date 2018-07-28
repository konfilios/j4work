package org.j4work.domain.base.entities;

import java.io.Serializable;

/**
 * Reference entity implementation with manually assigned id and lukey.
 */
abstract public class RefEntity<ID extends Serializable>
    extends ManualIdNamedEntity<ID>
    implements LukeyIdentifiable {

    protected String lukey;

    @Override
    public String getLukey() {
        return lukey;
    }
}
