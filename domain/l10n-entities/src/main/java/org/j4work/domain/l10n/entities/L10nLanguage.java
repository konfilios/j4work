package org.j4work.domain.l10n.entities;


import org.j4work.domain.base.entities.jpa.RefEntity;

import javax.persistence.*;

/**
 * Languages.
 */
@Entity
@Access(AccessType.FIELD)
@AttributeOverride(name = "id", column = @Column(columnDefinition = "char"))
public class L10nLanguage extends RefEntity<String> {

}
