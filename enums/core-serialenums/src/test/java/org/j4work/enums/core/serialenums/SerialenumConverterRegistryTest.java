package org.j4work.enums.core.serialenums;

import org.j4work.enums.core.api.*;
import org.j4work.enums.core.spi.EnumConverter;
import org.j4work.enums.core.spi.EnumConverterFactory;
import org.j4work.enums.core.serialenums.SerialEnums.SerialEnum;
import org.junit.Test;

import javax.annotation.Nonnull;

/**
 * .
 */
public class SerialenumConverterRegistryTest {

    /**
     * If the ConverterFactory generic parameter of Serialenum is an interface, then the registry won't be able
     * to instantiate a ConverterFactory.
     */
    static public class EnumWithInterfaceFactoryTest {

        @Test(expected = Exception.class)
        public void test() {
        }

        enum EnumWithInterfaceFactoryType implements SerialEnum<EnumConverterFactory> {

        }
    }

    /**
     * If the converter factory creates null converters then the registry won't be able to
     * produce converters.
     */
    static public class EnumWithNullProducingFactoryTest {

        @Test(expected = Exception.class)
        public void test() {
        }

        enum EnumWithNullProducingFactory implements SerialEnum<NullProducingFactory> {

        }

        class NullProducingFactory implements EnumConverterFactory {

            @Nonnull
            @Override
            public <E extends Enum<E>> EnumConverter<E> getConverter(
                @Nonnull Class<E> enumClass
            ) {
                return null;
            }
        }
    }

    /**
     * EnumWithoutFactory does not specify its corresponding ConverterFactory type as generic parameter to Serialenum
     * and can thus not be processed by the registry.
     */
    static public class SerialEnumTypesMustSpecifyFactoryTypeTest {

        @Test(expected = Exception.class)
        public void test() {
        }

        enum EnumWithoutFactory implements SerialEnumWithoutFactory {

        }

        interface SerialEnumWithoutFactory extends SerialEnum {

        }
    }
}