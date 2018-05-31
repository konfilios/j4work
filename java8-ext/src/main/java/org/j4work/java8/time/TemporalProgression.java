package org.j4work.java8.time;

import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.util.Iterator;

public class TemporalProgression<B extends Temporal & Comparable<? super B>>
    implements Iterable<B> {

    /**
     * Start temporal object.
     */
    final private B start;

    /**
     * End temporal object.
     */
    final private B endInclusive;

    /**
     *
     */
    final private TemporalAmount step;

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

    static public <B extends Temporal & Comparable<? super B>> TemporalProgression<B> upTo(
        final B start,
        final B endInclusive,
        final TemporalAmount step
    ) {
        return new TemporalProgression<>(start, endInclusive, step, true);
    }

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
            current = (B)current.plus(iterable.step);
            return next;
        }
    }

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
            current = (B)current.minus(iterable.step);
            return next;
        }
    }
}
