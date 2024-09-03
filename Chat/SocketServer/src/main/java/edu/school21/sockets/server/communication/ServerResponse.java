package edu.school21.sockets.server.communication;

import edu.school21.sockets.server.commandHandlers.CommandStatus;

import java.util.Map;

public class ServerResponse {
    private CommandStatus status;
    private String message;
    private Map<String, Object> data;

    public ServerResponse() {}

    public ServerResponse(CommandStatus status, String message, Map<String, Object> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public CommandStatus getStatus() {
        return status;
    }

    public void setStatus(CommandStatus status) {
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

    public void addData(String key, Object val) {
        data.put(key, val);
    }
}
