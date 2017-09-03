package org.j4work.datastractures.twowayindex.impl;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Normalizes string values by removing all non-alphanumeric characters and converting to lowercase.
 */
public class SlugBiMapIndex<K> extends BiMapIndex<K, String> {

    private static final Pattern NOT_ALPHANUMERIC = Pattern.compile("[^A-Za-z0-9]");

    public SlugBiMapIndex(Map<K, String> values) {
        super(values);
    }

    @Nonnull
    @Override
    protected String normalizeSearchValue(@Nonnull String searchValue) {
        return NOT_ALPHANUMERIC.matcher(searchValue).replaceAll("").toLowerCase();
    }
}
