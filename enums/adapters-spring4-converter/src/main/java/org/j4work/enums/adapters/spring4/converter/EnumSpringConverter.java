package org.j4work.enums.adapters.spring4.converter;

import org.j4work.enums.core.api.Enums;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;

import java.util.HashSet;
import java.util.Set;

/**
 * .
 */
public class EnumSpringConverter implements ConditionalGenericConverter {

    /**
     * Initially allow conversion between any type and any enum and refine later using matches().
     */
    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        final Set<ConvertiblePair> pairs = new HashSet<ConvertiblePair>();

        pairs.add(new ConvertiblePair(Number.class, Enum.class));
        pairs.add(new ConvertiblePair(String.class, Enum.class));

        return pairs;
    }

    @Override
    public Object convert(Object sourceObject, TypeDescriptor sourceType, TypeDescriptor targetType) {
        return Enums.SERVICE.convert(
            sourceObject,
            targetType.getObjectType()
        );
    }

    /**
     * Should the conversion from {@code sourceType} to {@code targetType} currently under
     * consideration be selected?
     *
     * @param sourceType the type descriptor of the field we are converting from
     * @param targetType the type descriptor of the field we are converting to
     * @return true if conversion should be performed, false otherwise
     */
    @Override
    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {

        return Enums.SERVICE.canConvert(
            sourceType.getObjectType(),
            targetType.getObjectType()
        );
    }
}
