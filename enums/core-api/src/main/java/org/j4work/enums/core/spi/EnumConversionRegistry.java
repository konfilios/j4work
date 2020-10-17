package org.j4work.enums.core.spi;

import javax.annotation.Nonnull;

/**
 * .
 */
public interface EnumConversionRegistry {

    void registerConverterFactory(@Nonnull EnumConverterFactory converterFactory);
}
