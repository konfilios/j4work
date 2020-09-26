package org.j4work.domain.base.entities;

import java.io.Serializable;

/**
 * Reference entity implementation with manually assigned id and lukey.
 */
abstract public class RefEntity<ID extends Serializable>
    extends ManualIdNamedEntity<ID>
    implements LukeyIdentifiable {

    protected String lukey;

    public RefEntity() {
    }

    public RefEntity(ID id, String name, String lukey) {
        super(id, name);
        this.lukey = lukey;
    }

    @Override
    public String getLukey() {
        return lukey;
    }
}
