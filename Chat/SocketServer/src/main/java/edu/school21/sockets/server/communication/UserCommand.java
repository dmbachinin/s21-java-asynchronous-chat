package edu.school21.sockets.server.communication;

import java.util.Map;

public class UserCommand {
    private String command;
    private Map<String, Object> parameters;

    public UserCommand() {}

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
}
