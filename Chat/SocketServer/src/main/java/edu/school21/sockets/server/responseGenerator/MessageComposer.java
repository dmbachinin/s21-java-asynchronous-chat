package edu.school21.sockets.server.responseGenerator;

import edu.school21.sockets.models.ChatRoom;
import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import edu.school21.sockets.server.commandHandlers.CommandStatus;
import edu.school21.sockets.server.responseGenerator.responses.Response;

import java.util.List;
import java.util.Map;

public interface MessageComposer {
    String generate(CommandStatus status, String message, Map<String, Object> data);
}

