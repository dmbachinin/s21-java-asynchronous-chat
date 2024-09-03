package edu.school21.sockets.server.commandHandlers;

public interface CommandHandler {
    String execute(UserCommand command);
}
