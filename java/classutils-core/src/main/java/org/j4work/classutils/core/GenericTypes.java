package org.j4work.classutils.core;

import javax.annotation.Nonnull;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.*;

/**
 * .
 */
public class GenericTypes {

    /**
     * Retrieves the type arguments of a class when regarded as an subclass of the
     * given typed superclass or interface. The order of the runtime type classes matches the order
     * of the type variables in the declaration of the typed superclass or interface.
     * <p>
     * For example, for the classes
     * <p>
     * <pre>
     * class Foo&lt;U, V&gt; {}
     * class Bar&lt;W&gt; extends Foo&lt;String, W&gt; {}
     * class Baz extends Bar&lt;Long&gt;
     * </pre>
     * <p>
     * and a <code>typedClass</code> argument of <code>Baz.class</code>, the method should return
     * <p>
     * <ul>
     * <li><code>[String, Long]</code> for a <code>typedSuperclass</code> argument of <code>Foo.class</code>,
     * and
     * <li><code>[Long]</code> if <code>typedSuperclass</code> is <code>Bar.class</code>.
     * </ul>
     * For type parameters that cannot be determined, <code>null</code> is returned.
     * <p>
     * <b>Note:</b> It is <u>not</u> possible to retrieve type information that is not available
     * in the (super)class hierarchy at <u>compile</u>-time. Calling
     * <code>getActualTypeArguments(new ArrayList&lt;String&gt;().getClass(), List.class)</code> will,
     * for instance, return <code>[null]</code> because the specification of the actual type
     * (<code>String</code>, in this example) did not take place either in the superclass {@link AbstractList}
     * or the interface {@link List}.
     * <p>
     * If {@code superclass} is <em>not</em> a superclass or -interface of {@code class},
     * the method returns {@code null}. This may happen (in spite of the signature) if the
     * method is called with non-generic arguments.
     *
     * @param <S>             the type of the object
     * @param typedClass      the class for which type information is required
     * @param typedSuperclass the typed class or interface of which the object is to be regarded a
     *                        subclass
     * @return the type arguments for the given class when regarded as a subclass of the
     * given typed superclass, in the order defined in the superclass. If
     * {@code class} is not a subclass of {@code superclass}, returns {@code null}.
     * @throws IllegalArgumentException if <code>typedSuperclass</code> or <code>typedClass</code>
     *                                  is <code>null</code>
     */
    @Nonnull
    public static <S> List<Class<?>> getActualTypeArguments(
        @Nonnull Class<? extends S> typedClass,
        @Nonnull Class<S> typedSuperclass
    ) {
        // The type signature should ensure that the class really *is* an subclass of
        // typedSuperclass, but this can be circumvented by using "generic-less" arguments.
//        if (!typedSuperclass.isAssignableFrom(typedClass)) {
//            throw new IllegalArgumentException(typedClass + " is not a supertype of " + typedClass);
//        }

        TypeVariable<?>[] typedClassTypeParams = typedSuperclass.getTypeParameters();

        // if the class has no parameters, return
        if (typedClassTypeParams.length == 0) {
            return new ArrayList<Class<?>>(0);
        }

        //
        // It would be nice if the parent class simply "aggregated" all the type variable
        // assignments that happen in subclasses. In other words, it would be nice if, in the
        // example in the JavaDoc, new Baz().getClass().getSuperclass().getGenericSuperclass()
        // would return [String, Long] as actual type arguments.
        // Unfortunately, though, it returns [String, W], because the assignment of Long to W
        // isn't accessible to Bar. W's value is available from new Baz().getClass().getGenericSuperclass(),
        // and must be "remembered" as we traverse the object hierarchy.
        // Note, though, that the "variable substitution" of W (the variable used in Bar) for V (the
        // equivalent variable in Foo) *is* propagated, but only to the immediate parent!
        //
        Map<TypeVariable<?>, Class<?>> typeAssignments =
            new HashMap<TypeVariable<?>, Class<?>>(typedClassTypeParams.length);

        /*
         * Get one possible path from the typed class to the typed superclass. For classes, there
         * is only one (the superclass chain), but for interfaces there may be multiple. We only
         * need one, however (and it doesn't matter which one) since the compiler does not allow
         * inheritance chains with conflicting generic type information.
         */
        List<Class<? extends S>> superclassChain = TypeChains.getSuperclassChain(typedClass, typedSuperclass);

        assert superclassChain != null : Arrays.<Class<?>>asList(typedSuperclass, typedClass);

        /*
         * The list is ordered so that successive elements are immediate superclasses. The iteration
         * stops with the class whose *superclass* is the last element, because type information
         * is collected from the superclass.
         */
        for (int i = 0; i < superclassChain.size() - 1; i++) {
            collectAssignments(superclassChain.get(i), superclassChain.get(i + 1), typeAssignments);
        }

        // will contain null for entries for which no class could be resolved
        return getActualAssignments(typedClassTypeParams, typeAssignments);
    }

    private static void collectAssignments(
        Class<?> clazz,
        Class<?> supertype,
        Map<TypeVariable<?>, Class<?>> typeAssignments
    ) {
        TypeVariable<?>[] typeParameters = supertype.getTypeParameters();

        // the superclass is not necessarily a generic class
        if (typeParameters.length == 0) {
            return;
        }

        Type[] actualTypeAttributes = getActualTypeAttributes(clazz, supertype);

        assert typeParameters.length == actualTypeAttributes.length
            : Arrays.asList(typeParameters, typeAssignments);

        // matches up type parameters with their actual assignments, assuming the order is the same!
        for (int i = 0; i < actualTypeAttributes.length; i++) {
            Type type = actualTypeAttributes[i];

            /*
             * type will be a Class or ParameterizedType if the actual type is known,
             * and a TypeVariable if not.
             */
            if (type instanceof Class) {
                typeAssignments.put(typeParameters[i], (Class<?>) type);
            } else if (type instanceof ParameterizedType) {
                assert ((ParameterizedType) type).getRawType() instanceof Class : type;
                typeAssignments.put(typeParameters[i],
                    (Class<?>) ((ParameterizedType) type).getRawType());
            } else {
                assert type instanceof TypeVariable<?> : type;

                /*
                 * The actual type arguments consist of classes and type variables from the
                 * immediate child class. So if the type assignment mapping is updated to
                 * contain the mapping of all type variables of the *current* class to
                 * their classes, then these can be used in the *next* iteration to resolve
                 * any variable "left over" from this round.
                 *
                 * Any variables that cannot be resolved in this round are not resolvable, otherwise
                 * the would have been resolved in the previous round.
                 */
                if (typeAssignments.containsKey(type)) {
                    typeAssignments.put(typeParameters[i], typeAssignments.get(type));
                }

            }

        }

    }

    /**
     * The superclass is not necessarily a ParameterizedType even if it has type
     * parameters! This happens if a user fails to specify type parameters for a
     * class and ignores the warning, e.g.
     * <p>
     * class MyList extends ArrayList
     * <p>
     * In this case, the superclass ArrayList.class has one type parameter, but
     * MyList.class.getGenericSuperclass() returns a simple type object!
     * <p>
     * In this case, no type assignments take place, so the actual arguments are
     * simply the type parameters.
     */
    private static Type[] getActualTypeAttributes(Class<?> clazz, Class<?> supertype) {
        Type genericSupertype = tryGetGenericSupertype(clazz, supertype);

        return genericSupertype instanceof ParameterizedType
            ? ((ParameterizedType) genericSupertype).getActualTypeArguments()
            : supertype.getTypeParameters();
    }

    private static Type tryGetGenericSupertype(Class<?> clazz, Class<?> supertype) {

        if (!supertype.isInterface()) {
            return clazz.getGenericSuperclass();
        }

        for (Type interfaceType : clazz.getGenericInterfaces()) {
            // there is no guarantee that *all* the interfaces are generic
            if (interfaceType instanceof ParameterizedType
                && ((ParameterizedType) interfaceType).getRawType().equals(supertype)) {
                return interfaceType;
            } else {
                assert interfaceType instanceof Class : interfaceType;

                if (interfaceType.equals(supertype)) {
                    return interfaceType;
                }
            }
        }

        throw new AssertionError("Unable to find generic superclass information for class '"
            + clazz + "' and superclass/-interface '" + supertype + "'");
    }

    private static List<Class<?>> getActualAssignments(
        TypeVariable<?>[] typedClassTypeParams,
        Map<TypeVariable<?>, Class<?>> typeAssignments
    ) {
        List<Class<?>> actualAssignments = new ArrayList<Class<?>>(typedClassTypeParams.length);

        // for entries that could not be resolved, null should be returned
        for (TypeVariable<?> typedClassTypeParam : typedClassTypeParams) {
            actualAssignments.add(typeAssignments.get(typedClassTypeParam));
        }

        return actualAssignments;
    }
}
