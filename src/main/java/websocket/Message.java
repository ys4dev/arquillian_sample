package websocket;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Created by sakura on 2014/07/19.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(name = Hello.TYPE, value = Hello.class),
        @JsonSubTypes.Type(name = Talk.TYPE, value = Talk.class),
        @JsonSubTypes.Type(name = ServerHello.TYPE, value = ServerHello.class)
})
public abstract class Message {
    //public abstract String getType();
}
