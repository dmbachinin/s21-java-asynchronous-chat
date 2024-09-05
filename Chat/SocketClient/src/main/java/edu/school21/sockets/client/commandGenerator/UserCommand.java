package edu.school21.sockets.client.commandGenerator;

import java.util.Map;

public class UserCommand {
    String command;
    Map<String , Object> parameters;

    public UserCommand() {}

    public UserCommand(String command, Map<String, Object> data) {
        this.command = command;
        this.parameters = data;
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
}
