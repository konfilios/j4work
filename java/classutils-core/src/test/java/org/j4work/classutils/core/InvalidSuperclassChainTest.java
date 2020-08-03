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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Unit tests for the {@link TypeChains#getSuperclassChains(Class, Class)} and
 * {@link TypeChains#getSuperclassChain(Class, Class) getSuperclassChain(Class, Class)} methods.
 *
 * @author aphillips
 * @since 6 Mar 2009
 */
@RunWith(value = Parameterized.class)
@SuppressWarnings({"rawtypes", "unchecked"})
public class InvalidSuperclassChainTest {

    private final Class clazz;

    private final Class superclass;

    // called for each parameter set in the test data
    public InvalidSuperclassChainTest(Class<?> clazz, Class<?> superclass) {
        this.clazz = clazz;
        this.superclass = superclass;
    }

    @Parameters
    public static Collection<Object[]> data() {
        List<Object[]> data = new ArrayList<Object[]>();

        // invalid arguments
        data.add(new Object[]{null, null});
        data.add(new Object[]{null, Interface1.class});
        data.add(new Object[]{Class1.class, null});

        return data;
    }

    @Test(expected = IllegalArgumentException.class)
    public void getSuperclassChain_invalid() {
        TypeChains.getSuperclassChain(clazz, superclass);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getSuperclassChains_invalid() {
        TypeChains.getSuperclassChains(clazz, superclass);
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

    private static class Class1 implements Interface4a, Cloneable {

    }

}
