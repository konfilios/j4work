package org.j4work.domain.base.entities;

/**
 * Uniquely identifiable using a "lukey", short for "look-up key".
 *
 * Lukeys only contain safe characters so they can be mapped
 * to language constructs such as enums, constant names, etc.
 */
public interface LukeyIdentifiable {

    /**
     * Lookup key.
     */
    String getLukey();
}
