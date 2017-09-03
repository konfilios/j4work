package org.j4work.enums.core.serialenums.annotated;

import org.j4work.datastractures.twowayindex.impl.BiMapIndex;
import org.j4work.enums.core.serialenums.SerialEnums;
import org.j4work.enums.core.spi.EnumConverterFactory;
import org.j4work.enums.core.spi.EnumConverter;
import org.j4work.enums.core.converters.IntegerOnlyIndexedEnumConverter;
import org.j4work.classutils.core.AnnotatedEnums;
import org.j4work.classutils.core.BiFunction;

import javax.annotation.Nonnull;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * SerializableEnum whose constants are annotated with a numeric @Id annotation.
 */
public interface IntIdAnnotatedEnum extends SerialEnums.SerialEnum<IntIdAnnotatedEnum.ConverterFactory> {

    /**
     * .
     */
    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface IntId {

        int value();
    }


    /**
     * Creates IntegerSerializableEnumConverter instances from enum constants annotated with @IntId.
     */
    class ConverterFactory implements EnumConverterFactory {

        @Nonnull
        @Override
        public <E extends Enum<E>> EnumConverter<E> getConverter(
            @Nonnull Class<E> enumClass
        ) {
            return new IntegerOnlyIndexedEnumConverter<E>(
                new BiMapIndex<E, Integer>(
                    AnnotatedEnums.createMap(
                        enumClass,
                        IntId.class,
                        new IntIdValueMapper<E>()
                    )
                )
            );
        }

        /**
         * Maps an enum constant annotated with @IntId to the integer value of the @IntId annotation.
         *
         * @param <E> Type of enum constants.
         */
        private class IntIdValueMapper<E extends Enum<E>> implements BiFunction<E, IntId, Integer> {

            @Override
            public Integer apply(E e, IntId intId) {
                return intId.value();
            }
        }
    }
}
