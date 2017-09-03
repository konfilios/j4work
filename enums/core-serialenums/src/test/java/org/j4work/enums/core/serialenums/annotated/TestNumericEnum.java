package org.j4work.enums.core.serialenums.annotated;


import org.j4work.enums.core.serialenums.annotated.IntIdAnnotatedEnum.IntId;

/**
 * .
 */
enum TestNumericEnum {
    @IntId(-1)
    CONST_MINUS_ONE,

    @IntId(0)
    CONST_ZERO,

    @IntId(1)
    CONST_ONE,

    @IntId(Integer.MIN_VALUE)
    CONST_MIN_VALUE,

    @IntId(Integer.MAX_VALUE)
    CONST_MAX_VALUE
}
