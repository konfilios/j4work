package org.j4work.java8.time;

import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.util.Iterator;

/**
 * Progression of temporal elements.
 * <p>
 * An ascending progression allows iteration over {@link Temporal}
 * objects of type {@link B} starting from {@link #start} and adding
 * {@link #step} until {@link #endInclusive} is reached (or exceeded).
 * <p>
 * Descending progressions work the same, but they subtract {@link #step}
 * instead of adding it.
 * <p>
 * For example, one can iterate through all YearMonths between March 2015
 * and May 2016 inclusive with 1-month steps, like this:
 *
 * <code>
 * for (YearMonth ym : TemporalProgression.of(YearMonth.of(3, 2015), YearMonth.of(5, 2016), Period.ofMonths(1)) {
 * // :
 * }
 * </code>
 * <p>
 * Implementation notes: The class is immutable and thread-safe.
 */
public class TemporalProgression<B extends Temporal & Comparable<? super B>>
    implements Iterable<B> {

    /**
     * Start temporal object.
     */
    final private B start;

    /**
     * End temporal object (inclusive).
     */
    final private B endInclusive;

    /**
     * Progression step.
     */
    final private TemporalAmount step;

    /**
     * Ascending progressions move from {@link #start} to {@link #endInclusive} by adding {@link #step}.
     */
    final private boolean isAscending;

    public B getStart() {
        return start;
    }

    public B getEndInclusive() {
        return endInclusive;
    }

    public TemporalAmount getStep() {
        return step;
    }

    public boolean isAscending() {
        return isAscending;
    }

    /**
     * Create an ascending temporal progression.
     */
    static public <B extends Temporal & Comparable<? super B>> TemporalProgression<B> upTo(
        final B start,
        final B endInclusive,
        final TemporalAmount step
    ) {
        return new TemporalProgression<>(start, endInclusive, step, true);
    }

    /**
     * Create a descending temporal progression.
     */
    static public <B extends Temporal & Comparable<? super B>> TemporalProgression<B> downTo(
        final B start,
        final B endInclusive,
        final TemporalAmount step
    ) {
        return new TemporalProgression<>(start, endInclusive, step, false);
    }

    private TemporalProgression(
        final B start,
        final B endInclusive,
        final TemporalAmount step,
        final boolean isAscending
    ) {
        this.start = start;
        this.endInclusive = endInclusive;
        this.step = step;
        this.isAscending = isAscending;
    }

    @Override
    public Iterator<B> iterator() {
        return isAscending
            ? new AscendingTemporalIterator<>(this)
            : new DescendingTemporalIterator<>(this);
    }

    /**
     * Iterates between {@link #start} and {@link #endInclusive} in ascending direction.
     */
    static private class AscendingTemporalIterator
        <B extends Temporal & Comparable<? super B>> implements Iterator<B> {

        final private TemporalProgression<B> iterable;

        private B current;

        private AscendingTemporalIterator(final TemporalProgression<B> iterable) {
            this.iterable = iterable;
            this.current = iterable.start;
        }

        @Override
        public boolean hasNext() {
            return (current.compareTo(iterable.endInclusive) <= 0);
        }

        @Override
        @SuppressWarnings("unchecked")
        public B next() {
            final B next = current;
            current = (B) current.plus(iterable.step);
            return next;
        }
    }

    /**
     * Iterates between {@link #start} and {@link #endInclusive} in descending direction.
     */
    static private class DescendingTemporalIterator
        <B extends Temporal & Comparable<? super B>> implements Iterator<B> {

        final private TemporalProgression<B> iterable;

        private B current;

        private DescendingTemporalIterator(final TemporalProgression<B> iterable) {
            this.iterable = iterable;
            this.current = iterable.start;
        }

        @Override
        public boolean hasNext() {
            return (current.compareTo(iterable.endInclusive) >= 0);
        }

        @Override
        @SuppressWarnings("unchecked")
        public B next() {
            final B next = current;
            current = (B) current.minus(iterable.step);
            return next;
        }
    }
}
