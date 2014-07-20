package websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import static org.junit.Assert.*;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by sakura on 2014/07/20.
 */
public class MessageTest {

    @Test
    public void testDeserializeHello() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue("{\"name\":\"test\", \"type\":\"hello\"}", Message.class);
        assertEquals(Hello.class, message.getClass());
        Hello hello = (Hello)message;
        assertEquals("test", hello.getName());
    }

    @Test
    public void testDeserializeTalk() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue("{\"text\":\"message\", \"type\":\"talk\", \"from\":\"user name\"}", Message.class);
        assertEquals(Talk.class, message.getClass());
        Talk talk = (Talk)message;
        assertEquals("message", talk.getText());
    }

    @Test
    public void testServerHello() throws Exception {
        ServerHello hello = new ServerHello();
        hello.setName("new name");
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(mapper.writeValueAsString(hello), Message.class);
        ServerHello hello2 = (ServerHello)message;
        assertEquals("new name", hello2.getName());
    }
}
