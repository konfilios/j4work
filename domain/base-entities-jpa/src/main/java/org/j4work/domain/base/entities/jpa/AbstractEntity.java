package org.j4work.domain.base.entities.jpa;


import org.j4work.domain.base.entities.Identifiable;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Abstract identifiable entity implementing hash code and equality.
 */
@MappedSuperclass
abstract public class AbstractEntity<ID extends Serializable>
    implements Identifiable<ID> {

    @Override
    public int hashCode() {
        final ID id = getId();
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        final ID id = getId();
        if (o == null || id == null || getClass() != o.getClass()) {
            return false;
        } else {
            return id.equals(((Identifiable) o).getId());
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + getId() + ']';
    }

}
