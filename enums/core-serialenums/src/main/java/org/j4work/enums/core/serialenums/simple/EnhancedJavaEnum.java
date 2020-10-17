package org.j4work.enums.core.serialenums.simple;

import org.j4work.datastractures.twowayindex.impl.BiMapIndex;
import org.j4work.enums.core.converters.MixedIntegerAndStringIndexedEnumConverter;
import org.j4work.enums.core.serialenums.SerialEnums;
import org.j4work.enums.core.spi.EnumConverter;
import org.j4work.enums.core.spi.EnumConverterFactory;

import javax.annotation.Nonnull;
import java.util.EnumMap;
import java.util.Map;

/**
 * .
 */
public interface EnhancedJavaEnum extends SerialEnums.SerialEnum<EnhancedJavaEnum.ConverterFactory> {

    /**
     * Creates IntegerSerializableEnumConverter instances from enum constants annotated with @IntId.
     */
    class ConverterFactory implements EnumConverterFactory {

        private static <E extends Enum<E>> Map<E, Integer> ordinalsMap(Class<E> enumClass) {
            final EnumMap<E, Integer> map = new EnumMap<E, Integer>(enumClass);

            for (E enumConstant : enumClass.getEnumConstants()) {
                map.put(enumConstant, enumConstant.ordinal());
            }

            return map;
        }

        private static <E extends Enum<E>> Map<E, String> namesMap(Class<E> enumClass) {
            final EnumMap<E, String> map = new EnumMap<E, String>(enumClass);

            for (E enumConstant : enumClass.getEnumConstants()) {
                map.put(enumConstant, enumConstant.name());
            }

            return map;
        }

        @Nonnull
        @Override
        public <E extends Enum<E>> EnumConverter<E> getConverter(@Nonnull Class<E> enumClass) {
            return new MixedIntegerAndStringIndexedEnumConverter<E>(
                new BiMapIndex<E, Integer>(ordinalsMap(enumClass)),
                new BiMapIndex<E, String>(namesMap(enumClass))
            );
        }
    }

}
