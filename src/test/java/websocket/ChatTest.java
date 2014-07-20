package websocket;

import org.junit.Test;
import static org.mockito.Mockito.*;

import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;

/**
 * Created by sakura on 2014/07/21.
 */
public class ChatTest {
    @Test
    public void testChat() throws Exception {
        ChatEndpoint endpoint = new ChatEndpoint();
        Session session = mock(Session.class);
        RemoteEndpoint.Async remote = mock(RemoteEndpoint.Async.class);
        when(session.getAsyncRemote()).thenReturn(remote);

        endpoint.onOpen(session);
        endpoint.onMessage(session, "{\"type\":\"hello\", \"name\":\"my name\"}");
        verify(remote).sendText("{\"type\":\"serverHello\",\"name\":\"my name\"}");
        endpoint.onMessage(session, "{\"type\":\"talk\", \"from\":\"user\", \"text\":\"message\"}");
        verify(remote).sendText("{\"type\":\"talk\",\"text\":\"message\",\"from\":\"my name\"}");
    }
}
