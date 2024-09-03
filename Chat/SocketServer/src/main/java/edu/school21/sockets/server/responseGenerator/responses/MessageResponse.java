package edu.school21.sockets.server.responseGenerator.responses;

public class MessageResponse implements Response<String> {

    @Override
    public String generate(String entity) {
        return entity;
    }
}
