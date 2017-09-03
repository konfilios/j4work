package org.j4work.domain.base.entities;

import java.io.Serializable;

public interface Identifiable<ID extends Serializable> {
    ID getId();
}
