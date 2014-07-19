package websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
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
        Assert.assertEquals(Hello.class, message.getClass());
        Hello hello = (Hello)message;
        Assert.assertEquals("test", hello.getName());
    }

    @Test
    public void testDeserializeSay() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue("{\"text\":\"message\", \"type\":\"say\"}", Message.class);
        Assert.assertEquals(Say.class, message.getClass());
        Say say = (Say)message;
        Assert.assertEquals("message", say.getText());
    }
}
