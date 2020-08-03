package org.j4work.benchmarks.typecheck;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

/**
 * Benchmark instanceof/isInstance/isAssignableFrom/class equality performance.
 *
 * How to run:
 * -----------
 * mvn clean package
 * java -jar target/benchmarks.jar org.j4work.benchmarks.typecheck.InstanceOfBenchmarks
 */
@Fork(value = 2)
@Warmup(iterations = 4)
@Measurement(iterations = 6)
public class InstanceOfBenchmarks {
    @State(Scope.Benchmark)
    static public class InstanceAndClassState {
        final private Object integer = 0;

        final private Class integerClass = Integer.class;
    }

    /**
     * instanceof with the object's exact class
     */
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public boolean test_instanceof_Integer(InstanceAndClassState state) {
        return state.integer instanceof Integer;
    }

    /**
     * instanceof with a parent class of the object (class hierarchy is traversed)
     */
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public boolean test_instanceof_Number(InstanceAndClassState state) {
        return state.integer instanceof Number;
    }

    /**
     * isInstance with the object's exact class
     */
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public boolean test_isInstance_Integer(InstanceAndClassState state) {
        return state.integerClass.isInstance(state.integer);
    }

    /**
     * isAssignableFrom with the object's exact class
     */
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public boolean test_isAssignableFrom_Integer(InstanceAndClassState state) {
        return Integer.class.isAssignableFrom(state.integerClass);
    }

    /**
     * isAssignableFrom with a parent class of the object (class hierarchy is traversed)
     */
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public boolean test_isAssignableFrom_Number(InstanceAndClassState state) {
        return Number.class.isAssignableFrom(state.integerClass);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public boolean test_getClass_equals_Integer(InstanceAndClassState state) {
        return state.integer.getClass().equals(Integer.class);
    }

    /**
     * Direct class equality with the object's exact class
     */
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public boolean test_getCass_doubleEqualsOperator_Integer(InstanceAndClassState state) {
        return state.integer.getClass() == Integer.class;
    }
}
