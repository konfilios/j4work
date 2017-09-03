package org.j4work.enums.test.assertions;

import org.assertj.core.api.AbstractAssert;

/**
 * Assertions for an SerialEnum class.
 */
public class ConvertibleEnumClassAssert<E extends Enum<E>>
    extends AbstractAssert<ConvertibleEnumClassAssert<E>, Class<E>> {

    private ConvertibleEnumClassAssert(Class<E> actual) {
        super(actual, ConvertibleEnumClassAssert.class);
    }

    public static <E extends Enum<E>>
    ConvertibleEnumClassAssert assertThat(Class<E> actual) {
        return new ConvertibleEnumClassAssert<E>(actual);
    }

    public ConvertibleEnumClassAssert<E> respectsContract() {
        isNotNull();

        forceClassInitialization();

        return this;
    }


    /**
     * Force initialization of enum class.
     * <p>
     * SerialEnumConverter instances are declared as static variables on their corresponding enum class.
     * <p>
     * In order for these instances to be actually created, a simple reference to the .class object
     * of the enum class is not enough. We need to force the JVM to initialize the class, i.e. run
     * all static initialization blocks. Class.forName() is known to force such initialization.
     */
    private void forceClassInitialization() {
        try {
            Class.forName(actual.getName());
        } catch (ClassNotFoundException e) {
            failWithMessage("Could not force initialization of class "
                + actual + ": " + e.getMessage());
        }
    }

}
