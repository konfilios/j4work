package org.j4work.java8.time;

import org.junit.Test;

import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;

import static org.assertj.core.api.Assertions.assertThat;

public class TemporalProgressionTest {

    @Test
    public void testUpToLocalDate() {
        final TemporalProgression<LocalDate> progression = TemporalProgression.upTo(
            LocalDate.of(2015, 2, 27),
            LocalDate.of(2015, 3, 1),
            Period.ofDays(1)
        );

        assertThat(progression)
            .containsExactlyInAnyOrder(
                LocalDate.of(2015, 2, 27),
                LocalDate.of(2015, 2, 28),
                LocalDate.of(2015, 3, 1)
            );
    }

    @Test
    public void testDownToLocalDate() {
        final TemporalProgression<LocalDate> progression = TemporalProgression.downTo(
            LocalDate.of(2015, 3, 1),
            LocalDate.of(2015, 2, 27),
            Period.ofDays(1)
        );

        assertThat(progression)
            .containsExactlyInAnyOrder(
                LocalDate.of(2015, 3, 1),
                LocalDate.of(2015, 2, 28),
                LocalDate.of(2015, 2, 27)
            );
    }

    @Test
    public void testUpToYearMonth() {
        final TemporalProgression<YearMonth> progression = TemporalProgression.upTo(
            YearMonth.of(2015, 12),
            YearMonth.of(2016, 1),
            Period.ofMonths(1)
        );

        assertThat(progression)
            .containsExactlyInAnyOrder(
                YearMonth.of(2015, 12),
                YearMonth.of(2016, 1)
            );

    }

    @Test
    public void testDownToYearMonth() {
        final TemporalProgression<YearMonth> progression = TemporalProgression.downTo(
            YearMonth.of(2016, 1),
            YearMonth.of(2015, 12),
            Period.ofMonths(1)
        );

        assertThat(progression)
            .containsExactlyInAnyOrder(
                YearMonth.of(2016, 1),
                YearMonth.of(2015, 12)
            );

    }

}