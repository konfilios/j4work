package org.j4work.domain.base.entities.jpa;

import org.j4work.domain.base.entities.NamedIdLukeyIdentifiable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Reference entity implementation with manually assigned id and lookup key.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
abstract public class RefEntity<ID extends Serializable>
    extends ManualIdNamedEntity<ID>
    implements NamedIdLukeyIdentifiable<ID> {

    @Column(nullable = false)
    private String lukey;

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

    @Override
    public String toString() {
        return getLukey() + '[' + getId() + ']';
    }
}
