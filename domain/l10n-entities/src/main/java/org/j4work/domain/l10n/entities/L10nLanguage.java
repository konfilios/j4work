package org.j4work.domain.l10n.entities;


import org.j4work.domain.base.entities.RefNamedEntity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;

/**
 *
 */
@Entity
@AttributeOverride(name = "id", column = @Column(columnDefinition = "char"))
public class L10nLanguage extends RefNamedEntity<String>
{
}
