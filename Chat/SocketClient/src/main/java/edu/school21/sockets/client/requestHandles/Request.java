package edu.school21.sockets.client.requestHandles;

import java.util.HashMap;
import java.util.Map;

public class Request {
    private String status;
    private String message;
    private Map<String, Object> data;

    public Request() {
        status = null;
        message = null;
        data = new HashMap<>();
    }

    public String getStatus() {
        return status;
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
}
