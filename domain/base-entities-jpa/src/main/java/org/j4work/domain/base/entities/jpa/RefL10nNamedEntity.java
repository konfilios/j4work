package org.j4work.domain.base.entities.jpa;

import org.j4work.domain.base.entities.L10nNamedIdLukeyIdentifiable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Map;

/**
 * Standard implementation of entity with i18n names.
 * <p>
 * Default name equals the name corresponding to the default language.
 * <p>
 * TODO: Fix names map.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
abstract public class RefL10nNamedEntity<ID extends Serializable>
    extends AutoIdEntity<ID>
    implements L10nNamedIdLukeyIdentifiable<ID> {

    /**
     * Map of names per language code.
     */
    private Map<String, String> names;

    @Override
    public String getName(String languageId) {
        return names.get(languageId);
    }

    @Override
    public String toString() {
        return this.getClass() + "{" +
            "name=" + getName() +
            '}';
    }
}
