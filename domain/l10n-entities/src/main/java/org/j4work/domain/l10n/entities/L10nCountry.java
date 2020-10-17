package org.j4work.domain.l10n.entities;


import org.j4work.domain.base.entities.jpa.RefEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Countries.
 *
 * http://data.okfn.org/data/core/language-codes
 */
@Entity
@Access(AccessType.FIELD)
@AttributeOverride(name = "id", column = @Column(columnDefinition = "char"))
public class L10nCountry extends RefEntity<String> {

    @ManyToOne
    @NotNull
    private L10nContinent continent;

    public L10nContinent getContinent() {
        return continent;
    }
}
