package org.j4work.classutils.core;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;
import static java.util.Collections.singleton;

/**
 * .
 */
public class TypeChains {

    /**
     * Returns <u>one</u> of the possible chains of superclasses and/or interfaces joining the
     * specified class to the given superclass, as returned by {@link #getSuperclassChains(Class, Class)}.
     * <i>Which</i> of the possible chains will be returned is not defined.
     * <p>
     * If <code>superclass</code> is <i>not</i> a superclass or -interface of <code>class</code>,
     * the method returns <code>null</code>. This may happen (in spite of the signature) if the
     * method is called with non-generic arguments.
     *
     * @param <S>        the type of the superclass at the &quot;end&quot; of the chain
     * @param clazz      the class at the &quot;start&quot; of the superclass chain
     * @param superclass the class at the &quot;end&quot; of the superclass chain
     * @return <u>one</u> superclass chain linking <code>class</code> to <code>superclass</code>,
     * where successive elements of the list are immediate superclasses or -interfaces. If
     * <code>class</code> is not a subclass of <code>superclass</code>, returns <code>null</code>.
     * @throws IllegalArgumentException if either argument is null
     * @see #getSuperclassChains(Class, Class)
     */
    @Nonnull
    public static <S> List<Class<? extends S>> getSuperclassChain(
        @Nonnull Class<? extends S> clazz,
        @Nonnull Class<S> superclass
    ) {
        return getSuperclassChainsInternal(clazz, superclass, true).iterator().next();
    }

    /**
     * Returns the chain of superclass and/or interfaces from the specified class to the given
     * superclass. Either parameter may be an interface.
     * <p>
     * Each list in the resulting set contains immediate superclass elements in order, i.e. for
     * classes
     * <p>
     * <pre>
     * class Foo {}
     * class Bar extends Foo {}
     * class Baz extends Bar {}
     * </pre>
     * <p>
     * <code>getSuperclassChains(Baz.class, Foo.class)</code> will return one list, <code>[Baz.class,
     * Bar.class, Foo.class]</code>.
     * <p>
     * If both parameters are classes, there can only be one possible chain. However, if the superclass
     * is an interface, there may be multiple possible inheritance chains. For instance, for
     * <p>
     * <pre>
     * interface Foo {}
     * interface Bar1 extends Foo {}
     * interface Bar2 extends Foo {}
     * interface Baz extends Bar1, Bar2 {}
     * </pre>
     * <p>
     * both <code>[Baz.class, Bar1.class, Foo.class]</code> and <code>[Baz.class, Bar2.class, Foo.class]</code>
     * are valid inheritance chains, and the method returns both.
     * <p>
     * If <code>superclass</code> is <i>not</i> a superclass or -interface of <code>class</code>,
     * the method returns an empty set. This may happen (in spite of the signature) if the
     * method is called with non-generic arguments.
     *
     * @param <S>        the type of the superclass at the &quot;end&quot; of the chain
     * @param clazz      the class at the &quot;start&quot; of the superclass chain
     * @param superclass the class at the &quot;end&quot; of the superclass chain
     * @return all possible superclass chains linking <code>class</code> to <code>superclass</code>,
     * where successive elements of the list are immediate superclasses or -interfaces. If
     * <code>class</code> is not a subclass of <code>superclass</code>, returns an empty set.
     * @throws IllegalArgumentException if either argument is null
     * @see #getSuperclassChain(Class, Class)
     */
    @Nonnull
    public static <S> Set<List<Class<? extends S>>> getSuperclassChains(
        @Nonnull Class<? extends S> clazz,
        @Nonnull Class<S> superclass) {
        return getSuperclassChainsInternal(clazz, superclass, false);
    }

    @Nonnull
    private static <S> Set<List<Class<? extends S>>> getSuperclassChainsInternal(
        @Nonnull Class<? extends S> clazz,
        @Nonnull Class<S> superclass,
        boolean oneChainSufficient
    ) {
        if (!superclass.isAssignableFrom(clazz)) {
            throw new IllegalArgumentException(superclass + " is not a supertype of " + clazz);
        }

        // interfaces only need to be considered if the superclass is an interface
        return getSuperclassSubchains(
            clazz,
            superclass,
            oneChainSufficient,
            superclass.isInterface()
        );
    }

    // recursive method: gets the subchains from the given class to the target class
    @SuppressWarnings("unchecked")
    private static <S> Set<List<Class<? extends S>>> getSuperclassSubchains(
        Class<? extends S> subclass,
        Class<S> superclass,
        boolean oneChainSufficient,
        boolean considerInterfaces
    ) {

        // base case: the subclass *is* the target class
        if (subclass.equals(superclass)) {
            // since the list will be built from the *head*, a linked list is a good choice
            List<Class<? extends S>> subchain = new LinkedList<Class<? extends S>>();
            subchain.add(subclass);
            return singleton(subchain);
        }

        // recursive case: get all superclasses and, if required, interfaces and recurse

        Set<List<Class<? extends S>>> subchains = new HashSet<List<Class<? extends S>>>();

        for (Class<? extends S> supertype : getImmediateSupertypes(subclass, considerInterfaces)) {
            Set<List<Class<? extends S>>> subchainsFromSupertype = getSuperclassSubchains(
                supertype,
                superclass,
                oneChainSufficient,
                considerInterfaces
            );

            // each chain from the supertype results in a chain [current, subchain-from-super]
            for (List<Class<? extends S>> subchainFromSupertype : subchainsFromSupertype) {
                // adds the class to the beginning of the subchain and stores this extended subchain
                subchainFromSupertype.add(0, subclass);
                subchains.add(subchainFromSupertype);

                if (oneChainSufficient) {
                    return subchains;
                }
            }
        }

        return subchains;
    }

    @SuppressWarnings("unchecked")
    private static <S> Set<Class<? extends S>> getImmediateSupertypes(
        Class<? extends S> subclass, boolean considerInterfaces
    ) {
        Set<Class<? extends S>> supertypes = new HashSet<Class<? extends S>>();

        Class<? extends S> immediateSuperclass = (Class<? extends S>) subclass.getSuperclass();

        // interfaces and Object don't have a superclass
        if (immediateSuperclass != null) {
            supertypes.add(immediateSuperclass);
        }

        if (considerInterfaces) {
            supertypes.addAll(asList((Class<? extends S>[]) subclass.getInterfaces()));
        }
        return supertypes;
    }
}
