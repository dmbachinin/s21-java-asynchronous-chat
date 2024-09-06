package edu.school21.sockets.server.responseGenerator.responses;

import edu.school21.sockets.models.Message;

public class MessageResponse {

    public static String generate(Message entity) {
        return entity.getContent();
    }
}
