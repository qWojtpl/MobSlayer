package pl.mobslayer.data;

import java.util.HashMap;

public class MessagesManager {

    private final HashMap<String, String> messages = new HashMap<>();

    public String getMessage(String key) {
        return messages.getOrDefault(key, "").replace("&", "§");
    }

    public void addMessage(String key, String message) {
        messages.put(key, message);
    }

    public void clearMessages() {
        messages.clear();
    }

}
