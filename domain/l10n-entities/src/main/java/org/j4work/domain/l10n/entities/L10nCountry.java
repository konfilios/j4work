package org.j4work.domain.l10n.entities;


import org.j4work.domain.base.entities.RefNamedEntity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * http://data.okfn.org/data/core/language-codes
 */
@Entity
@AttributeOverride(name = "id", column = @Column(columnDefinition = "char"))
public class L10nCountry extends RefNamedEntity<String>
{
    @NotNull
    @ManyToOne
    private L10nContinent continent;

    public L10nContinent getContinent()
    {
        return continent;
    }
}
