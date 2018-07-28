package org.j4work.domain.l10n.entities;


import org.j4work.domain.base.entities.RefEntity;

import javax.validation.constraints.NotNull;

/**
 * http://data.okfn.org/data/core/language-codes
 */
public class L10nCountry extends RefEntity<String> {

    @NotNull
    private L10nContinent continent;

    public L10nContinent getContinent() {
        return continent;
    }
}
