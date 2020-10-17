package org.j4work.enums.core.serialenums;

import org.j4work.classutils.core.GenericTypes;
import org.j4work.enums.core.api.Enums;
import org.j4work.enums.core.spi.EnumConverter;
import org.j4work.enums.core.spi.EnumConverterFactory;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * .
 */
public abstract class SerialEnums {

    static {
        Enums.SERVICE.registerConverterFactory(new SerialEnumAnnotatedEnumConverterFactory());
    }

    /**
     * Marker interface for Enum classes which can be serialized using the SerialEnums Registry.
     * <p>
     * The ACF generic parameter allows lazy auto-registration of converters for a given enum.
     *
     * @todo Elaborate on auto-registration
     */
    public interface SerialEnum<ACF extends EnumConverterFactory> {

    }

    /**
     * Delegates EnumConverter creation to EnumConverterFactory instances declared as generic
     * parameters to the SerialEnum marker interface.
     * <p>
     * Let's take the following class as an example:
     * <p>
     * <pre>{@code
     * enum MyEnum implements SerialEnum<SomeOtherFactory> {}
     * }</pre>
     * <p>
     * This factory would then create a SomeOtherFactory instance (using a no-arg constructor)
     * and call its createConverter() method.
     */
    static class SerialEnumAnnotatedEnumConverterFactory implements EnumConverterFactory {

        @Nonnull
        private <E extends Enum<E>> EnumConverterFactory createConverterFactoryFromGeneric(
            @Nonnull Class<E> enumClass
        ) {
            final Class<? extends EnumConverterFactory> factoryClass =
                getConverterFactoryClassFromGeneric(enumClass);

            try {
                return factoryClass.newInstance();
            } catch (Exception e) {
                throw new IllegalStateException("Could not instantiate " + factoryClass + " for "
                    + enumClass + ": " + e.getMessage(), e);
            }
        }

        @SuppressWarnings("unchecked")
        private <E extends Enum<E>>
        Class<? extends EnumConverterFactory> getConverterFactoryClassFromGeneric(
            @Nonnull Class<E> enumClass
        ) {
            final List<Class<?>> typeArgs = GenericTypes.getActualTypeArguments((Class) enumClass,
                SerialEnum.class);

            if (typeArgs.isEmpty()) {
                throw new IllegalArgumentException(
                    "Enum class " + enumClass + " implements " + SerialEnum.class
                        + " but without an argument for the EnumConverterFactory generic parameter");
            }

            final Class<?> firstTypeArg = typeArgs.get(0);

            if (!EnumConverterFactory.class.isAssignableFrom(firstTypeArg)) {
                throw new IllegalStateException(
                    "The generic argument of SerialEnum interface implemented by " +
                        enumClass + " must be a subtype of " + EnumConverterFactory.class);
            }

            return (Class<? extends EnumConverterFactory>) firstTypeArg;
        }

        @Override
        public <E extends Enum<E>> EnumConverter<E> getConverter(@Nonnull Class<E> enumClass) {
            if (SerialEnum.class.isAssignableFrom(enumClass)) {
                return createConverterFactoryFromGeneric(enumClass).getConverter(enumClass);
            }

            return null;
        }
    }
}
