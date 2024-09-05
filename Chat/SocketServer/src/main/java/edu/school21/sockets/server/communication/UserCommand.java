package edu.school21.sockets.server.communication;

import java.util.HashMap;
import java.util.Map;

public class UserCommand {
    private String command;
    private Map<String, Object> parameters;

    public UserCommand() {
        command = null;
        parameters = new HashMap<>();
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public void addParameter(String key, Object val) {
        parameters.put(key, val);
    }

    @Override
    public String toString() {
        return "UserCommand{" +
                "command='" + command + '\'' +
                ", parameters=" + parameters +
                '}';
    }
}
