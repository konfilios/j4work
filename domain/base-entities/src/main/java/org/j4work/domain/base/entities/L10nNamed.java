package org.j4work.domain.base.entities;

/**
 * An entity with localized human-readable names.
 */
public interface L10nNamed extends Named {

    /**
     * Name in given language code.
     */
    String getName(String languageId);

}
