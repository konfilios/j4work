package org.j4work.domain.l10n.entities;

import org.j4work.domain.base.entities.RefNamedEntity;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;

/**
 *
 */
@Entity
public class L10nTimezone extends RefNamedEntity<Short>
{
    @NotEmpty
    String code;

    public String getCode()
    {
        return code;
    }
}
