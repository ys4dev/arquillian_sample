package websocket;

/**
 * Created by sakura on 2014/07/20.
 */
public class ServerHello extends Message {

    public static final String TYPE = "serverHello";

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
