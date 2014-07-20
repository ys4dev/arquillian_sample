package websocket;

/**
 * Created by sakura on 2014/07/20.
 */
public class Talk extends Message {

    public static final String TYPE = "talk";

    private String text;
    private String from;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
