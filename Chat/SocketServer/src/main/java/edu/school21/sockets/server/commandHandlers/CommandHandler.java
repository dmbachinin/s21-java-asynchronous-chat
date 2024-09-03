package edu.school21.sockets.server.commandHandlers;

import edu.school21.sockets.server.communication.ServerResponse;
import edu.school21.sockets.server.communication.UserCommand;

public interface CommandHandler {
    ServerResponse execute(UserCommand command);
}
