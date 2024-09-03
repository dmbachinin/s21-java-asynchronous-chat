package edu.school21.sockets.server.responseGenerator.responses;

import edu.school21.sockets.server.commandHandlers.CommandStatus;

import java.util.Map;

@FunctionalInterface
public interface Response<T> {
    String generate(T entity);
}
