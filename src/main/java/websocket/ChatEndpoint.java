package websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import javax.xml.ws.RequestWrapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sakura on 2014/07/19.
 */
@ServerEndpoint("/chat")
@ApplicationScoped
public class ChatEndpoint {

    Map<Session, User> sessions = new HashMap<>();

    public ChatEndpoint() {
        System.out.printf("new endpoint");
    }

    @OnOpen
    public void onOpen(Session session) {
        sessions.put(session, null);
    }

    @OnMessage
    public void onMessage(Session session, String text) throws IOException {
        System.out.printf("message:%s%n", text);
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(text, Message.class);
        if (message instanceof Hello) {
            processHello((Hello)message, session);
        } else if (message instanceof Talk) {
            processTalk((Talk)message, session);
        }
    }

    @OnError
    public void onError(Throwable t) {
        t.printStackTrace();
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
    }

    protected void processHello(Hello hello, Session session) {
        User user = sessions.get(session);
        if (user == null) {
            user = new User();
            sessions.put(session, user);
        }
        user.setName(hello.getName());
        ObjectMapper mapper = new ObjectMapper();
        try {
            ServerHello serverHello = new ServerHello();
            serverHello.setName(hello.getName());
            String text = mapper.writeValueAsString(serverHello);
            session.getAsyncRemote().sendText(text);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    protected void processTalk(Talk talk, Session session) {
        User user = sessions.get(session);
        if (user == null || user.getName() == null) {
            return;
        }

        talk.setFrom(user.getName());
        broadcast(talk);
    }

    protected void broadcast(Message message) {
        for (Session session : sessions.keySet()) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                String text = mapper.writeValueAsString(message);
                session.getAsyncRemote().sendText(text);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }
}
