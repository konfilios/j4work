package org.j4work.enums.core.spi.converters;

/**
 * .
 */
public class NumberUtils {

    public static final Class[] INTEGRAL_NUMBER_TYPES = new Class[]{
        Byte.class,
        Short.class,
        Integer.class,
        Long.class
    };

    @SuppressWarnings("unchecked")
    public static <T> T convertNumber(Number number, Class<T> targetClass) {
        if (Integer.class == targetClass) {
            return (T) ((Integer) number.intValue());

        } else if (Byte.class == targetClass) {
            return (T) ((Byte) number.byteValue());

        } else if (Short.class == targetClass) {
            return (T) ((Short) number.shortValue());

        } else if (Long.class == targetClass) {
            return (T) ((Long) number.longValue());

        } else {
            return null;
        }

    }
}
