package edu.school21.sockets.server.communication;

import edu.school21.sockets.server.commandHandlers.CommandStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ServerResponse {
    private CommandStatus status;
    private String message;
    private Map<String, Object> data;

    public ServerResponse() {
        status = null;
        message = null;
        data = new HashMap<>();
    }

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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ServerResponse response = (ServerResponse) object;
        return status == response.status
                && Objects.equals(data, response.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, data);
    }
}
