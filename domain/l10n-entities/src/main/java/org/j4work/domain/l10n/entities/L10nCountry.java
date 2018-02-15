package org.j4work.domain.l10n.entities;


import org.j4work.domain.base.entities.RefNamedEntity;

import javax.validation.constraints.NotNull;

/**
 * http://data.okfn.org/data/core/language-codes
 */
public class L10nCountry extends RefNamedEntity<String>
{
    @NotNull
    private L10nContinent continent;

    public L10nContinent getContinent()
    {
        return continent;
    }
}
