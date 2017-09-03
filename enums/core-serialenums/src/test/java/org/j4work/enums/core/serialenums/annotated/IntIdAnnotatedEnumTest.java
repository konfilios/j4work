package org.j4work.enums.core.serialenums.annotated;

import org.j4work.enums.core.api.Enums;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.j4work.enums.core.serialenums.annotated.IntIdAnnotatedEnumTest.MyEnum.VALUE_A;
import static org.j4work.enums.test.assertions.ConvertibleEnumClassAssert.assertThat;

/**
 * .
 */
public class IntIdAnnotatedEnumTest {

    @Test
    public void enum_respects_contract() {
        assertThat(MyEnum.class)
            .respectsContract();
    }

    @Test
    public void lookup_by_existing_valid_integer_id() {
        assertThat(Enums.valueOf(MyEnum.class, 10))
            .isEqualTo(VALUE_A);
    }

    @Test
    public void lookup_by_existing_valid_string_id() {
        assertThat(Enums.valueOf(MyEnum.class, "10"))
            .isEqualTo(VALUE_A);
    }

    @Test
    public void lookup_by_nonexisting_integer_id() {
        assertThat(Enums.valueOf(MyEnum.class, 99999))
            .isNull();
    }

    @Test
    public void lookup_by_nonexisting_string_id() {
        assertThat(Enums.valueOf(MyEnum.class, "99999"))
            .isNull();
    }

    @Test
    public void lookup_by_malformed_string_id() {
        assertThat(Enums.valueOf(MyEnum.class, "malformed"))
            .isNull();
    }

    /**
     * Test enum.
     */
    enum MyEnum implements IntIdAnnotatedEnum {
        @IntId(10)
        VALUE_A,
        @IntId(20)
        VALUE_B;
    }

}