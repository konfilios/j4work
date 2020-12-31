package org.j4work.kotlin.utils.string

import org.assertj.core.api.Assertions.assertThat
import org.j4work.kotlin.utils.greek.GreeklishUtils.toGreeklish
import org.junit.jupiter.api.Test

class GreeklishUtilsTest {

    @Test
    fun `multiple greek 'i' should be converted`() {
        assertThat(toGreeklish("Κίννηση"))
            .isEqualTo("kinisi")

    }

}