package org.j4work.enums.adapters.jackson2.serdeser;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * https://fasterxml.github.io/jackson-databind/javadoc/2.2.0/com/fasterxml/jackson/databind/ser/std/StdSerializer.html
 * https://github.com/eugenp/tutorials/blob/master/jackson/src/main/java/com/baeldung/jackson/serialization/DistanceSerializer.java
 * http://www.baeldung.com/jackson-serialize-enums
 * http://www.davismol.net/2015/05/18/jackson-create-and-register-a-custom-json-serializer-with-stdserializer-and-simplemodule-classes/
 * http://www.davismol.net/2015/05/20/jackson-create-a-custom-json-deserializer-with-stddeserializer-and-jsontoken-classes/
 */
public class EnumJacksonSerializer extends StdSerializer<Enum> {

    protected EnumJacksonSerializer(JavaType type) {
        super(type);
    }

    @Override
    public void serialize(
        Enum anEnum, JsonGenerator jsonGenerator, SerializerProvider serializerProvider
    )
        throws IOException, JsonGenerationException {

    }
}
