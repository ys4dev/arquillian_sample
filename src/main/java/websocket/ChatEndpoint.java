package websocket;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sakura on 2014/07/19.
 */
@ServerEndpoint("/chat")
public class ChatEndpoint {

    Set<Session> sessions = new HashSet<>();

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
    }

    @OnMessage
    public void onMessage(Session session, String text) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(text, Message.class);
    }

    @OnError
    public void onError(Throwable t) {

    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
    }
}
