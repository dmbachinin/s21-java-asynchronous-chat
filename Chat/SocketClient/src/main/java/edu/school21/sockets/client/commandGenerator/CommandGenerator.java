package edu.school21.sockets.client.commandGenerator;

import org.springframework.stereotype.Component;

import java.util.Map;

public class CommandGenerator {
    static public UserCommand generate(AvailableCommand command, Map<String, Object> data) {
        return new UserCommand(command.toString(), data);
    }
}
