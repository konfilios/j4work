package org.j4work.enums.core.serialenums.annotated;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * .
 */
public interface SlugAnnotatedEnum {

    /**
     * .
     */
    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface Slug {

        String value();
    }


}
