package edu.school21.sockets.server.responseGenerator;

import edu.school21.sockets.server.commandHandlers.CommandStatus;
import edu.school21.sockets.server.communication.ServerResponse;

import java.util.Map;

public interface MessageComposer {
    String generate(ServerResponse response);
}

