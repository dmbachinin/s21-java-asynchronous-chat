package edu.school21.sockets.server.responseGenerator.responses;

import edu.school21.sockets.models.User;

public class ErrorResponse implements Response<String> {
    @Override
    public String generate(String message) {
        return message;
    }
}
