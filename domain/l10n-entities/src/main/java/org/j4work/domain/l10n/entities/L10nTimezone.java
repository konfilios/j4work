package org.j4work.domain.l10n.entities;

import org.j4work.domain.base.entities.RefNamedEntity;

import javax.validation.constraints.NotEmpty;

/**
 *
 */
public class L10nTimezone extends RefNamedEntity<Short>
{
    @NotEmpty
    private String code;

    public String getCode()
    {
        return code;
    }
}
