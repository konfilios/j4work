/*
 * @(#)ClassUtilsSuperclassChainTest.java     6 Mar 2009
 *
 * Copyright © 2009 Andrew Phillips.
 *
 * ====================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ====================================================================
 */
package org.j4work.classutils.core;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.io.Serializable;
import java.util.*;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

/**
 * Unit tests for the {@link TypeChains#getSuperclassChains(Class, Class)} and
 * {@link TypeChains#getSuperclassChain(Class, Class) getSuperclassChain(Class, Class)} methods.
 *
 * @author aphillips
 * @since 6 Mar 2009
 */
@RunWith(value = Parameterized.class)
@SuppressWarnings({"rawtypes", "unchecked"})
public class SuperclassChainTest {

    private final Class clazz;

    private final Class superclass;

    private final Set<List<Class<?>>> expectedSuperclassChains;

    // called for each parameter set in the test data
    public SuperclassChainTest(
        Class<?> clazz,
        Class<?> superclass,
        Set<List<Class<?>>> expectedSuperclassChains
    ) {
        this.clazz = clazz;
        this.superclass = superclass;
        this.expectedSuperclassChains = expectedSuperclassChains;
    }

    @Parameters
    public static Collection<Object[]> data() {
        List<Object[]> data = new ArrayList<Object[]>();

        // nonexistent chains (class/interface -> class/interface)
        data.add(new Object[]{Object.class, String.class, Collections.EMPTY_SET});
        data.add(new Object[]{Interface1.class, Object.class, Collections.EMPTY_SET});
        data.add(new Object[]{Object.class, Interface1.class, Collections.EMPTY_SET});
        data.add(new Object[]{Interface1.class, Cloneable.class, Collections.EMPTY_SET});

        // class -> class chains
        data.add(new Object[]{Object.class, Object.class, asSet(
            asList(Object.class)
        )});
        data.add(new Object[]{Class3.class, Class2.class, asSet(
            asList(Class3.class, Class2.class)
        )});
        data.add(new Object[]{Class3.class, Object.class, asSet(
            asList(Class3.class, Class2.class, Class1.class, Object.class)
        )});

        // class -> interface chains
        data.add(new Object[]{Class2.class, Interface5.class, asSet(
            asList(Class2.class, Interface5.class)
        )});
        data.add(new Object[]{Class3.class, Interface4a.class, asSet(
            asList(Class3.class, Class2.class, Interface5.class, Interface4a.class),
            asList(Class3.class, Class2.class, Class1.class, Interface4a.class)
        )});
        data.add(new Object[]{Class2.class, Interface3.class, asSet(
            asList(Class2.class, Interface5.class, Interface4a.class, Interface3.class),
            asList(Class2.class, Interface5.class, Interface4b.class, Interface3.class),
            asList(Class2.class, Class1.class, Interface4a.class, Interface3.class)
        )});

        // interface -> interface chains
        data.add(new Object[]{Interface4a.class, Cloneable.class, asSet(
            asList(Interface4a.class, Cloneable.class)
        )});

        data.add(new Object[]{Interface5.class, Interface1.class, asSet(
            asList(
                Interface5.class,
                Interface4a.class,
                Interface3.class,
                Interface2a.class,
                Interface1.class
            ),
            asList(
                Interface5.class,
                Interface4a.class,
                Interface3.class,
                Interface2b.class,
                Interface1.class
            ),
            asList(
                Interface5.class,
                Interface4b.class,
                Interface3.class,
                Interface2a.class,
                Interface1.class
            ),
            asList(
                Interface5.class,
                Interface4b.class,
                Interface3.class,
                Interface2b.class,
                Interface1.class
            )
        )});

        return data;
    }

    // can't use SetUtils.asSet as that would cause a circular dependency
    private static <U> Set<U> asSet(U... objects) {
        return new HashSet<U>(asList(objects));
    }

    @Test
    public void getSuperclassChain() {
        List<Class<?>> superclassChain = TypeChains.getSuperclassChain(clazz, superclass);

        // the expected result is null if there is no chain between the arguments
        if (expectedSuperclassChains.isEmpty()) {
            assertNull(superclassChain);
        } else {
            assertTrue(expectedSuperclassChains.contains(superclassChain));
        }
    }

    @Test
    public void getSuperclassChains() {
        assertEquals(expectedSuperclassChains, TypeChains.getSuperclassChains(clazz, superclass));
    }

    private interface Interface1 {

    }

    private interface Interface2a extends Interface1 {

    }

    private interface Interface2b extends Interface1 {

    }

    private interface Interface3 extends Interface2a, Interface2b {

    }

    private interface Interface4a extends Interface3, Cloneable {

    }

    private interface Interface4b extends Interface3 {

    }

    private interface Interface5 extends Interface4a, Interface4b {

    }

    private static class Class1 implements Interface4a, Cloneable {

    }

    private static class Class2 extends Class1 implements Interface5, Serializable {

        private static final long serialVersionUID = 1L;
    }

    private static class Class3 extends Class2 {

        private static final long serialVersionUID = 1L;
    }

}
