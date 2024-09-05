package edu.school21.sockets.client.requestHandles;

import java.util.HashMap;
import java.util.Map;

public class Request {
    private String command;
    private String status;
    private String message;
    private Map<String, Object> data;

    public Request() {
        command = null;
        status = null;
        message = null;
        data = new HashMap<>();
    }

    public<T> T getValue(String key, Class<T> clazz) {
        if (!data.containsKey(key)) {
            return null;
        }
        Object value = data.get(key);

        return clazz.isInstance(value) ? clazz.cast(value) : null;
    }

    public String getStatus() {
        return status;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Request{" +
                "command='" + command + '\'' +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
