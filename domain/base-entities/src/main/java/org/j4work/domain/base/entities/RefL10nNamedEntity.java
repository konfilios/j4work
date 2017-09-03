package org.j4work.domain.base.entities;

import java.io.Serializable;
import java.util.Map;

/**
 * Standard implementation of entity with i18n names.
 *
 * Default name equals the name corresponding to the default language.
 */
abstract public class RefL10nNamedEntity<ID extends Serializable> extends StdEntity<ID> implements L10nNamed {

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
