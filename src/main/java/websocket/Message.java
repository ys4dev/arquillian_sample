package websocket;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.jsontype.impl.AsPropertyTypeDeserializer;

/**
 * Created by sakura on 2014/07/19.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(name = "hello", value = Hello.class),
        @JsonSubTypes.Type(name = "say", value = Say.class)
})
public interface Message {

}
