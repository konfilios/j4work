package org.j4work.kotlin.extjava.java.time

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.time.YearMonth

class YearMonthExtKtTest {
    val YM_2015_2 = YearMonth.of(2015, 2)
    val YM_2015_3 = YearMonth.of(2015, 3)
    val YM_2015_4 = YearMonth.of(2015, 4)

    @Test(expected = IllegalArgumentException::class)
    fun `step 0 is not allowed`() {
        YM_2015_2.upTo(YM_2015_2, 0)
    }

    @Test
    fun `when start=end we get a singleton no matter the step`() {
        assertThat(YM_2015_2.upTo(YM_2015_2, -1).toList())
            .containsExactly(YM_2015_2)

        assertThat(YM_2015_2.upTo(YM_2015_2).toList())
            .containsExactly(YM_2015_2)

        assertThat(YM_2015_2.upTo(YM_2015_2, 2).toList())
            .containsExactly(YM_2015_2)
    }

    @Test
    fun `when start=start+1 with step=1 we get two elements`() {
        assertThat(YM_2015_2.upTo(YM_2015_3).toList())
            .containsExactly(YM_2015_2, YM_2015_3)
    }

    @Test
    fun `start and end are further apart, middle elements should be there`() {
        assertThat(YM_2015_2.upTo(YM_2015_4).toList())
            .containsExactly(YM_2015_2, YM_2015_3, YM_2015_4)
    }

    @Test
    fun `start+step exceeds the end THUS end is not in the results`() {
        assertThat(YM_2015_2.upTo(YM_2015_3, 2).toList())
            .containsExactly(YM_2015_2)
    }

    @Test
    fun `start+step includes the end`() {
        assertThat(YM_2015_2.upTo(YM_2015_4, 2).toList())
            .containsExactly(YM_2015_2, YM_2015_4)
    }

    @Test
    fun `start smaller than end but step is negative`() {
        assertThat(YM_2015_2.upTo(YM_2015_4, -1).toList())
            .isEmpty()
    }
}