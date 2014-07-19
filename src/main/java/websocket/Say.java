package websocket;

/**
 * Created by sakura on 2014/07/20.
 */
public class Say implements Message {
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
